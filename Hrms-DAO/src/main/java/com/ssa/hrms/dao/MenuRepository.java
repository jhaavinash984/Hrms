package com.ssa.hrms.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ssa.hrms.dto.mysqlentities.Group;
import com.ssa.hrms.dto.mysqlentities.Menu;

public interface MenuRepository extends JpaRepository<Menu, Integer>,JpaSpecificationExecutor<Menu>{
	
	Optional<Menu> findById(Integer id);

}
