package com.sunhill.banking.domain.util;

import java.math.BigDecimal;
import java.util.Optional;

import org.apache.commons.lang3.BooleanUtils;

import com.sunhill.banking.domain.Account;

public final class BankingValidation {
	
	private BankingValidation(){}
	
	public static BigDecimal checkAndReturnValueForGivenAttribute(final BigDecimal amount) {
		return Optional.ofNullable(amount).orElse(BigDecimal.ZERO);
	}
	
	public static boolean isGivenAccountsAreNotSame(Account firstAccount, Account secondAccount) {
		return BooleanUtils.toBoolean(firstAccount != null && secondAccount != null 
				&& !firstAccount.getOwner().equals(secondAccount.getOwner()));
	}
	
	public static BigDecimal calculateGivenAmountGraterThanEqualZero(BigDecimal amount) {
		if(checkAndReturnValueForGivenAttribute(amount).compareTo(BigDecimal.ZERO) < 1)
			return BigDecimal.ZERO;
		return checkAndReturnValueForGivenAttribute(amount);
	}
	
}
