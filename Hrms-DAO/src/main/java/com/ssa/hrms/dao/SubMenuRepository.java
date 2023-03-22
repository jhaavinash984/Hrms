package com.ssa.hrms.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ssa.hrms.dto.mysqlentities.SubMenu;

public interface SubMenuRepository extends JpaRepository<SubMenu, Integer>{
	
	List<SubMenu> findByMenuId(Integer menuId);

}
