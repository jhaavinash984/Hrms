package com.ssa.hrms.dto.model;

import java.io.Serializable;

import com.ssa.hrms.common.MessageType;

public class MessageDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String message;
	private MessageType type;
	private String fieldName;

	public MessageDTO(MessageType type, String message) {
		this.message = message;
		this.type = type;
	}

	public MessageDTO(String errorMessage) {
		this(MessageType.ERROR, errorMessage);
	}

	public MessageDTO(MessageType type, String message, String fieldName) {
		this.message = message;
		this.type = type;
		this.fieldName = fieldName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

}
