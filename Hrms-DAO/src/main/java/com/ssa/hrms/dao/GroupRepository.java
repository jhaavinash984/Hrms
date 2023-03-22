package com.ssa.hrms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.ssa.hrms.dto.mysqlentities.Group;
import com.ssa.hrms.dto.mysqlentities.User;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer>,JpaSpecificationExecutor<Group>{
       
	public Group findByGroupId(Integer groupId);

	public Group findByGroupNameIgnoreCase(String groupName);
}
