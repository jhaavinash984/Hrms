package com.ssa.hrms.dao.specifications;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.data.jpa.domain.Specification;

import com.ssa.hrms.dto.model.ProjectDTO;
import com.ssa.hrms.dto.model.UserManagementDTO;
import com.ssa.hrms.dto.mysqlentities.Role;
import com.ssa.hrms.dto.mysqlentities.User;

public class UserManagementSpecification implements Specification<User> {
	private UserManagementDTO filter;

	public UserManagementSpecification(UserManagementDTO filter) {
		super();
		this.filter = filter;
	}

	@Override
	public Predicate toPredicate(Root<User> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
		
		List<Predicate> predicates = new ArrayList<>();
		List<Predicate> searchFilterPredicates = new ArrayList<>();
		if(filter.getGroupId() != null){
			predicates.add(cb.equal(root.<Integer>get("groupId"), filter.getGroupId()));
		}
		if(filter.getSystemRoleId() != null){
			 cq.distinct(true);
			 Subquery<Role> roleSubQuery = cq.subquery(Role.class);
	         Root<Role> roles = roleSubQuery.from(Role.class);
	         Expression<Collection<Role>> userRole = roles.get("user");
	         roleSubQuery.select(roles);
	         roleSubQuery.where(cb.equal(roles.get("id"), 2), cb.isMember(root.get("id"), userRole));
	         predicates.add(cb.exists(roleSubQuery));
		}
		if (filter.getUsername() != null) {
			String containsLikePattern = getContainsLikePattern(filter.getUsername());
			searchFilterPredicates.add(cb.like(cb.lower(root.<String>get("username")), containsLikePattern));
		}
		Predicate mandatoryPredicates = null;
		Predicate searchPredicates = null;
		if (!predicates.isEmpty()) {
			mandatoryPredicates = cb.and(predicates.toArray(new Predicate[] {}));
		}
		if (!searchFilterPredicates.isEmpty()) {
			searchPredicates = cb.or(searchFilterPredicates.toArray(new Predicate[] {}));
		}
		if (null == mandatoryPredicates) {
			return searchPredicates;
		} else if (null == searchPredicates) {
			return mandatoryPredicates;
		} else {
			return cb.and(mandatoryPredicates, searchPredicates);
		}
	}

	private static String getContainsLikePattern(String searchTerm) {
		if (searchTerm == null || searchTerm.isEmpty()) {
			return "%";
		} else {
			return "%" + searchTerm.toLowerCase() + "%";
		}
	}
	

}
