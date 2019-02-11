package com.sunhill.banking.domain;

import java.math.BigDecimal;

public interface IDepositable {
	
	/**
	 * to add given amount the balance
	 * 
	 * @param amount
	 */
	public void deposit(BigDecimal amount);
}
