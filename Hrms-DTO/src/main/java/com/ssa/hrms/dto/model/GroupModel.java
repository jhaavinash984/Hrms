package com.ssa.hrms.dto.model;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ssa.hrms.common.LocalDateDeserializer;

import lombok.Data;

@Data
public class GroupModel {
	
	
	private String groupId;
	
	private String groupName;
	
	private String groupDescription;
	
	private String contactPerson;
	
	private String mobileNumber;
	
	private String email;
	
	private String address;
	
	private String establishmentDate;
	
	@JsonInclude(Include.NON_NULL)
	private PageRequestModel pageRequestModel;

}
