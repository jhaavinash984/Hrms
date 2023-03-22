package com.ssa.hrms.webservices.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.ssa.hrms.dao.RoleRepository;
import com.ssa.hrms.dao.UserRepository;
import com.ssa.hrms.dao.specifications.UserManagementSpecification;
import com.ssa.hrms.dto.model.Gender;
import com.ssa.hrms.dto.model.GroupModel;
import com.ssa.hrms.dto.model.ProfessionalInformationModel;
import com.ssa.hrms.dto.model.ProjectDTO;
import com.ssa.hrms.dto.model.UserManagementDTO;
import com.ssa.hrms.dto.model.UserManagementModel;
import com.ssa.hrms.dto.mysqlentities.PersonalInformation;
import com.ssa.hrms.dto.mysqlentities.Role;
import com.ssa.hrms.dto.mysqlentities.User;
import com.ssa.hrms.security.domain.LoggedInUser;
import com.ssa.hrms.security.util.Encryptor;
import com.ssa.hrms.webservices.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	Encryptor encryptor;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public User saveUser(UserManagementDTO user,LoggedInUser loggedInUser){
		User newUser=new User();
		newUser.setUsername(user.getUsername());
		String password=generateRandomPassword(10);
		excelSheetWrite(user.getUsername(),password);
		newUser.setPassword(bcryptEncoder.encode(password));
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setGroupId(user.getGroupId());
		newUser.setEmail(user.getEmail());
		newUser.setContactNumber(user.getMobileNumber());
		newUser.setGender(Gender.getGenderCodeFromValue(user.getGender()));
		newUser.setIsActive(user.getIsActive());
		//System Role Save
		List<Role> roles = roleRepo.findByRoleTypeId(loggedInUser.getRole().get(0).getRoleTypeId()-100);
		newUser.setRole(roles);
		userRepo.save(newUser);
		return newUser;
		
		
	}
	
	@Override
	public Map<String,Object> fetchUser(UserManagementSpecification userManagementSpec, PageRequest pageRequestObj,LoggedInUser loggedInUser){
		Map<String, Object> totalData = new HashMap<>();
		List<UserManagementModel> userListToReturn = new ArrayList<>();
		List<User> allUsers = userRepo.findAll(userManagementSpec, pageRequestObj).getContent();
		for(User user : allUsers){
			UserManagementModel userMgmtModel = modelMapper.map(user, UserManagementModel.class);
			userMgmtModel.setId(encryptor.encrypt(user.getId().toString()));
				userMgmtModel.setGroupId(encryptor.encrypt(user.getGroupId().toString()));
				userMgmtModel.setGroupName(user.getGroup().getGroupName());
			userListToReturn.add(userMgmtModel);
		}
	    totalData.put("data",userListToReturn);
	    totalData.put("length", userRepo.count(userManagementSpec));
		return totalData;
		
	}
	
	public String generateRandomPassword(int length){
		 //char[] splCharacter = (new String("^$*.[]{}()?-\"!@#%&/\\,><':;|_~`")).toCharArray();
		 char[] lowercaseLetter = (new String("abcdefghijklmnopqrstuvwxyz")).toCharArray();
		 char[] uppercaseLetter = (new String("ABCDEFGHIJKLMNOPQRSTUVWXYZ")).toCharArray();
		 char[] numbers = (new String("0123456789")).toCharArray();
		 char[] allCharacter = (new String("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789")).toCharArray();
		 Random rand = new SecureRandom();
         char[] password = new char[length];
         password[0] = lowercaseLetter[rand.nextInt(lowercaseLetter.length)];
		 password[1] = uppercaseLetter[rand.nextInt(uppercaseLetter.length)];
		 password[2] = numbers[rand.nextInt(numbers.length)];
		// password[3] = splCharacter[rand.nextInt(splCharacter.length)];   
         for (int i = 3; i < length; i++) {
            password[i] = allCharacter[rand.nextInt(allCharacter.length)];
         }

         for (int i = 0; i < password.length; i++) {
            int randomPosition = rand.nextInt(password.length);
            char temp = password[i];
            password[i] = password[randomPosition];
            password[randomPosition] = temp;
         }

		        return new String(password);
	}
	
	 public void excelSheetWrite(String username,String password) {
	        String excelFilePath = "C:/Users/Avinash/Desktop/credential.xlsx";
	         
	        try {
	            FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
	            Workbook workbook = WorkbookFactory.create(inputStream);
	 
	            Sheet sheet = workbook.getSheetAt(0);
	 
	            Object[][] bookData = {
	                    {username, password}
	            };
	 
	            int rowCount = sheet.getLastRowNum();
	 
	            for (Object[] aBook : bookData) {
	                Row row = sheet.createRow(++rowCount);
	 
	                int columnCount = 0;
	                 
	                Cell cell = row.createCell(columnCount);
	                cell.setCellValue(rowCount);
	                 
	                for (Object field : aBook) {
	                    cell = row.createCell(++columnCount);
	                    if (field instanceof String) {
	                        cell.setCellValue((String) field);
	                    } else if (field instanceof Integer) {
	                        cell.setCellValue((Integer) field);
	                    }
	                }
	 
	            }
	 
	            inputStream.close();
	            FileOutputStream outputStream = new FileOutputStream(excelFilePath);
	            workbook.write(outputStream);
	            workbook.close();
	            outputStream.close();
	             
	        } catch (IOException | EncryptedDocumentException
	                | InvalidFormatException ex) {
	            ex.printStackTrace();
	        }
	    }

}
