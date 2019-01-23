package com.sunhill.banking.service;

import java.math.BigDecimal;

public interface IAccountService {

	public String deposit(String owner, BigDecimal amount);
	public boolean withdraw(String owner, BigDecimal amount);
	public String info(String owner);
	public boolean transfer(String fromOwner, String toOwner, BigDecimal amount);
	
}
