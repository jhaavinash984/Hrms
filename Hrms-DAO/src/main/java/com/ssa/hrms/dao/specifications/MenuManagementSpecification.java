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
import com.ssa.hrms.dto.model.MenuModel;
import com.ssa.hrms.dto.mysqlentities.Group;
import com.ssa.hrms.dto.mysqlentities.Menu;

public class MenuManagementSpecification implements Specification<Menu> {
	
    private MenuModel filter;
	
	public MenuManagementSpecification(MenuModel menu){
		super();
		this.filter = menu;
	}
	
	
	@Override
	public Predicate toPredicate(Root<Menu> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
		
		List<Predicate> predicates = new ArrayList<>();
		List<Predicate> searchFilterPredicates = new ArrayList<>();
		if(filter.getId() != null){
			predicates.add(cb.equal(root.<Integer>get("id"), filter.getId()));
		}
		if (filter.getMenuName() != null) {
			Path<String> menuName = root.<String>get("menuName");
			String containsLikePattern = getContainsLikePattern(filter.getMenuName());
			searchFilterPredicates.add(cb.like(cb.lower(menuName), containsLikePattern));
		}
		if (filter.getMenuLink() != null) {
			Path<String> menuLink = root.<String>get("menuLink");
			String containsLikePattern = getContainsLikePattern(filter.getMenuLink());
			searchFilterPredicates.add(cb.like(cb.lower(menuLink), containsLikePattern));
		}
		if (filter.getIconName() != null) {
			Path<String> iconName = root.<String>get("iconName");
			String containsLikePattern = getContainsLikePattern(filter.getIconName());
			searchFilterPredicates.add(cb.like(cb.lower(iconName), containsLikePattern));
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
