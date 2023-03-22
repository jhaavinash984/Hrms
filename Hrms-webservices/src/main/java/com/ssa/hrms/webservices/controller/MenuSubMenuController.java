package com.ssa.hrms.webservices.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssa.hrms.common.Constants;
import com.ssa.hrms.common.MessageReader;
import com.ssa.hrms.common.MessageType;
import com.ssa.hrms.common.exception.ApplicationException;
import com.ssa.hrms.dao.specifications.GroupManagementSpecification;
import com.ssa.hrms.dao.specifications.MenuManagementSpecification;
import com.ssa.hrms.dto.model.GroupModel;
import com.ssa.hrms.dto.model.MenuModel;
import com.ssa.hrms.dto.model.MessageDTO;
import com.ssa.hrms.dto.model.PageRequestModel;
import com.ssa.hrms.dto.model.ResponseModel;
import com.ssa.hrms.security.domain.LoggedInUser;
import com.ssa.hrms.webservices.helper.CommonFunction;
import com.ssa.hrms.webservices.services.MenuSubMenuService;

@RestController
@RequestMapping("/webapi/Administration")
public class MenuSubMenuController extends ExceptionHandlerController {
	
	@Autowired
	private MenuSubMenuService menuSubMenuService;
	
	@Autowired 
	CommonFunction commonFunction;
	
	
	/**@PreAuthorize("hasAuthority('CUSTOMERADMIN')") 
	@RequestMapping(value = "/addFeaturesToRole", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseModel saveFeatureBasedOnRole(@RequestBody Map<String, Map<String, Object>> featureBasedOnRoleObj) {
		
	    LoggedInUser loggedInUser = commonFunction.loggedInUser();
		try{
			ResponseModel respModel=null;
			menuSubMenuService.saveFeatureBasedOnRole(featureBasedOnRoleObj,loggedInUser);
			MessageDTO msgDTO = new MessageDTO(MessageType.SUCCESS,
					MessageReader.getProperty("feature_based_on_role.create.success"));
			respModel = new ResponseModel(Constants.RESULT_SUCCESS_CODE, msgDTO);
	        return respModel;
			}
			catch(Exception e){
				 throw new ApplicationException(MessageReader.getProperty("feature_based_on_role.create.fail"));
			}
				
	}**/
	
	@PreAuthorize("hasAuthority('SYSTEMADMIN')")
	@RequestMapping(value = "/fetchFeaturesToRole", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseModel fetchFeatureBasedOnRole() {
		
	    LoggedInUser loggedInUser = commonFunction.loggedInUser();
		try{
			ResponseModel respModel=null;
			List<MenuModel> menu = menuSubMenuService.fetchAllFeature();
			MessageDTO msgDTO = new MessageDTO(MessageType.SUCCESS,
					MessageReader.getProperty("feature_based_on_role.fetch.success"));
			respModel = new ResponseModel(Constants.RESULT_SUCCESS_CODE, msgDTO);
			respModel.setDataModel(menu);
	        return respModel;
			}
			catch(Exception e){
				 throw new ApplicationException(MessageReader.getProperty("feature_based_on_role.fetch.fail"));
			}
				
	}
	
	@PreAuthorize("hasAuthority('SYSTEMADMIN')") 
	@RequestMapping(value = "/fetchMenu", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseModel fetchMenu(@RequestBody PageRequestModel  pageRequestModel) {
		
	    LoggedInUser loggedInUser = commonFunction.loggedInUser();
		try{
			ResponseModel respModel=null;
			MenuModel menuModel = new MenuModel();
			MenuManagementSpecification menuMgmtSpec = null;
			if((pageRequestModel.getSearchText() != null) && (!pageRequestModel.getSearchText().trim().isEmpty())){
				menuModel = menuFilterObject(pageRequestModel.getSearchText().trim());
				menuMgmtSpec = new MenuManagementSpecification(menuModel);
			}
			PageRequest pageRequestObj = commonFunction.getPageRequestObject(pageRequestModel);
			Map<String, Object> menuData = menuSubMenuService.fetchMenu(menuMgmtSpec, pageRequestObj);
			MessageDTO msgDTO = new MessageDTO(MessageType.SUCCESS,
					MessageReader.getProperty("menu.fetch.success"));
			respModel = new ResponseModel(Constants.RESULT_SUCCESS_CODE, msgDTO);
			respModel.setDataModel(menuData);
	        return respModel;
			}
			catch(Exception e){
				 throw new ApplicationException(MessageReader.getProperty("menu.fetch.fail"));
			}
				
	}
	
	@PreAuthorize("hasAuthority('SYSTEMADMIN')") 
	@RequestMapping(value = "/addMenu", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseModel saveMenu(@RequestBody MenuModel menuModel) {
		
	    LoggedInUser loggedInUser = commonFunction.loggedInUser();
		try{
			ResponseModel respModel=null;
			menuSubMenuService.saveOrUpdateMenu(menuModel);
			MessageDTO msgDTO = new MessageDTO(MessageType.SUCCESS,
					MessageReader.getProperty("menu.create.success"));
			respModel = new ResponseModel(Constants.RESULT_SUCCESS_CODE, msgDTO);
	        return respModel;
			}
			catch(Exception e){
				 throw new ApplicationException(MessageReader.getProperty("menu.create.fail"));
			}
				
	}
	
	public MenuModel menuFilterObject(String searchAttribute){
		MenuModel menuModel = new MenuModel();
		menuModel.setMenuName(searchAttribute);
		menuModel.setMenuLink(searchAttribute);
		menuModel.setIconName(searchAttribute);
		return menuModel;
	}
}
