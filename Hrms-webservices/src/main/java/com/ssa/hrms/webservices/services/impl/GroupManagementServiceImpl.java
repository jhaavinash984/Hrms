package com.ssa.hrms.webservices.services.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;

import com.ssa.hrms.common.MessageType;
import com.ssa.hrms.common.exception.EntityExistException;
import com.ssa.hrms.dto.model.MessageDTO;
import com.ssa.hrms.dto.model.ResponseModel;
import com.ssa.hrms.security.domain.LoggedInUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ssa.hrms.common.ApplicationProperty;
import com.ssa.hrms.dao.GroupRepository;
import com.ssa.hrms.dao.specifications.GroupManagementSpecification;
import com.ssa.hrms.dto.model.GroupModel;
import com.ssa.hrms.dto.mysqlentities.Group;
import com.ssa.hrms.security.util.Encryptor;
import com.ssa.hrms.webservices.services.GroupManagementService;

import coms.ssa.hrms.dto.entities.key.CreateUpdateDetails;

import javax.persistence.EntityNotFoundException;

@Service
public class GroupManagementServiceImpl implements GroupManagementService {
	
	@Autowired
	Encryptor encryptor;
	
	@Autowired
	GroupRepository groupRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public ResponseModel saveGroup(GroupModel groupModel, LoggedInUser loggedInUser){
		Group grpNew = new Group();
		LocalDate estbDate = null;
		try{
		    estbDate = LocalDate.parse(groupModel.getEstablishmentDate());
		}catch(Exception e){
			System.out.println("date parsing issue while adding group "+e.getMessage());
		}
		ZonedDateTime nowZonedDate = ZonedDateTime.now(TimeZone.getTimeZone(ApplicationProperty.UTCTimeZone).toZoneId());
		LocalDateTime now = nowZonedDate.toLocalDateTime();
		CreateUpdateDetails createUpdateDetails = new CreateUpdateDetails();
		Group groupExist = groupRepo.findByGroupNameIgnoreCase(groupModel.getGroupName());
		if(groupExist == null){
			grpNew.setGroupName(groupModel.getGroupName());
			grpNew.setGroupDescription(groupModel.getGroupDescription());
			grpNew.setContactPerson(groupModel.getContactPerson());
			grpNew.setMobileNumber(groupModel.getMobileNumber());
			grpNew.setEmail(groupModel.getEmail());
			grpNew.setEstablishmentDate(estbDate);
			grpNew.setAddress(groupModel.getAddress());
			createUpdateDetails.setCreatedBy(loggedInUser.getId());
			createUpdateDetails.setCreatedDate(now);
			
		}else{
			throw new EntityExistException("Group Already exist "+groupModel.getGroupName());
			/*grpNew = groupRepo.findByGroupId(Integer.parseInt(encryptor.decrypt(groupModel.getGroupId())));
			grpNew.setGroupDescription(groupModel.getGroupDescription());
			grpNew.setContactPerson(groupModel.getContactPerson());
			grpNew.setMobileNumber(groupModel.getMobileNumber());
			grpNew.setEmail(groupModel.getEmail());
			grpNew.setEstablishmentDate(estbDate);
			grpNew.setAddress(groupModel.getAddress());
			createUpdateDetails.setUpdatedBy(userId);
			createUpdateDetails.setUpdatedDate(now);*/
		}
		grpNew.setCreateUpdateDetails(createUpdateDetails);
		Group group = groupRepo.save(grpNew);
		groupModel.setGroupId(encryptor.encrypt(String.valueOf(group.getGroupId())));
		ResponseModel responseModel = new ResponseModel(200,groupModel,new MessageDTO(MessageType.FAILED,"Group Created",null));
		return responseModel;
	}

	@Override
	public ResponseModel updateGroup(String groupId,GroupModel groupModel, LoggedInUser loggedInUser){
		LocalDate estbDate = null;
		try{
			estbDate = LocalDate.parse(groupModel.getEstablishmentDate());
		}catch(Exception e){
			System.out.println("date parsing issue while updating group "+e.getMessage());
		}
		ZonedDateTime nowZonedDate = ZonedDateTime.now(TimeZone.getTimeZone(ApplicationProperty.UTCTimeZone).toZoneId());
		LocalDateTime now = nowZonedDate.toLocalDateTime();
		CreateUpdateDetails createUpdateDetails = new CreateUpdateDetails();
		Group groupExist = groupRepo.findByGroupId(Integer.parseInt(encryptor.decrypt(groupId)));
		if(groupExist != null){
			groupExist.setGroupDescription(groupModel.getGroupDescription());
			groupExist.setContactPerson(groupModel.getContactPerson());
			groupExist.setMobileNumber(groupModel.getMobileNumber());
			groupExist.setEmail(groupModel.getEmail());
			groupExist.setEstablishmentDate(estbDate);
			groupExist.setAddress(groupModel.getAddress());
			createUpdateDetails.setUpdatedBy(loggedInUser.getId());
			createUpdateDetails.setUpdatedDate(now);

		}else{
			throw new EntityNotFoundException("Group Not Found "+groupModel.getGroupName());
		}
		groupExist.setCreateUpdateDetails(createUpdateDetails);
		groupRepo.save(groupExist);
		ResponseModel responseModel = new ResponseModel(200,groupModel,new MessageDTO(MessageType.SUCCESS,"Group Updated",null));
		return responseModel;
	}

	@Override
	public ResponseModel deleteGroup(String groupId){
		Group group = groupRepo.findByGroupId(Integer.parseInt(encryptor.decrypt(groupId)));
		if(group != null){
			groupRepo.delete(group);
		}else{
			throw new EntityNotFoundException("Group Not Found");
		}
		ResponseModel responseModel = new ResponseModel(200,group,new MessageDTO(MessageType.SUCCESS,"Group Deleted",null));
		return responseModel;

	}
	
	
	@Override
	public Map<String,Object> fetchGroup(GroupManagementSpecification grpMgmtSpec, PageRequest pageRequestObj){
		Map<String, Object> totalData = new HashMap<>();
		List<GroupModel> grpListToReturn = new ArrayList<>();
		List<Group> allGroups = grpMgmtSpec != null ? groupRepo.findAll(grpMgmtSpec, pageRequestObj).getContent() : groupRepo.findAll(pageRequestObj).getContent();
		if(!allGroups.isEmpty()){
		   grpListToReturn = allGroups.stream().map(value -> modelMapper.map(value, GroupModel.class)).collect(Collectors.toList());
		   grpListToReturn.forEach(value -> value.setGroupId(encryptor.encrypt(value.getGroupId())));
		   totalData.put("data",grpListToReturn);
		   totalData.put("length",grpMgmtSpec != null ? groupRepo.count(grpMgmtSpec) : groupRepo.count());
		}
		return totalData;
		
	}


}
