package com.ssa.hrms.webservices.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ssa.hrms.common.Constants;
import com.ssa.hrms.common.MessageReader;
import com.ssa.hrms.common.MessageType;
import com.ssa.hrms.common.exception.ApplicationException;
import com.ssa.hrms.dao.specifications.GroupManagementSpecification;
import com.ssa.hrms.dto.model.DepartmentModel;
import com.ssa.hrms.dto.model.GroupModel;
import com.ssa.hrms.dto.model.MenuModel;
import com.ssa.hrms.dto.model.MessageDTO;
import com.ssa.hrms.dto.model.PageRequestModel;
import com.ssa.hrms.dto.model.ResponseModel;
import com.ssa.hrms.security.domain.LoggedInUser;
import com.ssa.hrms.webservices.helper.CommonFunction;
import com.ssa.hrms.webservices.services.GroupManagementService;

@RestController
@RequestMapping("/webapi/Administration")
public class GroupManagementController extends ExceptionHandlerController{
	
	@Autowired 
	CommonFunction commonFunction;
	
	@Autowired
	GroupManagementService grpmgmtService;
	
	@PreAuthorize("hasAuthority('SYSTEMADMIN')") 
	@RequestMapping(value = "/addGroup", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseModel saveGroup(@RequestBody GroupModel groupModel) {
		
	    LoggedInUser loggedInUser = commonFunction.loggedInUser();
		try{
	        return grpmgmtService.saveGroup(groupModel,loggedInUser);
			}
			catch(Exception e){
				 throw new ApplicationException(MessageReader.getProperty("group.create.fail"));
			}
				
	}
	@PreAuthorize("hasAuthority('SYSTEMADMIN')")
	@RequestMapping(value="/updateGroup/{id}",method = RequestMethod.PUT,produces="application/json")
	public ResponseModel updateGroup(@PathVariable("id") String groupId, @RequestBody GroupModel groupModel){
		LoggedInUser loggedInUser = commonFunction.loggedInUser();
		return grpmgmtService.updateGroup(groupId,groupModel,loggedInUser);
	}

	@PreAuthorize("hasAuthority('SYSTEMADMIN')")
	@RequestMapping(value="/deleteGroup/{id}",method = RequestMethod.DELETE,produces="application/json")
	public ResponseModel deleteGroup(@PathVariable("id") String groupId){
		return grpmgmtService.deleteGroup(groupId);
	}
	
	@PreAuthorize("hasAuthority('SYSTEMADMIN')")
	@RequestMapping(value = "/fetchGroup", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseModel fetchGroup(@RequestBody PageRequestModel  pageRequestModel) {
		
	    LoggedInUser loggedInUser = commonFunction.loggedInUser();
		try{
			ResponseModel respModel=null;
			GroupModel groupModel = new GroupModel();
			GroupManagementSpecification grpMgmtSpec = null;
			if((pageRequestModel.getSearchText() != null) && (!pageRequestModel.getSearchText().trim().isEmpty())){
				groupModel = groupFilterObject(pageRequestModel.getSearchText().trim());
				grpMgmtSpec = new GroupManagementSpecification(groupModel);
			}
			PageRequest pageRequestObj = commonFunction.getPageRequestObject(pageRequestModel);
			Map<String, Object> groupData = grpmgmtService.fetchGroup(grpMgmtSpec, pageRequestObj);
			MessageDTO msgDTO = new MessageDTO(MessageType.SUCCESS,
					MessageReader.getProperty("group.fetch.success"));
			respModel = new ResponseModel(Constants.RESULT_SUCCESS_CODE, msgDTO);
			respModel.setDataModel(groupData);
	        return respModel;
			}
			catch(Exception e){
				 throw new ApplicationException(MessageReader.getProperty("group.fetch.fail"));
			}
				
	}
	
	public GroupModel groupFilterObject(String searchAttribute){
		GroupModel groupModel = new GroupModel();
		groupModel.setGroupName(searchAttribute);
		groupModel.setGroupDescription(searchAttribute);
		groupModel.setContactPerson(searchAttribute);
		groupModel.setEmail(searchAttribute);
		groupModel.setMobileNumber(searchAttribute);
		return groupModel;
	}
	

}
