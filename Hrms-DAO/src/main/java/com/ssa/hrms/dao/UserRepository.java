package com.ssa.hrms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import com.ssa.hrms.dto.mysqlentities.User;
@Repository
public interface UserRepository extends JpaRepository<User, Integer>,JpaSpecificationExecutor<User> {
	
	User findByUsername(String username);
	User findByUsernameAndIsActive(String username,Integer isActive);

}
