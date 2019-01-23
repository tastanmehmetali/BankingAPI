package com.sunhill.banking.domain;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sunhill.banking.domain.util.BankingValidation;

public class SavingAccount extends Account implements ICalculateInterest {

	private static final Logger logger = LoggerFactory.getLogger(SavingAccount.class);
	protected static final double INTEREST_RATE_DEFAULT_VALUE = 0.02;
	private BigDecimal interestRate;
	
	public SavingAccount(String owner) {
		super(owner, null);
		interestRate = BigDecimal.valueOf(INTEREST_RATE_DEFAULT_VALUE);
		logger.debug("SavingAccount: Created owner {} with balance {} and  interestRate {}", owner, getBalance(),
				interestRate);
	}
	
	public SavingAccount(String owner, BigDecimal balance) {
		super(owner, balance);
		interestRate = BigDecimal.valueOf(INTEREST_RATE_DEFAULT_VALUE);
		logger.debug("SavingAccount: Created owner {} with balance {} and  interestRate {}", owner, getBalance(),
				interestRate);
	}
	
	public BigDecimal getInterestRate() {
		return interestRate;
	}
	
	public void setInterestRate(BigDecimal interestRate) {
		if (interestRate == null || interestRate.compareTo(BigDecimal.ZERO) < 1) {
			logger.debug("interestRate: {} can not valid!", interestRate);
			interestRate = BigDecimal.valueOf(INTEREST_RATE_DEFAULT_VALUE);
		}
		this.interestRate = interestRate;
	}

	@Override
	public synchronized boolean withdraw(BigDecimal amount) {
		amount = BankingValidation.calculateGivenAmountGraterThanEqualZero(amount);
		if (getBalance().compareTo(amount) > -1) {
			BigDecimal withdrawAmount = getBalance().subtract(amount);
			setBalance(withdrawAmount);
			return true;
		}

		return false;
	}

	@Override
	public synchronized void payInterest() {
		BigDecimal calcualtedInterest = getBalance().multiply(getInterestRate());
		BigDecimal calculatedBalance = getBalance().add(calcualtedInterest);
		setBalance(calculatedBalance);
	}

}
