package com.sunhill.banking.infra.persistance;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.sunhill.banking.domain.Account;
import com.sunhill.banking.domain.CheckingAccount;
import com.sunhill.banking.domain.SavingAccount;

@Repository
public class AccountDao implements IAccountDao {

	private static final Logger logger = LoggerFactory.getLogger(AccountDao.class);
	protected static final ConcurrentMap<String, Account> storage = new ConcurrentHashMap<>();

	static {
		storage.put("owner1", new CheckingAccount("owner1", BigDecimal.TEN));
		storage.put("owner2", new SavingAccount("owner2", BigDecimal.valueOf(20)));
		storage.put("owner3", new CheckingAccount("owner3", BigDecimal.valueOf(30)));
		storage.put("owner4", new SavingAccount("owner4", BigDecimal.valueOf(40)));
	}
	
	@Override
	public Account findAccountByOwnerName(String owner) {
		Account account = null;
		try {
			account = storage.get(owner);
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		
		return account;
	}

}
