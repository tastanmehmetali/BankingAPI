package com.sunhill.banking.service.exception;

/**
 * 
 * @author mehmetali
 *
 * to use banking different cases in the application process
 * 
 */
public class BankingException extends RuntimeException {

	private static final long serialVersionUID = 2373326399878501025L;

	public BankingException(String message) {
	     super(message);
	}
	
}
