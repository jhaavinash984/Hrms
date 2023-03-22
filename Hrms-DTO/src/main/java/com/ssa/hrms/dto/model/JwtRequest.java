package com.ssa.hrms.dto.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class JwtRequest implements Serializable {
	

	private static final long serialVersionUID = 5926468583005150707L;
	private String username;
	private String password;
	private String source;
    //need default constructor for JSON Parsing
	public JwtRequest()
	{
		
	}
	public JwtRequest(String username, String password, String source) {
		this.setUsername(username);
		this.setPassword(password);
		this.setSource(source);
	}


}
