package com.ssa.hrms.common.exception;

public class EntityExistException extends RuntimeException{

    public EntityExistException(String message){
        super(message);
    }
}
