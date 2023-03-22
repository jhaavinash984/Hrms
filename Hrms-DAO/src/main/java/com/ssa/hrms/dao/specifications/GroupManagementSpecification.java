package com.ssa.hrms.dao.specifications;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.ssa.hrms.dto.model.GroupModel;
import com.ssa.hrms.dto.mysqlentities.Group;

public class GroupManagementSpecification implements Specification<Group>{
	
	private GroupModel filter;
	
	public GroupManagementSpecification(GroupModel group){
		super();
		this.filter = group;
	}
	
	
	@Override
	public Predicate toPredicate(Root<Group> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
		
		List<Predicate> predicates = new ArrayList<>();
		List<Predicate> searchFilterPredicates = new ArrayList<>();
		if(filter.getGroupId() != null){
			predicates.add(cb.equal(root.<Integer>get("groupId"), filter.getGroupId()));
		}
		if (filter.getGroupName() != null) {
			Path<String> groupName = root.<String>get("groupName");
			String containsLikePattern = getContainsLikePattern(filter.getGroupName());
			searchFilterPredicates.add(cb.like(cb.lower(groupName), containsLikePattern));
		}
		if (filter.getGroupDescription() != null) {
			Path<String> groupDescription = root.<String>get("groupDescription");
			String containsLikePattern = getContainsLikePattern(filter.getGroupDescription());
			searchFilterPredicates.add(cb.like(cb.lower(groupDescription), containsLikePattern));
		}
		if (filter.getContactPerson() != null) {
			Path<String> contactPerson = root.<String>get("contactPerson");
			String containsLikePattern = getContainsLikePattern(filter.getContactPerson());
			searchFilterPredicates.add(cb.like(cb.lower(contactPerson), containsLikePattern));
		}
		if (filter.getMobileNumber() != null) {
			Path<String> contactNumber = root.<String>get("mobileNumber");
			String containsLikePattern = getContainsLikePattern(filter.getMobileNumber());
			searchFilterPredicates.add(cb.like(cb.lower(contactNumber), containsLikePattern));
		}
		if (filter.getEmail() != null) {
			Path<String> email = root.<String>get("email");
			String containsLikePattern = getContainsLikePattern(filter.getEmail());
			searchFilterPredicates.add(cb.like(cb.lower(email), containsLikePattern));
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
