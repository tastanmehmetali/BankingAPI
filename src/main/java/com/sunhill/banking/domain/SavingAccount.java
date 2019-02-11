package com.sunhill.banking.domain;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sunhill.banking.domain.util.BankingMessageUtil;
import com.sunhill.banking.domain.util.BankingValidation;
import com.sunhill.banking.service.exception.BankingException;

public class SavingAccount extends Account implements ICalculateInterest {

	private static final Logger logger = LoggerFactory.getLogger(SavingAccount.class);
	protected static final double INTEREST_RATE_DEFAULT_VALUE_YEARLY = 0.02; // 2% yearly
	private BigDecimal interestRate;
	
	public SavingAccount(String owner) {
		super(owner, BigDecimal.ZERO);
		interestRate = BigDecimal.valueOf(INTEREST_RATE_DEFAULT_VALUE_YEARLY);
		logger.debug("SavingAccount: Created owner: {} with balance: {} and  interestRate: {}", owner, getBalance(),
				interestRate);
	}
	
	public SavingAccount(String owner, BigDecimal balance) {
		super(owner, balance);
		interestRate = BigDecimal.valueOf(INTEREST_RATE_DEFAULT_VALUE_YEARLY);
		logger.debug("SavingAccount: Created owner: {} with balance: {} and  interestRate: {}", owner, getBalance(),
				interestRate);
	}
	
	public BigDecimal getInterestRate() {
		return interestRate;
	}
	
	public void setInterestRate(BigDecimal interestRate) {
		if(interestRate == null)
			throw new NullPointerException(BankingMessageUtil.GIVEN_AMOUNT_MUST_NOT_BE_NULL.getValue());
		
		if (interestRate.compareTo(BigDecimal.ZERO) < 1) {
			logger.debug("interestRate: {} can not valid!", interestRate);
			throw new BankingException(BankingMessageUtil.GIVEN_INTEREST_RATE_MUST_NOT_BE_LESS_THAN_ZERO.getValue());
		}
		
		this.interestRate = interestRate.divide(BigDecimal.valueOf(100));
	}

	@Override
	public synchronized void withdraw(BigDecimal amount) {
		amount = BankingValidation.calculateGivenAmountGraterThanEqualZero(amount);
		if (getBalance().compareTo(amount) < 0) {
			throw new BankingException(BankingMessageUtil.WANTS_TO_WITHDRAW_AMOUNT_MUST_NOT_BE_LESS_THAN_BALANCE.getValue());
		}

		BigDecimal withdrawAmount = getBalance().subtract(amount);
		setBalance(withdrawAmount);
	}
	
	private BigDecimal calculateInterestGain(int periodMonthly) {
		if(!BankingValidation.isValidGivenAmountGraterThanZero(BigDecimal.valueOf(periodMonthly)))
			throw new BankingException(BankingMessageUtil.GIVEN_PERIOD_MONTHLY_MUST_BE_GREATER_THAN_ZERO_FOR_CALCULATE_INTEREST.getValue());

		final BigDecimal calc = new BigDecimal(1, MathContext.DECIMAL64);
		BigDecimal monthlyRate = getInterestRate().divide(BigDecimal.valueOf(12), MathContext.DECIMAL64);
		BigDecimal baseFactor = monthlyRate.add(calc);
		BigDecimal calculatedValue = getBalance().multiply(baseFactor.pow(periodMonthly)).setScale(2, RoundingMode.HALF_DOWN);
		return calculatedValue.subtract(getBalance());
	}

	@Override
	public synchronized void payInterest(int termInYears) {
		BigDecimal calculatedBalance = calculateInterestGain(termInYears);
		BigDecimal calculatedGain = getBalance().add(calculatedBalance);
		setBalance(calculatedGain);
	}

}
