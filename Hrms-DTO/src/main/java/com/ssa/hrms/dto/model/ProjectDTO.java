package com.ssa.hrms.dto.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
public class ProjectDTO {
	
	@JsonInclude(Include.NON_NULL)
	private String id;
	
	@NotNull
	@Size(min = 2, max = 200)
	private String name;
	
	private String description;
	
	@NotNull
	private String startDate;
	
	@NotNull
	private String endDate;
	
	private String departmentId;
	
	private String departmentName;
	
	private String groupManagerId;
	
	@JsonInclude(Include.NON_NULL)
	private String groupManagerName;
	
	private String projectManagerId;
	
	@JsonInclude(Include.NON_NULL)
	private String projectManagerName;
	
	private Integer isActive;
	
	@JsonInclude(Include.NON_NULL)
	private Integer groupId;
	
	
}
