package com.ssa.hrms.dto.model;

public enum Gender {
	
	 MALE('M'), FEMALE('F'),OTHER('O');
	 private char genderCode;
	 Gender(char genderCode)
		{
			this.genderCode = genderCode;
		}
		
	  public char getGenderCode() {
			return genderCode;
		}
	  
	  public static char getGenderCodeFromValue(String gender){
		  Gender genders[] = Gender.values();
		  char genCode='O';
	      for(Gender gen : genders) {
	    	 if(gen.name().equals(gender))
	           genCode = gen.getGenderCode();
	      }
	      return genCode; 
	  }
	  public String getGenderFromGenderCode(char genderCode){
		  Gender genders[] = Gender.values();
		  String gender=null;
	      for(Gender gen : genders) {
	    	 if(gen.getGenderCode()==genderCode)
	           gender = gen.name();
	      }
	      return gender; 
	  }

}
