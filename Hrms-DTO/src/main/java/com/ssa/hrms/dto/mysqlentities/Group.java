package com.ssa.hrms.dto.mysqlentities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import coms.ssa.hrms.dto.entities.key.CreateUpdateDetails;

@Entity
@Table(name = "user_group")
public class Group  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Embedded
    private CreateUpdateDetails createUpdateDetails;
	
	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "GROUP_ID")
	private Integer groupId;
	
	@NotNull
	@Size(min = 5, max = 100)
	@Column(name = "GROUP_NAME")
	private String groupName;
	
	@Size(max = 100)
	@Column(name = "GROUP_DESCRIPTION")
	private String groupDescription;
	
	@NotNull
	@Size(min = 5, max = 100)
	@Column(name = "CONTACT_PERSON")
	private String contactPerson;
	
	@NotNull
	@Size(min = 5, max = 100)
	@Column(name = "MOBILE_NUMBER")
	private String mobileNumber;
	
	@NotNull
	@Size(min = 5, max = 100)
	@Column(name = "EMAIL")
	private String email;
	
	@NotNull
	@Size(min = 5, max = 100)
	@Column(name = "ADDRESS")
	private String address;
	
	@Column(name = "ESTABLISHMENT_DATE")
	private LocalDate establishmentDate;
	
	
	@OneToMany(mappedBy = "group")
	private List<User> user;
	
	/**@OneToMany(mappedBy = "group")
	private List<OrganisationRole> organisationRole;
	
	@OneToMany(mappedBy = "group")
	private List<Department> department;
	
	@OneToMany(mappedBy = "group")
	private List<RoleMenu> roleMenu;
	
	@OneToMany(mappedBy = "group")
	private List<RoleSubMenu> roleSubMenu;**/
	
	
	

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public LocalDate getEstablishmentDate() {
		return establishmentDate;
	}

	public void setEstablishmentDate(LocalDate estbDate) {
		this.establishmentDate = estbDate;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupDescription() {
		return groupDescription;
	}

	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<User> getUser() {
		return user;
	}

	public void setUser(List<User> user) {
		this.user = user;
	}

	public CreateUpdateDetails getCreateUpdateDetails() {
		return createUpdateDetails;
	}

	public void setCreateUpdateDetails(CreateUpdateDetails createUpdateDetails) {
		this.createUpdateDetails = createUpdateDetails;
	}
	
	
}
