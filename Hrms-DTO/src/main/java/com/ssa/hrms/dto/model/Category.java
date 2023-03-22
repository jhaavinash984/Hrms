package com.ssa.hrms.dto.model;

public enum Category {
	
	ORGANISATION(1),DEPARTMENT(2),PROJECT(3);
	 private int categoryCode;
	 Category(int categoryCode)
		{
			this.categoryCode = categoryCode;
		}
		
	  public int getCategoryCode() {
			return categoryCode;
		}
	  
	  public static int getCategoryCodeFromValue(String category){
		  Category categories[] = Category.values();
		  int categoryCode=0;
	      for(Category cat : categories) {
	    	 if(cat.name().equals(cat))
	           categoryCode = cat.getCategoryCode();
	      }
	      return categoryCode; 
	  }
	  public String getCategoryFromCategoryCode(int categoryCode){
		  Category categories[] = Category.values();
		  String category=null;
		  for(Category cat : categories) {
	    	 if(cat.getCategoryCode()==categoryCode)
	           category = cat.name();
	      }
	      return category; 
	  }

}
