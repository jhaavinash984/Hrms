package com.ssa.hrms.dto.model;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.ssa.hrms.dto.mysqlentities.Group;
import com.ssa.hrms.dto.mysqlentities.Role;

import coms.ssa.hrms.dto.entities.key.CreateUpdateDetails;
import lombok.Data;

@Data
public class UserManagementDTO {
	
	@NotNull
	@Size(min = 1, max = 50)
	private String username;
	
	private String firstName;
	
	private String lastName;
	
	private String fullName;
	
	private Integer groupId;
	
	private Integer orgRoleId;
	
	@NotNull
	@Size(min = 5, max = 100)
	private String email;
	
	@NotNull
	@Size(max = 50)
	private String mobileNumber;
	
	private String gender;
	
	private Integer isActive;
	
	private PersonalInformationModel personalInformation;
	
	private List<ProfessionalInformationModel> professionalInformation;
	
	private Integer systemRoleId;
	
}
