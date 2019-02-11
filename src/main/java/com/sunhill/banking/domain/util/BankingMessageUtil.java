package com.sunhill.banking.domain.util;

public enum BankingMessageUtil {

	OWNER_INFO("Owner (Name) can not be null!"),
	ACCOUNT_IS_NOT_EXIST("Account is not exist!"),
	ACCOUNT_INFO("Created account, owner: %s with balance: %s"), 
	GIVEN_ACCOUNT_IS_NOT_VALID("Given Account is not valid!"),
	TRANSFERED_ACCOUNT_MUST_NOT_NULL("Given transfered account must not be null!"), 
	GIVEN_AMOUNT_MUST_NOT_BE_NULL("Given amount must not be null"), 
	GIVEN_AMOUNT_MUST_NOT_BE_LESS_THAN_ZERO("Given amount must not be less than zero"), 
	GIVEN_INTEREST_RATE_MUST_NOT_BE_LESS_THAN_ZERO("Given interest rate must not be less than zero"), 
	WANTS_TO_WITHDRAW_AMOUNT_MUST_NOT_BE_LESS_THAN_BALANCE("Wants to withdraw amount must not be less than the balance"), 
	OWNER_INFO_MUSS_NOT_BE_EMPTY("Owner must not be empty!"), 
	WANTS_TO_WITHDRAW_AMOUNT_MUST_NOT_BE_LESS_THAN_OVERDRAFT("Wants to withdraw amount must not be less than the overdraft"), 
	GIVEN_ACCOUNTS_ARE_SAME("Given accounts are same!"), 
	GIVEN_OVERDRAFT_MUST_NOT_BE_GREATER_THAN_ZERO("Given overdraft mus not be greater than zero."), 
	GIVEN_PERIOD_MONTHLY_MUST_BE_GREATER_THAN_ZERO_FOR_CALCULATE_INTEREST("Given period monthly must be greater than zero for calculate interest."), 
	GIVEN_ACCOUNT_MUST_NOT_BE_NULL("Given Account must not be null!");
	
	private String value;

	BankingMessageUtil(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
}
