package com.ssa.hrms.dto.mysqlentities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="personal_information")
public class PersonalInformation implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
	
	@Size(min = 5, max = 50)
	@Column(name = "FATHER_NAME")
    private String fatherName;
	
	@Size(min = 5, max = 50)
	@Column(name = "MOTHER_NAME")
    private String motherName;
	
	@Size(min = 5, max = 200)
	@Column(name = "ADDRESS")
    private String address;
	
	@Size(min = 5, max = 50)
	@Column(name = "PAN_NUMBER")
    private String panNumber;
	
	@Size(min = 5, max = 50)
	@Column(name = "ADHARCARD_NUMBER")
    private String adharcardNumber;
	
	@Size(min = 5, max = 50)
	@Column(name = "PASSPORT_NUMBER")
    private String passportNumber;
	
	@JoinColumn(name = "USER_ID")
	@ManyToOne(cascade = CascadeType.PERSIST)
	private User userPersonalInformation;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getAdharcardNumber() {
		return adharcardNumber;
	}

	public void setAdharcardNumber(String adharcardNumber) {
		this.adharcardNumber = adharcardNumber;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public User getUserPersonalInformation() {
		return userPersonalInformation;
	}

	public void setUserPersonalInformation(User userPersonalInformation) {
		this.userPersonalInformation = userPersonalInformation;
	}
	

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof PersonalInformation)) {
            return false;
        }
        PersonalInformation other = (PersonalInformation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ssa.hrms.dto.mysqlentities.PersonalInformation[ id=" + id + " ]";
    }
	
}
