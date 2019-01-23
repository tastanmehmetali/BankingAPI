package com.sunhill.banking.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.sunhill.banking.domain.Account;
import com.sunhill.banking.domain.CheckingAccount;
import com.sunhill.banking.domain.util.BankingMessageUtil;
import com.sunhill.banking.infra.persistance.IAccountDao;
import com.sunhill.banking.service.exception.BankingException;

@Service
public class AccountService implements IAccountService {

	@Autowired
	public IAccountDao accountDao;
	
	@Override
	public String deposit(String owner, BigDecimal amount) {
		Account account = findAccountByOwnerNameWithConditions(owner);
		account.deposit(amount);
		return account.toString();
	}

	@Override
	public boolean withdraw(String owner, BigDecimal amount) {
		Account account = findAccountByOwnerNameWithConditions(owner);
		return account.withdraw(amount);
	}

	@Override
	public String info(String owner) {
		try {
			Account account = findAccountByOwnerNameWithConditions(owner);
			return account.toString();
		} catch (Exception e) {
			return e.getMessage();
		}
		
	}

	private Account findAccountByOwnerNameWithConditions(String owner) {
		Account account = findAccountByOwnerName(owner);
		if (account == null)
			throw new BankingException(BankingMessageUtil.ACCOUNT_IS_NOT_EXIST.getValue());

		return account;
	}

	private Account findAccountByOwnerName(String owner) {
		Preconditions.checkNotNull(owner, BankingMessageUtil.OWNER_INFO.getValue());
		return accountDao.findAccountByOwnerName(owner);
	}

	@Override
	public boolean transfer(String fromOwner, String toOwner, BigDecimal amount) {
		CheckingAccount fromAccount = findAccountByOwnerNameForCasting(fromOwner);
		CheckingAccount toAccount = findAccountByOwnerNameForCasting(toOwner);
		return fromAccount.transfer(toAccount, amount);
	}
	
	private CheckingAccount findAccountByOwnerNameForCasting(String owner) {
		Account account = findAccountByOwnerNameWithConditions(owner);
		if(!CheckingAccount.class.isInstance(account))
			throw new BankingException(BankingMessageUtil.GIVEN_ACCOUNT_IS_NOT_VALID.getValue());
		return CheckingAccount.class.cast(account);
	}

}
