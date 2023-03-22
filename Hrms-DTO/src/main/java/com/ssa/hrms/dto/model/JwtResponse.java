package com.ssa.hrms.dto.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class JwtResponse {
	
	
	private  String token;
	private String type;
	private Integer roleTypeId;
	private String message;
	
	public JwtResponse(String token, String type, Integer roleTypeId,String message) {
		this.token = token;
		this.type = type;
		this.roleTypeId = roleTypeId;
		this.message = message;
	}
	
	
	

}
