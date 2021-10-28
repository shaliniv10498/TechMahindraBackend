package com.hackerearth.fullstack.backend.controller;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hackerearth.fullstack.backend.exception.CustomException;
import com.hackerearth.fullstack.backend.model.Student;
import com.hackerearth.fullstack.backend.payload.request.MessageServiceRequest;
import com.hackerearth.fullstack.backend.payload.request.StudentRequest;
import com.hackerearth.fullstack.backend.payload.response.MessageResponse;
import com.hackerearth.fullstack.backend.payload.response.MessageServiceResponse;
import com.hackerearth.fullstack.backend.repository.StudentRepository;
import com.hackerearth.fullstack.backend.services.StudentService;
import com.hackerearth.fullstack.backend.utils.Constants;

@CrossOrigin(origins = "http://localhost:5000")
@RestController
@RequestMapping("/api/v1")
public class StudentController {
    

    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private StudentService studentService;


    @PostMapping("/student")
    public Student addStudent(@RequestBody StudentRequest studentRequest) throws CustomException {

        /*
        Complete this function to get a post request as a student taking
        the parameters as Student Name and Student Roll Number
        */
        if(studentRequest.getName()==null||studentRequest.getName().length()==0){
            throw new CustomException(Constants.INVALID_NAME_MESSAGE,Constants.INVALID_NAME);
        }
        if(studentRequest.getRoll()==null||studentRequest.getRoll().length()==0){
            throw new CustomException(Constants.INVALID_ROLL_MESSAGE,Constants.INVALID_ROLL);
        }
        Student student=studentRepository.getByRoll(studentRequest.getRoll());
        if(student!=null){
            throw new CustomException(Constants.STUDENT_ALREADY_PRESENT_MESSAGE,Constants.STUDENT_ALREADY_PRESENT);
        }
        else {
        student=new Student();
        student.setName(studentRequest.getName());
        student.setRoll(studentRequest.getRoll());
        Boolean  status = studentService.addStudent(student);
        
        }
        return student;//
    }
    
    @PostMapping("student/edit")
    public Student editStudent(@RequestBody StudentRequest studentReq) throws CustomException{
    	Student student=null;
    	 if(studentReq.getName()==null||studentReq.getName().length()==0){
             throw new CustomException(Constants.INVALID_NAME_MESSAGE,Constants.INVALID_NAME);
         }
         if(studentReq.getRoll()==null||studentReq.getRoll().length()==0){
             throw new CustomException(Constants.INVALID_ROLL_MESSAGE,Constants.INVALID_ROLL);
         }
    	student=studentRepository.getByRoll(studentReq.getRoll());
        if(student==null){
            throw new CustomException(Constants.NO_SUCH_STUDENT_MESSAGE,Constants.NO_SUCH_STUDENT);
        }
        else {
        	student=new Student();
        	student.setId(studentReq.getId());
        	student.setName(studentReq.getName());
        	student.setRoll(studentReq.getRoll());
        	studentRepository.save(student);
        }
        return student;
    }

    @DeleteMapping("/student/{id}")
    public ResponseEntity<MessageResponse> deleteStudent(@PathVariable long id) throws CustomException {

        /*
        Complete this function to delete as a student taking
        the parameters as student id
        */
        Optional<Student> student=studentRepository.findById(id);
        if(!student.isPresent()){
            throw new CustomException(Constants.NO_SUCH_STUDENT_MESSAGE,Constants.NO_SUCH_STUDENT);
        }
        studentRepository.delete(student.get());
        //MessageResponse msg = new MessageResponse();
       return new ResponseEntity<MessageResponse>(HttpStatus.OK);

       //
    }

    @GetMapping("/student")
    public Iterable<Student> getAllStudents(){
        List<Student> result = studentService.fetchAllStudents();
        return result;/*
        Complete this function to get all the students as get request
        the parameters as student id
        */
       //
    }
    
    @GetMapping("/student/search/{name}")
    public Iterable<Student> getFilteredStudentsByName(@PathVariable String name){
    	List<Student> result = studentService.fetchFilteredStudents(name);
    	return result;
    }

  @GetMapping("/student/fetch/")
  @Produces({MediaType.APPLICATION_JSON})
  @Consumes({MediaType.APPLICATION_JSON})
  public ResponseEntity<MessageServiceResponse> fetchStudents() {
	  HashMap<String , Object> responseMap = new HashMap<>();
	  responseMap.put("result", studentService.fetchAllStudents());
	  MessageServiceResponse responseObj = new MessageServiceResponse();
	  responseObj.setResponse(responseMap);
	  return new ResponseEntity<MessageServiceResponse>(responseObj,HttpStatus.OK);
  }
  
  @PostMapping("/student/fetch/limit")
  @Consumes({MediaType.APPLICATION_JSON})
  @Produces({MediaType.APPLICATION_JSON})
   public ResponseEntity<MessageServiceResponse> fetchStudentsLimit(@RequestBody MessageServiceRequest requestBody) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
	 
	  System.out.println(requestBody);
	  System.out.println(requestBody.getRequest());
	  MessageServiceResponse responseObj = new MessageServiceResponse();
	  try {
		responseObj.setResponse(studentService.fetchAndSaveData(requestBody));
	} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
			| InvocationTargetException | ClassNotFoundException | InstantiationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  return new ResponseEntity<MessageServiceResponse>(responseObj,HttpStatus.OK);
  }
  

}
