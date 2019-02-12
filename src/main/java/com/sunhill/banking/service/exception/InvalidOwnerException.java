package com.sunhill.banking.service.exception;

/**
 * 
 * @author mehmetali
 *
 * to handle invalid given owner (name)
 * 
 */
public class InvalidOwnerException extends RuntimeException {

	private static final long serialVersionUID = -3915327951016774835L;

	public InvalidOwnerException(String message) {
		super(message);
	}

}
