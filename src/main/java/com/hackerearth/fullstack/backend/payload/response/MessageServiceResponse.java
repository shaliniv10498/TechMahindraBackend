package com.hackerearth.fullstack.backend.payload.response;

/**
 * author shalini.verma
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageServiceResponse implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	private HashMap<String , Object> response = new HashMap<>();
	public HashMap<String, Object> getResponse() {
		return response;
	}
	public void setResponse(HashMap<String, Object> response) {
		this.response = response;
	}

	
	
 
}
