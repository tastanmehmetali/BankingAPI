package com.sunhill.banking.domain;

import java.math.BigDecimal;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sunhill.banking.domain.util.BankingMessageUtil;
import com.sunhill.banking.domain.util.BankingValidation;
import com.sunhill.banking.service.exception.BankingException;
import com.sunhill.banking.service.exception.InvalidOwnerException;

/**
 * 
 * @author mehmetali
 *
 * Each account has depositable and withdrawable features. 
 * Additionally, to get account info
 * 
 */
public abstract class Account implements IDepositable, IWithdrawable {

	private static final Logger logger = LoggerFactory.getLogger(Account.class);
	private String owner;
	private BigDecimal balance;

	/**
	 * 
	 * owner is account name and balance is account balance.
	 * 
	 * @param owner
	 * @param balance
	 */
	public Account(final String owner, final BigDecimal balance) {
		if(owner == null)
			throw new InvalidOwnerException(BankingMessageUtil.OWNER_INFO.getValue());
		
		if(StringUtils.EMPTY.equals(owner))
			throw new InvalidOwnerException(BankingMessageUtil.OWNER_INFO_MUSS_NOT_BE_EMPTY.getValue());
		
		this.owner = owner;
		this.balance = BankingValidation.calculateGivenAmountGraterThanEqualZero(balance);
		logger.debug("Created owner: {} with balance {}", owner, balance);
	}
	
	public String getOwner() {
		return owner;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	protected void setBalance(BigDecimal balance) {
		Optional<BigDecimal> balanceOptional = Optional.ofNullable(balance);
		if(!balanceOptional.isPresent())
			throw new BankingException(BankingMessageUtil.GIVEN_AMOUNT_MUST_NOT_BE_NULL.getValue());
		
		this.balance = balanceOptional.get();
	}

	/**
	 * 
	 * given amount is added the balance
	 * 
	 * @param amount
	 * 
	 */
	@Override
	public synchronized void deposit(BigDecimal amount) {
		logger.debug("deposit --- amount: {}", amount);
		amount = BankingValidation.calculateGivenAmountGraterThanEqualZero(amount);
		BigDecimal depoistAmount = getBalance().add(amount);
		setBalance(depoistAmount);
	}
	
	@Override
	public String toString() {
		return String.format(BankingMessageUtil.ACCOUNT_INFO.getValue(), owner, balance);
	}

}
