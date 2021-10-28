package com.hackerearth.fullstack.backend.services;

import java.util.HashMap;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hackerearth.fullstack.backend.repository.StudentRepository;

@Component
public class ManagerTrigger {
	
	@Autowired
	private StudentRepository studentRepo;
	
	
	@Autowired 
	private SessionFactory factory;
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public HashMap<String,Object> fetchLimitedStudents(Object object){
		 
		HashMap<String,Integer> objectData = (HashMap<String, Integer>) object;
		  HashMap<String,Object> filtered = new HashMap<>();
		  Session session = null;
				  try {
					  session = factory.openSession();
					  org.hibernate.query.Query query = session.createQuery("FROM Student where name like ?1");
					   query.setParameter(1,"%"+objectData.get("search")+"%");
					    filtered.put("result",query.list());
					    return filtered;
				  }
		       catch(Exception e) {
			   e.printStackTrace();
		   }
		   return null;
	   }


	public StudentRepository getStudentRepo() {
		return studentRepo;
	}


	public void setStudentRepo(StudentRepository studentRepo) {
		this.studentRepo = studentRepo;
	}
	

}
