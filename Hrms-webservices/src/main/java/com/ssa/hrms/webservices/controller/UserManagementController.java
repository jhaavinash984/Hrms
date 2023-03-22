package com.ssa.hrms.webservices.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.ssa.hrms.common.Constants;
import com.ssa.hrms.common.MessageReader;
import com.ssa.hrms.common.MessageType;
import com.ssa.hrms.common.exception.ApplicationException;
import com.ssa.hrms.common.exception.hrmsValidationException;
import com.ssa.hrms.dao.specifications.UserManagementSpecification;
import com.ssa.hrms.dto.model.DepartmentModel;
import com.ssa.hrms.dto.model.MessageDTO;
import com.ssa.hrms.dto.model.PageRequestModel;
import com.ssa.hrms.dto.model.ProjectDTO;
import com.ssa.hrms.dto.model.ResponseModel;
import com.ssa.hrms.dto.model.UserManagementDTO;
import com.ssa.hrms.dto.mysqlentities.User;
import com.ssa.hrms.security.domain.LoggedInUser;
import com.ssa.hrms.webservices.helper.CommonFunction;
import com.ssa.hrms.webservices.services.UserService;

@RestController
@RequestMapping("/webapi/Administration")
public class UserManagementController extends ExceptionHandlerController {
	
	@Autowired 
	CommonFunction commonFunction;
	
	@Autowired
	private UserService userService;
	
	
	@PreAuthorize("hasAuthority('SYSTEMADMIN') or hasAuthority('CUSTOMERADMIN')") 
	@RequestMapping(value = "/addUser", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseModel addUser(@Valid @RequestBody UserManagementDTO user, BindingResult bindingResult) throws hrmsValidationException {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    LoggedInUser loggedInUser = (LoggedInUser)auth.getPrincipal();
		ResponseModel respModel=null;
		if (bindingResult.hasErrors()) {
			 throw new hrmsValidationException(bindingResult);
			
		}else{ 
			try{
				//user.setGroupId(currentUser.getGroupId());
				User newUser=userService.saveUser(user,loggedInUser);
				MessageDTO msgDTO = new MessageDTO(MessageType.SUCCESS,
						MessageReader.getProperty("user.create.success"));
				respModel = new ResponseModel(Constants.RESULT_SUCCESS_CODE, msgDTO);
		        return respModel;
			}
			catch(Exception e){
				MessageDTO msgDTO = new MessageDTO(MessageType.FAILED,
						MessageReader.getProperty("user.create.failure"),e.getMessage());
				respModel = new ResponseModel(Constants.RESULT_FAILED_CODE, msgDTO);
		        return respModel;
			}
				
			}
	}
	
	/**@PreAuthorize("hasAuthority('SYSTEMADMIN') or hasAuthority('CUSTOMERADMIN')") 
	@RequestMapping(value = "/fetchUser", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseModel fetchUser(@RequestBody UserManagementDTO user, BindingResult bindingResult) throws hrmsValidationException {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    LoggedInUser loggedInUser = (LoggedInUser)auth.getPrincipal();
		ResponseModel respModel=null; 
			try{
				//user.setGroupId(currentUser.getGroupId());
				User newUser=userService.saveUser(user,loggedInUser);
				MessageDTO msgDTO = new MessageDTO(MessageType.SUCCESS,
						MessageReader.getProperty("user.create.success"));
				respModel = new ResponseModel(Constants.RESULT_SUCCESS_CODE, msgDTO);
		        return respModel;
			}
			catch(Exception e){
				MessageDTO msgDTO = new MessageDTO(MessageType.FAILED,
						MessageReader.getProperty("user.create.failure"),e.getMessage());
				respModel = new ResponseModel(Constants.RESULT_FAILED_CODE, msgDTO);
		        return respModel;
			}
				
	 }**/
	
	@PreAuthorize("hasAuthority('SYSTEMADMIN') or hasAuthority('CUSTOMERADMIN')") 
	@RequestMapping(value = "/fetchUser", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseModel fetchUser(@RequestBody PageRequestModel  pageRequestModel) {
		
		 LoggedInUser loggedInUser = commonFunction.loggedInUser();
		  try{
				ResponseModel respModel=null;
				UserManagementDTO userManagementDTO = new UserManagementDTO();
				if(loggedInUser.getGroupId() != null){
					userManagementDTO.setGroupId(loggedInUser.getGroupId());
				}else{
					userManagementDTO.setSystemRoleId(loggedInUser.getRole().get(0).getId());
				}
				if((pageRequestModel.getSearchText() != null) && (!pageRequestModel.getSearchText().trim().isEmpty())){
					userManagementDTO = userManagementFilterObject(pageRequestModel.getSearchText().trim());
				}
				PageRequest pageRequestObj = commonFunction.getPageRequestObject(pageRequestModel);
				UserManagementSpecification userMgmtSpec = new UserManagementSpecification(userManagementDTO);
				Map<String, Object> projectData = userService.fetchUser(userMgmtSpec, pageRequestObj,loggedInUser);
				MessageDTO msgDTO = new MessageDTO(MessageType.SUCCESS,
						MessageReader.getProperty("user.fetch.success"));
				respModel = new ResponseModel(Constants.RESULT_SUCCESS_CODE, msgDTO);
				respModel.setDataModel(projectData);
		        return respModel;
				}
				catch(Exception e){
					 throw new ApplicationException(MessageReader.getProperty("user.fetch.fail"));
				}
				
	}
	
	public UserManagementDTO userManagementFilterObject(String searchAttribute){
		UserManagementDTO userManagementDTO = new UserManagementDTO();
		userManagementDTO.setUsername(searchAttribute);
		userManagementDTO.setFirstName(searchAttribute);
		return userManagementDTO;
	}

}
