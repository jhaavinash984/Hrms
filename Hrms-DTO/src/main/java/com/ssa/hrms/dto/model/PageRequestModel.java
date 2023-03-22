package com.ssa.hrms.dto.model;

import lombok.Data;

@Data
public class PageRequestModel {
	
	private int pageNumber;
	
	private int maxRecordsFetchPerPage;
	
	private String sortingAttribute;
	
	private String searchText;
	
	private String direction;
	
	
	
}