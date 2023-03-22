package com.ssa.hrms.webservices.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;

import com.ssa.hrms.dao.specifications.MenuManagementSpecification;
import com.ssa.hrms.dto.model.MenuModel;

public interface MenuSubMenuService {

	//void saveFeatureBasedOnRole(Map<String, Map<String, Object>> featureBasedOnRoleObj, LoggedInUser loggedInUser);

	public List<MenuModel> fetchAllFeature();

	public void saveOrUpdateMenu(MenuModel menuModel);

	public Map<String, Object> fetchMenu(MenuManagementSpecification menuMgmtSpec, PageRequest pageRequestObj);

}
