package com.ssa.hrms.webservices.services.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ssa.hrms.common.ApplicationProperty;
import com.ssa.hrms.dao.MenuRepository;
import com.ssa.hrms.dao.SubMenuRepository;
import com.ssa.hrms.dao.specifications.GroupManagementSpecification;
import com.ssa.hrms.dao.specifications.MenuManagementSpecification;
import com.ssa.hrms.dto.model.GroupModel;
import com.ssa.hrms.dto.model.MenuModel;
import com.ssa.hrms.dto.model.Source;
import com.ssa.hrms.dto.model.SubMenuModel;
import com.ssa.hrms.dto.mysqlentities.Group;
import com.ssa.hrms.dto.mysqlentities.Menu;
import com.ssa.hrms.dto.mysqlentities.SubMenu;
import com.ssa.hrms.security.domain.LoggedInUser;
import com.ssa.hrms.security.util.Encryptor;
import com.ssa.hrms.webservices.helper.CommonFunction;
import com.ssa.hrms.webservices.services.MenuSubMenuService;

import coms.ssa.hrms.dto.entities.key.CreateUpdateDetails;

@Service
public class MenuSubMenuServiceImpl implements MenuSubMenuService  {
	
	@Autowired
	MenuRepository menuRepo;
	
	@Autowired
	SubMenuRepository subMenuRepo;
	
	@Autowired
	Encryptor encryptor;
	
	@Autowired 
	CommonFunction commonFunction;
	
	@Autowired
	private ModelMapper modelMapper;

	
	/**@Override
	public void saveFeatureBasedOnRole(Map<String, Map<String, Object>> featureBasedOnRoleObj, LoggedInUser loggedInUser){
		String roleId = featureBasedOnRoleObj.entrySet().iterator().next().getKey();
		CreateUpdateDetails creatorInfo = commonFunction.creatorInfo(loggedInUser);
		Map<String, Object> menuAndSubmenu = featureBasedOnRoleObj.entrySet().iterator().next().getValue();
		for(Map.Entry<String, Object> entry : menuAndSubmenu.entrySet()){
			RoleMenu roleMenu = new RoleMenu();
			roleMenu.setOrgRoleId(Integer.parseInt(encryptor.decrypt(roleId)));
			roleMenu.setMenuId(Integer.parseInt(encryptor.decrypt(entry.getKey())));
			roleMenu.setCreateUpdateDetails(creatorInfo);
			roleMenuRepo.save(roleMenu);
			if(entry.getValue()!=null){
				List<String> subMenu = (List<String>)entry.getValue();
				subMenu.forEach(value ->{
					RoleSubMenu roleSubMenu = new RoleSubMenu();
					roleSubMenu.setOrgRoleId(Integer.parseInt(encryptor.decrypt(roleId)));
					roleSubMenu.setSubMenuId(Integer.parseInt(encryptor.decrypt(value)));
					roleSubMenu.setCreateUpdateDetails(creatorInfo);
					roleSubMenuRepo.save(roleSubMenu);
				});
			}
			
			
		}
	}**/
	
	@Override
	public List<MenuModel> fetchAllFeature(){

		List<MenuModel> finalListToreturn = new ArrayList<>();
		List<Menu> menuClct = menuRepo.findAll();
		//List<RoleMenu> roleMenuList = roleMenuRepo.findByOrgRoleId(loggedInUser.getOrganisationRoleId());
		//List<RoleMenu> roleMenuList = null;
	   // Integer sourceId = loggedInUser.getSource().equals("hrms") ? Source.hrms.getSourceId() : Source.hrmsCustomer.getSourceId();
		//roleMenuList = roleMenuRepo.findByOrgRoleIdAndSourceId(loggedInUser.getOrganisationRoleId(),sourceId);
		//List<RoleSubMenu> subMenuBasedOnrole = roleSubMenuRepo.findByOrgRoleId(loggedInUser.getOrganisationRoleId());
		//List<RoleSubMenu> subMenuBasedOnrole = roleSubMenuRepo.findByOrgRoleIdAndSourceId(loggedInUser.getOrganisationRoleId(), sourceId);
		//List<Integer> subMenuIds = subMenuBasedOnrole.stream().map(value -> value.getSubMenuId()).collect(Collectors.toList());
		if(!menuClct.isEmpty()){
			for(Menu menu : menuClct){
				MenuModel menuModel = new MenuModel();
				menuModel.setId(encryptor.encrypt(String.valueOf(menu.getId())));
				menuModel.setMenuName(menu.getMenuName());
				menuModel.setMenuLink(menu.getMenuLink());
				List<SubMenu> subMenu = menu.getSubMenu();
				List<SubMenuModel> subMenuModel = new ArrayList<>();
				if(!subMenu.isEmpty()){
					subMenuModel = subMenu.stream().map(value -> modelMapper.map(value, SubMenuModel.class)).collect(Collectors.toList());
				}
				menuModel.setSubMenu(subMenuModel); 
				finalListToreturn.add(menuModel);
			}
			
		}
		return finalListToreturn;
	}
	
	@Override
	public Map<String,Object> fetchMenu(MenuManagementSpecification menuMgmtSpec, PageRequest pageRequestObj){
		Map<String, Object> totalData = new HashMap<>();
		List<MenuModel> menuListToReturn = new ArrayList<>();
		List<Menu> allMenus = menuMgmtSpec != null ? menuRepo.findAll(menuMgmtSpec, pageRequestObj).getContent() : menuRepo.findAll(pageRequestObj).getContent();
		if(!allMenus.isEmpty()){
		   menuListToReturn = allMenus.stream().map(value -> modelMapper.map(value, MenuModel.class)).collect(Collectors.toList());
		   totalData.put("data",menuListToReturn);
		   totalData.put("length",menuMgmtSpec != null ? menuRepo.count(menuMgmtSpec) : menuRepo.count());
		}
		return totalData;
		
	}
	
	@Override
	public void saveOrUpdateMenu(MenuModel menuModel){
		Menu menuNew = new Menu();
		if(menuModel.getId() == null){
			menuNew = modelMapper.map(menuModel, Menu.class);
		}else{
			menuNew = menuRepo.findById(Integer.parseInt(menuModel.getId())).get();
			menuNew.setMenuName(menuModel.getMenuName());
			menuNew.setMenuLink(menuModel.getMenuLink());
			menuNew.setIconName(menuModel.getIconName());
		}
		menuRepo.save(menuNew);	
	}
	

}
