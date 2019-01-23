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
		super(owner, null);
		overDraftLimit = new BigDecimal(OVER_DRAFT_LIMIT_DEFAULT_VALUE);
		logger.debug("CheckingAccount: Created owner {} with balance {} and  overdraft {}", owner, getBalance(),
				overDraftLimit);
	}

	public CheckingAccount(final String owner, final BigDecimal balance) {
		super(owner, balance);
		overDraftLimit = new BigDecimal(OVER_DRAFT_LIMIT_DEFAULT_VALUE);
		logger.debug("CheckingAccount: Created owner {} with balance {} and  overdraft {}", owner, getBalance(),
				overDraftLimit);
	}

	public BigDecimal getOverDraftLimit() {
		return overDraftLimit;
	}

	public void setOverDraftLimit(BigDecimal overDraftLimit) {
		if (overDraftLimit == null || overDraftLimit.compareTo(BigDecimal.ZERO) > -1)
			overDraftLimit = new BigDecimal(OVER_DRAFT_LIMIT_DEFAULT_VALUE);
		this.overDraftLimit = overDraftLimit;
	}

	@Override
	public synchronized boolean withdraw(BigDecimal amount) {
		logger.debug("withdraw --- amount: {}", amount);
		amount = BankingValidation.calculateGivenAmountGraterThanEqualZero(amount);
		return calculateWithdraw(amount);
	}

	private boolean calculateWithdraw(final BigDecimal amount) {
		BigDecimal remainBalance = getBalance().subtract(amount);
		if (remainBalance.compareTo(getOverDraftLimit()) > -1) {
			setBalance(remainBalance);
			return true;
		}

		return false;
	}

	@Override
	public boolean transfer(final CheckingAccount account, BigDecimal amount) {
		logger.debug("transfer --- amount: {}", amount);
		if(account == null)
			throw new BankingException(BankingMessageUtil.TRANSFERED_ACCOUNT_CAN_NOT_NULL.getValue());
		
		if(getOwner().equals(account.getOwner()))
			throw new BankingException("Given accounts are same!");
		
		amount = BankingValidation.calculateGivenAmountGraterThanEqualZero(amount);
		if(withdraw(amount)) {
			account.deposit(amount);
			return true;
		}
		return false;
	}
	
}
