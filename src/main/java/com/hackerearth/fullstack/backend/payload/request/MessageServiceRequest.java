package com.hackerearth.fullstack.backend.payload.request;

/**
 * author shalini.verma
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageServiceRequest implements Serializable {
	
   
	private static final long serialVersionUID = 1L;
   private HashMap<String,Object> request = new HashMap<>();
public HashMap<String,Object> getRequest() {
	return request;
}
public void setRequest(HashMap<String,Object> request) {
	this.request = request;
}

  
   
}
