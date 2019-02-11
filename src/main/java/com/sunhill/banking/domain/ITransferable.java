package com.sunhill.banking.domain;

import java.math.BigDecimal;

public interface ITransferable {
	
	/**
	 * 
	 * If the interface is implemented by other accounts except checkingAccount,
	 * it can be changed type of account.
	 * 
	 * @param account type of checkingAccount
	 * @param amount
	 */
	public void transfer(CheckingAccount account, BigDecimal amount);
}
