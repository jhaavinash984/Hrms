package com.ssa.hrms.dto.model;

import com.ssa.hrms.common.exception.ApplicationException;

public enum Source {
	
	hrms(0),hrmsCustomer(1),hrmsSub(2);
	
	private final int sourceID;

	Source(final int newValue) {
		sourceID = newValue;
    }

    public int getSourceId() { 
    	return sourceID; 
    }
    

	public static Source fromValue(int sourceId) throws ApplicationException
	{
		for(Source value : values())
            if(value.getSourceId() == sourceId) return value; 
		throw new ApplicationException("No such user status defined.");
	}
}
