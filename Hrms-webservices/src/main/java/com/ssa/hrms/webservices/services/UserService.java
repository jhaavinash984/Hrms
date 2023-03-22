package com.ssa.hrms.webservices.services;

import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ssa.hrms.dao.specifications.UserManagementSpecification;
import com.ssa.hrms.dto.model.UserManagementDTO;
import com.ssa.hrms.dto.mysqlentities.User;
import com.ssa.hrms.security.domain.LoggedInUser;


public interface UserService {
	
	public User saveUser(UserManagementDTO user,LoggedInUser loggedInUser);

	public Map<String, Object> fetchUser(UserManagementSpecification userManagementSpec, PageRequest pageRequestObj,
			LoggedInUser loggedInUser);

}
