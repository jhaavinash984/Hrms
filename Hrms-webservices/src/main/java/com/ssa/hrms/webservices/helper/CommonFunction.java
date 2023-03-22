package com.ssa.hrms.webservices.helper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ssa.hrms.dto.model.PageRequestModel;
import com.ssa.hrms.security.domain.LoggedInUser;

import coms.ssa.hrms.dto.entities.key.CreateUpdateDetails;

import org.springframework.data.domain.Sort.Direction;

@Service
public class CommonFunction {
	
	public LoggedInUser loggedInUser(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    LoggedInUser currentUser = (LoggedInUser)auth.getPrincipal();
	    return currentUser;
	}
	
	public PageRequest getPageRequestObject(PageRequestModel pageRequestModel){
		PageRequest pageRequest = null;
		if("ascending".equals(pageRequestModel.getDirection())){
			pageRequest =PageRequest.of(pageRequestModel.getPageNumber(), pageRequestModel.getMaxRecordsFetchPerPage(), Direction.ASC, pageRequestModel.getSortingAttribute());
		}
		else{
			pageRequest = PageRequest.of(pageRequestModel.getPageNumber(), pageRequestModel.getMaxRecordsFetchPerPage(), Direction.DESC, pageRequestModel.getSortingAttribute());
		}
		return pageRequest;
	}
	
	public CreateUpdateDetails creatorInfo(LoggedInUser loggedInUser){
		
		CreateUpdateDetails creatorInfo = new CreateUpdateDetails();
		creatorInfo.setCreatedBy(loggedInUser.getId());
		 LocalDateTime currentDateAndTime = LocalDateTime.ofInstant(new Date().toInstant(), 
                 ZoneId.systemDefault());
		creatorInfo.setCreatedDate(currentDateAndTime);
		return creatorInfo;
		
	}

}
