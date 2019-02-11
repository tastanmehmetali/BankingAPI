package com.sunhill.banking.domain;

import java.math.BigDecimal;

public interface IWithdrawable {
	
	/**
	 * to reduce given amount from the balance
	 * 
	 * @param amount
	 */
	public void withdraw(BigDecimal amount);
}
