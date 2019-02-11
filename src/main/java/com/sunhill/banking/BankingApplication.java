package com.sunhill.banking;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sunhill.banking.domain.Account;
import com.sunhill.banking.domain.CheckingAccount;
import com.sunhill.banking.domain.SavingAccount;

public class BankingApplication {

	private static final String OWNER1 = "owner1";
	private static final String OWNER2 = "owner2";
	private static final String OWNER3 = "owner3";
	private static final String OWNER4 = "owner4";
	private static final Logger logger = LoggerFactory.getLogger(BankingApplication.class);
	private static final ConcurrentMap<String, Account> storage = new ConcurrentHashMap<>();

	static {
		storage.put(OWNER1, new CheckingAccount(OWNER1, BigDecimal.TEN));
		storage.put(OWNER2, new SavingAccount(OWNER2, BigDecimal.valueOf(20)));
		storage.put(OWNER3, new CheckingAccount(OWNER3, BigDecimal.valueOf(30)));
		storage.put(OWNER4, new SavingAccount(OWNER4, BigDecimal.valueOf(40)));
	}

	/**
	 * 
	 * @param args
	 *            that required arguments to initialize, start and configure the
	 *            web applicaiton
	 */
	public static void main(final String[] args) {
		logger.info("BankingApplication started!");
	}
}
