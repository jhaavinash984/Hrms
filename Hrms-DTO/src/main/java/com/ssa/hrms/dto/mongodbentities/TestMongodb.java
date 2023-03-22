package com.ssa.hrms.dto.mongodbentities;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class TestMongodb {
	
	    @Id
	    public String id;

	    public String firstName;
	    public String lastName;

	    public TestMongodb() {}

	    public TestMongodb(String firstName, String lastName) {
	        this.firstName = firstName;
	        this.lastName = lastName;
	    }

	    @Override
	    public String toString() {
	        return String.format(
	                "Customer[id=%s, firstName='%s', lastName='%s']",
	                id, firstName, lastName);
	    }

}
