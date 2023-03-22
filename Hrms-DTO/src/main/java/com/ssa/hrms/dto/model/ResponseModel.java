package com.ssa.hrms.dto.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResponseModel implements Serializable {
	private static final long serialVersionUID = -4984829012919923289L;
	private Integer statusCode;
	private List<MessageDTO> responseMessage = new ArrayList<MessageDTO>();
	private Object dataModel;

	public ResponseModel() {
	}

	public ResponseModel(Integer statusCode, Object dataModel) {
		this.statusCode = statusCode;
		this.dataModel = dataModel;
	}

	public ResponseModel(Integer statusCode, MessageDTO message) {
		this.statusCode = statusCode;
		responseMessage.add(message);
	}
	
	public ResponseModel(Integer statusCode, Object dataModel, MessageDTO message) {
		this.statusCode = statusCode;
		this.dataModel = dataModel;
		responseMessage.add(message);
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public Object getDataModel() {
		return dataModel;
	}

	public void setDataModel(Object dataModel) {
		this.dataModel = dataModel;
	}

	public List<MessageDTO> getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(List<MessageDTO> responseMessage) {
		this.responseMessage = responseMessage;
	}
}
