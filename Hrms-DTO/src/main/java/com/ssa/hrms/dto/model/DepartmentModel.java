package com.ssa.hrms.dto.model;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
public class DepartmentModel {
	
	private Integer id;
	
	@JsonIgnore
	private Integer groupId;
	
	@NotNull
	@Size(min = 2, max = 200)
	private String departmentName;
	
	private String departmentDesc;
	
	private String departmentCreationDate;
	
	@JsonInclude(Include.NON_NULL)
	private PageRequestModel pageRequestModel;
	

}
