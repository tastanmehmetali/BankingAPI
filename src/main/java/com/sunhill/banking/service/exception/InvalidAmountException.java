package com.sunhill.banking.service.exception;

/**
 * @author mehmetali
 * 
 * to handle invalid given amount
 *
 */
public class InvalidAmountException extends RuntimeException {

	private static final long serialVersionUID = -4634411480393057407L;

	public InvalidAmountException(String message) {
		super(message);
	}

}
