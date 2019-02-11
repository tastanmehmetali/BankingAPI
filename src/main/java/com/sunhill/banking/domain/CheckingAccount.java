package com.sunhill.banking.domain;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sunhill.banking.domain.util.BankingMessageUtil;
import com.sunhill.banking.domain.util.BankingValidation;
import com.sunhill.banking.service.exception.BankingException;

public class CheckingAccount extends Account implements ITransferable {

	private static final Logger logger = LoggerFactory.getLogger(CheckingAccount.class);
	protected static final int OVER_DRAFT_LIMIT_DEFAULT_VALUE = -1000;
	private BigDecimal overDraftLimit;

	public CheckingAccount(final String owner) {
		super(owner, BigDecimal.ZERO);
		overDraftLimit = new BigDecimal(OVER_DRAFT_LIMIT_DEFAULT_VALUE);
		logger.debug("CheckingAccount: Created owner: {} with balance: {} and  overdraft: {}", owner, getBalance(),
				overDraftLimit);
	}

	public CheckingAccount(final String owner, final BigDecimal balance) {
		super(owner, balance);
		overDraftLimit = new BigDecimal(OVER_DRAFT_LIMIT_DEFAULT_VALUE);
		logger.debug("CheckingAccount: Created owner: {} with balance: {} and  overdraft: {}", owner, getBalance(),
				overDraftLimit);
	}

	public BigDecimal getOverDraftLimit() {
		return overDraftLimit;
	}

	public void setOverDraftLimit(BigDecimal overDraftLimit) {
		if(overDraftLimit == null)
			throw new NullPointerException(BankingMessageUtil.GIVEN_AMOUNT_MUST_NOT_BE_NULL.getValue());
		
		if(overDraftLimit.compareTo(BigDecimal.ZERO) > 0)
			throw new BankingException(BankingMessageUtil.GIVEN_OVERDRAFT_MUST_NOT_BE_GREATER_THAN_ZERO.getValue());

		this.overDraftLimit = overDraftLimit;
	}

	@Override
	public synchronized void withdraw(BigDecimal amount) {
		logger.debug("withdraw --- amount: {}", amount);
		amount = BankingValidation.calculateGivenAmountGraterThanEqualZero(amount);
		calculateWithdraw(amount);
	}

	private void calculateWithdraw(final BigDecimal amount) {
		BigDecimal remainBalance = getBalance().subtract(amount);
		if (remainBalance.compareTo(getOverDraftLimit()) < 0) {
			throw new BankingException(BankingMessageUtil.WANTS_TO_WITHDRAW_AMOUNT_MUST_NOT_BE_LESS_THAN_OVERDRAFT.getValue());
		}

		setBalance(remainBalance);
	}

	@Override
	public void transfer(final CheckingAccount account, BigDecimal amount) {
		logger.debug("transfer --- amount: {}", amount);
		if(account == null)
			throw new BankingException(BankingMessageUtil.TRANSFERED_ACCOUNT_MUST_NOT_NULL.getValue());
		
		if(getOwner().equals(account.getOwner()))
			throw new BankingException(BankingMessageUtil.GIVEN_ACCOUNTS_ARE_SAME.getValue());
		
		amount = BankingValidation.calculateGivenAmountGraterThanEqualZero(amount);
		withdraw(amount);
		account.deposit(amount);
	}
	
}
