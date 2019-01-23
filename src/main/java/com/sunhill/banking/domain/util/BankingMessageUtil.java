package com.sunhill.banking.domain.util;

public enum BankingMessageUtil {

	OWNER_INFO("Owner (Name) can not be null!"),
	ACCOUNT_IS_NOT_EXIST("Account is not exist!"),
	ACCOUNT_INFO("Created account: owner %s with balance  %s"), 
	GIVEN_ACCOUNT_IS_NOT_VALID("Given Account is not valid!"),
	TRANSFERED_ACCOUNT_CAN_NOT_NULL("Given transfered account can not be null!");
	
	private String value;

	BankingMessageUtil(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
}
