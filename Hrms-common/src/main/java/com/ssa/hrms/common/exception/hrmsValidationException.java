package com.ssa.hrms.common.exception;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

/**
 * 
 * @author IKH4KOR
 *
 */

@SuppressWarnings("serial")
public class hrmsValidationException extends Exception {

	private final BindingResult bindingResult;

	public hrmsValidationException(BindingResult bindingResult) {
		this.bindingResult = bindingResult;
	}

	/**
	 * Return the results of the failed validation.
	 */
	public BindingResult getBindingResult() {
		return this.bindingResult;
	}

	@Override
	public String getMessage() {
		StringBuilder sb = new StringBuilder("Validation failed").append(", with ")
				.append(this.bindingResult.getErrorCount()).append(" error(s): ");
		for (ObjectError error : this.bindingResult.getAllErrors()) {
			sb.append("[").append(error).append("] ");
		}
		return sb.toString();
	}

}
