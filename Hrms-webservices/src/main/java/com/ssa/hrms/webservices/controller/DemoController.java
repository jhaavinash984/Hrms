package com.ssa.hrms.webservices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssa.hrms.common.Constants;
import com.ssa.hrms.common.MessageReader;
import com.ssa.hrms.common.MessageType;
import com.ssa.hrms.dao.UserRepository;
import com.ssa.hrms.dao.mongodb.TestMongodbRepository;
import com.ssa.hrms.dto.model.MessageDTO;
import com.ssa.hrms.dto.model.ResponseModel;
import com.ssa.hrms.dto.mongodbentities.TestMongodb;
import com.ssa.hrms.security.domain.LoggedInUser;

@RestController
@RequestMapping(value="/webapi")
public class DemoController extends ExceptionHandlerController {
	
	@Autowired
	private TestMongodbRepository userRepo;
	
	@PreAuthorize("hasAuthority('SYSTEMADMIN')")
	@RequestMapping(value = "/test", method = RequestMethod.GET, produces = "application/json")
	public ResponseModel getFirstProduct(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    LoggedInUser currentUser = (LoggedInUser)auth.getPrincipal();
	    System.out.println("current is"+currentUser);
		ResponseModel respModel=null;
		MessageDTO msgDTO = new MessageDTO(MessageType.SUCCESS,
				MessageReader.getProperty("user.create.success"));
		respModel = new ResponseModel(Constants.RESULT_SUCCESS_CODE, msgDTO);
        return respModel;
		
	}
	
	@PreAuthorize("hasAuthority('SYSTEMADMIN')")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public TestMongodb addNewUsers(@RequestBody TestMongodb user) {
		System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
		return userRepo.save(user);
	}
	
	

}
