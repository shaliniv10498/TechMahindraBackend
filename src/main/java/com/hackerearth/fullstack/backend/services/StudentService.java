package com.hackerearth.fullstack.backend.services;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackerearth.fullstack.backend.model.Student;
import com.hackerearth.fullstack.backend.repository.StudentRepository;

@Service
public class StudentService {
	
	@Autowired
	private StudentRepository studentRepo;
	
	@Autowired SessionFactory factory;
	
	public Boolean addStudent(Student studentRecord) {
		return studentRepo.save(studentRecord)!=null ;
	}
	
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
}
