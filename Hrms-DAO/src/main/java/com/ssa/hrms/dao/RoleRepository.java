package com.ssa.hrms.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssa.hrms.dto.mysqlentities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	
	List<Role> findByRoleTypeId(Integer roleTypeId);
	
	

}
