package com.ssa.hrms.dto.mysqlentities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import coms.ssa.hrms.dto.entities.key.CreateUpdateDetails;

@Entity
@Table(name = "system_role")
public class Role implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Embedded
    private CreateUpdateDetails createUpdateDetails;
	
	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@NotNull
	@Size(min = 1, max = 100)
	@Column(name = "ROLE_NAME")
	private String roleName;
	
	@NotNull
	@Column(name = "ROLE_TYPE_ID")
	private Integer roleTypeId;
	
	@ManyToMany(mappedBy = "role")
	private List<User> user;
	
	/**@OneToMany(mappedBy = "systemRole")
	private List<RoleMenu> roleMenu;
	
	@OneToMany(mappedBy = "systemRole")
	private List<RoleSubMenu> roleSubMenu;**/

	
	public Integer getRoleTypeId() {
		return roleTypeId;
	}

	public void setRoleTypeId(Integer roleTypeId) {
		this.roleTypeId = roleTypeId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public CreateUpdateDetails getCreateUpdateDetails() {
		return createUpdateDetails;
	}

	public void setCreateUpdateDetails(CreateUpdateDetails createUpdateDetails) {
		this.createUpdateDetails = createUpdateDetails;
	}

	public List<User> getUser() {
		return user;
	}

	public void setUser(List<User> user) {
		this.user = user;
	}
    

}
