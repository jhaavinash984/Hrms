package com.ssa.hrms.webservices.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssa.hrms.common.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.ssa.hrms.common.Constants;
import com.ssa.hrms.common.MessageReader;
import com.ssa.hrms.common.MessageType;
import com.ssa.hrms.dto.model.MessageDTO;
import com.ssa.hrms.dto.model.ResponseModel;

@ControllerAdvice
public class ExceptionHandlerController {
	
	@Autowired
	MessageReader messageReader;
	
	@ExceptionHandler(ApplicationException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	protected ResponseModel applicationRestServiceErrorHandler(ApplicationException ae) {
		ResponseModel respModel = new ResponseModel();
		respModel.setStatusCode(Constants.RESULT_FAILED_CODE);
		MessageDTO messageDTO = new MessageDTO(MessageType.ERROR, ae.getMessage());
		List<MessageDTO> errors = new ArrayList<MessageDTO>();
		errors.add(messageDTO);
		respModel.setResponseMessage(errors);
		return respModel;
	}
	



	/**@ExceptionHandler(ExpectationFailedException.class)
	@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
	protected ResponseModel applicationRestServiceErrorHandler(ExpectationFailedException ae) {
		ResponseModel respModel = new ResponseModel();
		respModel.setStatusCode(HttpStatus.EXPECTATION_FAILED.value());
		MessageDTO messageDTO = new MessageDTO(MessageType.ERROR, ae.getMessage());
		logger.error("Invalid Value Error: " + ae.getMessage());
		List<MessageDTO> errors = new ArrayList<MessageDTO>();
		errors.add(messageDTO);
		respModel.setResponseMessage(errors);
		return respModel;
	}**/

	@ExceptionHandler(UsernameNotFoundException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	protected ResponseModel applicationRestServiceErrorHandler(UsernameNotFoundException ae) {
		ResponseModel respModel = new ResponseModel();
		respModel.setStatusCode(HttpStatus.FORBIDDEN.value());
		MessageDTO messageDTO = new MessageDTO(MessageType.ERROR, ae.getMessage());
		List<MessageDTO> errors = new ArrayList<MessageDTO>();
		errors.add(messageDTO);
		respModel.setResponseMessage(errors);
		return respModel;
	}
	
	@ExceptionHandler(BadCredentialException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	protected ResponseModel applicationRestServiceErrorHandler(BadCredentialException ae) {
		ResponseModel respModel = new ResponseModel();
		respModel.setStatusCode(HttpStatus.FORBIDDEN.value());
		MessageDTO messageDTO = new MessageDTO(MessageType.ERROR, ae.getMessage());
		List<MessageDTO> errors = new ArrayList<MessageDTO>();
		errors.add(messageDTO);
		respModel.setResponseMessage(errors);
		return respModel;
	}
	
	@ExceptionHandler(UsernotactiveException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	protected ResponseModel applicationRestServiceErrorHandler(UsernotactiveException ae) {
		ResponseModel respModel = new ResponseModel();
		respModel.setStatusCode(HttpStatus.FORBIDDEN.value());
		MessageDTO messageDTO = new MessageDTO(MessageType.ERROR, ae.getMessage());
		List<MessageDTO> errors = new ArrayList<MessageDTO>();
		errors.add(messageDTO);
		respModel.setResponseMessage(errors);
		return respModel;
	}

	/**@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseModel processMissingRequestParameterError(MissingServletRequestParameterException ex) {
		String msg = MessageReader.getProperty("notnull", new Object[] { ex.getParameterName() });
		return new ResponseModel(Constants.RESULT_FAILED_CODE, new MessageDTO(MessageType.ERROR, msg));
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseBody
	public ResponseModel dataIntegrityViolationExceptionError(DataIntegrityViolationException ex) {
		MessageDTO errorMessage = new MessageDTO(MessageType.ERROR, ex.getMessage());
		ResponseModel responseModel = new ResponseModel(Constants.RESULT_FAILED_CODE, errorMessage);
		if (ex.getMessage().contains("constraint [unique]")) {
			errorMessage.setMessage(Constants.ERR_DETAILS_ALREADY_EXISTS);
		}
		return responseModel;
	}**/

	@ExceptionHandler(hrmsValidationException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseModel processValidationError(hrmsValidationException ex) {
		BindingResult result = ex.getBindingResult();
		List<FieldError> errors = result.getFieldErrors();
		return processFieldError(errors);
	}

	@ExceptionHandler(EntityExistException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.FOUND)
	public ResponseModel entityAlreadyExist(EntityExistException ex){
		ResponseModel reponseModel = new ResponseModel(HttpStatus.FOUND.value(), new MessageDTO(MessageType.FAILED,ex.getMessage(),null));
		return reponseModel;
	}

	@ExceptionHandler(EntityUpdate.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_MODIFIED)
	public ResponseModel entityUpdate(EntityExistException ex){
		ResponseModel reponseModel = new ResponseModel(HttpStatus.NOT_MODIFIED.value(), new MessageDTO(MessageType.FAILED,ex.getMessage(),null));
		return reponseModel;
	}

	/**@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseModel processValidationError(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		List<FieldError> errors = result.getFieldErrors();
		return processFieldError(errors);
	}**/

	private ResponseModel processFieldError(List<FieldError> errors) {
		ResponseModel responseModel = new ResponseModel();
		List<MessageDTO> responseMessages = new ArrayList<>();
		Map<String, MessageDTO> responseMessagesMap = new HashMap<>();
		for (FieldError error : errors) {
			String msg;
			if ("NotNull".equalsIgnoreCase(error.getCode())) {
				msg = MessageReader.getProperty("notnull", new Object[] { error.getField() });
			} else if ("Size".equalsIgnoreCase(error.getCode())) {
				if (error.getArguments().length > 2 && !(error.getArguments()[1].equals(error.getArguments()[2]))) {
					msg = MessageReader.getProperty("between.size",
							new Object[] { error.getField(), error.getArguments()[2], error.getArguments()[1] });
				} else {
					msg = MessageReader.getProperty("fixed.size",
							new Object[] { error.getField(), error.getArguments()[1] });
				}
			} else if ("Min".equalsIgnoreCase(error.getCode())) {
				msg = MessageReader.getProperty("fixed.size",
						new Object[] { error.getField(), error.getArguments()[1] });
			} else if ("Pattern".equalsIgnoreCase(error.getCode())) {
				msg = MessageReader.getProperty("invalid.field", new Object[] { error.getField() });
			} else {
				msg = error.getDefaultMessage();
			}
			if (!responseMessagesMap.containsKey(error.getField())) {
				responseMessagesMap.put(error.getField(),
						new MessageDTO(MessageType.FIELDERROR, msg, error.getField()));
			}
		}
		responseMessages.addAll(responseMessagesMap.values());
		responseModel.setStatusCode(Constants.RESULT_FAILED_CODE);
		responseModel.setResponseMessage(responseMessages);
		return responseModel;
	}


}
