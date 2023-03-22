package com.ssa.hrms.dto.mysqlentities;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;


import coms.ssa.hrms.dto.entities.key.CreateUpdateDetails;
@Entity
@Table(name = "user")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	private Integer id;
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 50)
	@Column(name = "USERNAME",unique = true)
	private String username;
	
	@Basic(optional=false)
	@NotNull
	@Size(min = 1, max = 100)
	@Column(name = "PASSWORD")
	private String password;
	
	@NotNull
	@Size(max=50)
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Size(max=50)
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "GROUP_ID")
	private Integer groupId;
	
	@NotNull
	@Size(min = 5, max = 100)
	@Column(name = "EMAIL")
	private String email;
	
	@Size(max = 50)
	@Column(name = "CONTACT_NUMBER")
	private String contactNumber;
	
	@Column(name = "GENDER")
	private char gender;
	
	@Column(name = "IS_ACTIVE")
	private Integer isActive;
	
	/**@Column(name = "ORGANISATION_ROLE_ID")
	private Integer organisationRoleId ;**/
	
	@Size(max = 30)
	@Column(name = "TIMEZONE")
	private String timezone;
	
	@Embedded
    private CreateUpdateDetails createUpdateDetails;
	
	@JoinColumn(name = "GROUP_ID", referencedColumnName = "GROUP_ID", insertable = false, updatable = false)
	@ManyToOne
	private Group group;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = {
            @JoinColumn(name = "USER_ID", referencedColumnName = "ID") }, inverseJoinColumns = {
            @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID") })
    private List<Role> role;
	
	/**@JoinColumn(name = "ORGANISATION_ROLE_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	@ManyToOne
	private OrganisationRole organisationRole;
	
	@OneToOne(mappedBy = "userIdUserHistory")
	private UserHistory userIdCollection;**/
	
	//@OneToMany(mappedBy = "userIdUserPreviousHistory")
	//private List<UserPreviousHistory> userPreviousHistoryCollection;
	
	/**@OneToOne(mappedBy = "projectManagerIdUserHistory")
	private UserHistory projectManagerIdCollection;
	
	@OneToOne(mappedBy = "groupManagerIdUserHistory")
	private UserHistory groupManagerIdCollection;**/
	
	
	@OneToOne(mappedBy = "userPersonalInformation",cascade=CascadeType.PERSIST)
	private PersonalInformation personalInformation;
	
	/**@OneToMany(mappedBy = "userProfessionalInformation",cascade=CascadeType.PERSIST)
	private List<ProfessionalInformation> professionalInformationCollection;**/
	
    
	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	

	public List<Role> getRole() {
		return role;
	}

	public void setRole(List<Role> role) {
		this.role = role;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public CreateUpdateDetails getCreateUpdateDetails() {
		return createUpdateDetails;
	}

	public void setCreateUpdateDetails(CreateUpdateDetails createUpdateDetails) {
		this.createUpdateDetails = createUpdateDetails;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public PersonalInformation getPersonalInformation() {
		return personalInformation;
	}

	public void setPersonalInformation(PersonalInformation personalInformation) {
		this.personalInformation = personalInformation;
	}


	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	
	/**@OneToMany(mappedBy = "userIdProjectManagerChange")
	private List<ProjectManagerChange> projectManagerChangeCollection;
	
	@OneToMany(mappedBy = "userIdGroupManagerChange")
	private List<GroupManagerChange> groupManagerChangeCollection;**/
	
	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ssa.hrms.dto.mysqlentities.User[ id=" + id + " ]";
    }
	
}
