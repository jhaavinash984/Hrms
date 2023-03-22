package com.ssa.hrms.dto.model;

import java.util.List;

import lombok.Data;

@Data
public class MenuModel {
	
	private String id;
	private String menuName;
	private String menuLink;
	private String iconName;
	private List<SubMenuModel> subMenu;

}
