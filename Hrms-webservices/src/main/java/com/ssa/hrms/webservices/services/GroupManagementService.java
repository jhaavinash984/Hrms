package com.ssa.hrms.webservices.services;

import java.util.List;
import java.util.Map;

import com.ssa.hrms.dto.model.ResponseModel;
import com.ssa.hrms.security.domain.LoggedInUser;
import org.springframework.data.domain.PageRequest;

import com.ssa.hrms.dao.specifications.GroupManagementSpecification;
import com.ssa.hrms.dto.model.GroupModel;

public interface GroupManagementService {

	public ResponseModel saveGroup(GroupModel groupModel, LoggedInUser loggedInUser);

	public ResponseModel updateGroup(String userId,GroupModel groupModel, LoggedInUser loggedInUser);

	ResponseModel deleteGroup(String groupId);

	Map<String, Object> fetchGroup(GroupManagementSpecification grpMgmtSpec, PageRequest pageRequestObj);

}
