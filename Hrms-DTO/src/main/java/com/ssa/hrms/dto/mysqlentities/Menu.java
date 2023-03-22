package com.ssa.hrms.dto.mysqlentities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import coms.ssa.hrms.dto.entities.key.CreateUpdateDetails;

@Entity
@Table(name="menu")
public class Menu implements Serializable{
	
    private static final long serialVersionUID = 1L;
	
	@Id
    @Basic(optional = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 3, max = 50)
	@Column(name = "MENU_NAME")
    private String menuName;
	
	@Column(name = "MENU_LINK")
    private String menuLink;
	
	@Size(min = 2, max = 50)
	@Column(name = "ICON_NAME")
    private String iconName;
	
	@Embedded
    private CreateUpdateDetails createUpdateDetails;
	
	@OneToMany(mappedBy = "menu")
	private List<SubMenu> subMenu;
	
	{/*@OneToMany(mappedBy = "roleMenuMenu")
	private List<RoleMenu> roleMenu;*/}
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuLink() {
		return menuLink;
	}

	public void setMenuLink(String menuLink) {
		this.menuLink = menuLink;
	}


	public CreateUpdateDetails getCreateUpdateDetails() {
		return createUpdateDetails;
	}

	public void setCreateUpdateDetails(CreateUpdateDetails createUpdateDetails) {
		this.createUpdateDetails = createUpdateDetails;
	}

	public List<SubMenu> getSubMenu() {
		return subMenu;
	}

	public void setSubMenu(List<SubMenu> subMenu) {
		this.subMenu = subMenu;
	}

	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	/*public List<RoleMenu> getRoleMenu() {
		return roleMenu;
	}

	public void setRoleMenu(List<RoleMenu> roleMenu) {
		this.roleMenu = roleMenu;
	}*/
	

}
