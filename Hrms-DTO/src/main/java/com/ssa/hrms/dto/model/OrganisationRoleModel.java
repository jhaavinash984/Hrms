package com.ssa.hrms.dto.model;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class OrganisationRoleModel {
	
	    private String id;
		private String roleName;
	    private String level;
	    private Integer categoryId;

}
