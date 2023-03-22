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
@Table(name="submenu")
public class SubMenu implements Serializable{
	
	    private static final long serialVersionUID = 1L;
		
		@Id
	    @Basic(optional = false)
	    @GeneratedValue(strategy=GenerationType.IDENTITY)
	    @Column(name = "ID")
	    private Integer id;
		
		@Basic(optional = false)
		@NotNull
		@Size(min = 2, max = 50)
		@Column(name = "SUBMENU_NAME")
	    private String subMenuName;
		
		@Column(name = "SUBMENU_LINK")
	    private String subMenuLink;
		
		@Size(min = 2, max = 50)
		@Column(name = "ICON_NAME")
	    private String iconName;
		
		
		@JoinColumn(name = "MENU_ID", referencedColumnName = "ID", insertable = false, updatable = false)
		@ManyToOne
		private Menu menu;
		
		@Column(name = "MENU_ID")
		private Integer menuId;
		
		@Embedded
	    private CreateUpdateDetails createUpdateDetails;
		
		/*@OneToMany(mappedBy = "roleSubMenuSubMenu")
		private List<RoleSubMenu> roleSubMenu;*/
		
		

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getSubMenuName() {
			return subMenuName;
		}

		public void setSubMenuName(String subMenuName) {
			this.subMenuName = subMenuName;
		}

		public String getSubMenuLink() {
			return subMenuLink;
		}

		public void setSubMenuLink(String subMenuLink) {
			this.subMenuLink = subMenuLink;
		}

		public Menu getMenu() {
			return menu;
		}

		public void setMenu(Menu menu) {
			this.menu = menu;
		}

		public Integer getMenuId() {
			return menuId;
		}

		public void setMenuId(Integer menuId) {
			this.menuId = menuId;
		}

		public CreateUpdateDetails getCreateUpdateDetails() {
			return createUpdateDetails;
		}

		public void setCreateUpdateDetails(CreateUpdateDetails createUpdateDetails) {
			this.createUpdateDetails = createUpdateDetails;
		}

		public String getIconName() {
			return iconName;
		}

		public void setIconName(String iconName) {
			this.iconName = iconName;
		}

		/*public List<RoleSubMenu> getRoleSubMenu() {
			return roleSubMenu;
		}

		public void setRoleSubMenu(List<RoleSubMenu> roleSubMenu) {
			this.roleSubMenu = roleSubMenu;
		}*/
		

}
