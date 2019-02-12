package com.sunhill.banking.domain.util;

import java.math.BigDecimal;
import java.util.Optional;

import org.apache.commons.lang3.BooleanUtils;

import com.sunhill.banking.domain.Account;
import com.sunhill.banking.service.exception.BankingException;
import com.sunhill.banking.service.exception.InvalidAmountException;

/**
 * 
 * @author mehmetali
 * 
 * to validate given parameters
 * 
 */
public final class BankingValidation {
	
	private BankingValidation(){}
	
	/**
	 * 
	 * to check null value and if it is valid, return amount
	 * 
	 * @param amount
	 * @return BigDecimal
	 */
	public static BigDecimal checkNullAndReturnValueForGivenAttribute(final BigDecimal amount) {
		Optional<BigDecimal> amountOptional = Optional.ofNullable(amount);
		if(!amountOptional.isPresent())
			throw new InvalidAmountException(BankingMessageUtil.GIVEN_AMOUNT_MUST_NOT_BE_NULL.getValue());
		
		return amountOptional.get();
	}
	
	/**
	 * 
	 * to compare given accounts. if both aren't same, return true
	 * 
	 * @param firstAccount
	 * @param secondAccount
	 * @return boolean
	 */
	public static boolean isGivenAccountsAreNotSame(Account firstAccount, Account secondAccount) {
		if(firstAccount == null || secondAccount == null)
			throw new BankingException(BankingMessageUtil.GIVEN_ACCOUNT_MUST_NOT_BE_NULL.getValue());
		
		return BooleanUtils.toBoolean(!firstAccount.getOwner().equals(secondAccount.getOwner()));
	}
	
	/**
	 * 
	 * to calculate given amount, is greater than equal zero.
	 * 
	 * @param amount
	 * @return BigDecimal
	 */
	public static BigDecimal calculateGivenAmountGraterThanEqualZero(BigDecimal amount) {
		BigDecimal calcAmount = checkNullAndReturnValueForGivenAttribute(amount);
		if(calcAmount.compareTo(BigDecimal.ZERO) < 0)
			throw new BankingException(BankingMessageUtil.GIVEN_AMOUNT_MUST_NOT_BE_LESS_THAN_ZERO.getValue());
		
		return calcAmount;
	}

	/**
	 * to validate given amount, is greater than zero
	 * 
	 * @param amount
	 * @return boolean
	 */
	public static boolean isValidGivenAmountGraterThanZero(BigDecimal amount) {
		return (checkNullAndReturnValueForGivenAttribute(amount).compareTo(BigDecimal.ZERO) > 0);
	}
	
}
