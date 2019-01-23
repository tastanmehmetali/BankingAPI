package com.sunhill.banking.domain;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.sunhill.banking.domain.util.BankingMessageUtil;
import com.sunhill.banking.domain.util.BankingValidation;

public abstract class Account implements IDepositable, IWithdrawable {

	private static final Logger logger = LoggerFactory.getLogger(Account.class);
	private String owner;
	private BigDecimal balance;

	public Account(final String owner, final BigDecimal balance) {
		Preconditions.checkNotNull(owner, BankingMessageUtil.OWNER_INFO.getValue());
		this.owner = owner;
		this.balance = BankingValidation.checkAndReturnValueForGivenAttribute(balance);
		logger.debug("Created owner {} with balance {}", owner, balance);
	}
	
	public String getOwner() {
		return owner;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	protected void setBalance(BigDecimal balance) {
		this.balance = Optional.fromNullable(balance).or(BigDecimal.ZERO);
	}

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
