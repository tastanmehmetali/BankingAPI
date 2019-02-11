package com.sunhill.banking.domain.util;

import java.math.BigDecimal;
import java.util.Optional;

import org.apache.commons.lang3.BooleanUtils;

import com.sunhill.banking.domain.Account;
import com.sunhill.banking.service.exception.BankingException;

public final class BankingValidation {
	
	private BankingValidation(){}
	
	public static BigDecimal checkNullAndReturnValueForGivenAttribute(final BigDecimal amount) {
		Optional<BigDecimal> amountOptional = Optional.ofNullable(amount);
		if(!amountOptional.isPresent())
			throw new NullPointerException(BankingMessageUtil.GIVEN_AMOUNT_MUST_NOT_BE_NULL.getValue());
		
		return amountOptional.get();
	}
	
	public static boolean isGivenAccountsAreNotSame(Account firstAccount, Account secondAccount) {
		if(firstAccount == null || secondAccount == null)
			throw new BankingException(BankingMessageUtil.GIVEN_ACCOUNT_MUST_NOT_BE_NULL.getValue());
		
		return BooleanUtils.toBoolean(!firstAccount.getOwner().equals(secondAccount.getOwner()));
	}
	
	public static BigDecimal calculateGivenAmountGraterThanEqualZero(BigDecimal amount) {
		BigDecimal calcAmount = checkNullAndReturnValueForGivenAttribute(amount);
		if(calcAmount.compareTo(BigDecimal.ZERO) < 0)
			throw new BankingException(BankingMessageUtil.GIVEN_AMOUNT_MUST_NOT_BE_LESS_THAN_ZERO.getValue());
		
		return calcAmount;
	}

	public static boolean isValidGivenAmountGraterThanZero(BigDecimal amount) {
		return (checkNullAndReturnValueForGivenAttribute(amount).compareTo(BigDecimal.ZERO) > 0);
	}
	
}
