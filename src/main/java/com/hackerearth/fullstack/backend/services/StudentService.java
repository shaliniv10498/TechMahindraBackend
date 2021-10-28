package com.hackerearth.fullstack.backend.services;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackerearth.fullstack.backend.model.Student;
import com.hackerearth.fullstack.backend.payload.request.MessageServiceRequest;
import com.hackerearth.fullstack.backend.repository.StudentRepository;

@Service
public class StudentService {
	
	@Autowired
	private StudentRepository studentRepo;
	
	@Autowired SessionFactory factory;
	
	public Boolean addStudent(Student studentRecord) {
		return studentRepo.save(studentRecord)!=null ;
	}
	
	@Autowired
	private ManagerTrigger trigger;
	
   public List<Student> fetchAllStudents(){
	   List<Student> result = (List<Student>) studentRepo.findAll();
	   return result;
   }
   
   public List<Student> fetchFilteredStudents(String name){
	   Session session=null;
	   try {
		   session = factory.openSession();
		   Query query = session.createQuery("FROM Student where name like ?1");
		   query.setParameter(1,"%"+name+"%");
		   return ((org.hibernate.query.Query) query).list();
	   }
	   catch(Exception e) {
		   e.printStackTrace();
	   }
	   return null;
   }
   
   public HashMap<String,Object> fetchAndSaveData(MessageServiceRequest req) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException{
	   System.out.println(req.getRequest());
	   HashMap reponse = new HashMap<>();
	   if(req.getRequest()!=null) {
			 Iterator<String> iterator = req.getRequest().keySet().iterator();
			 String methodTobeCalled = null;
			 if(iterator.hasNext()){
				 methodTobeCalled = iterator.next();
			 }
			 Class c = Class.forName("com.hackerearth.fullstack.backend.services.ManagerTrigger");
			 //Object obj = c.newInstance();
			  Method method =c.getDeclaredMethod(methodTobeCalled, Object.class);
		      method.setAccessible(true);
		     Object responseInfo = method.invoke(trigger, req.getRequest().get(methodTobeCalled)); 
		   
			   reponse.put("result", responseInfo);
			   return reponse;
		}
	   
	
	   
	  
	   return null;
   }
   
   
}
