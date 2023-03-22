package com.ssa.hrms.dto.model;

public enum Role {
	
	SYSTEMADMIN(600), CUSTOMERADMIN(500), USER(400);
	
	private Integer roleTypeId;
	
	Role(Integer roleTypeId){
		this.roleTypeId = roleTypeId;
	}
	
	public Integer getRoleTypeId(){
		return roleTypeId;
	}

}
