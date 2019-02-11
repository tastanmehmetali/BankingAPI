package com.sunhill.banking.domain;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sunhill.banking.domain.util.BankingMessageUtil;
import com.sunhill.banking.service.exception.BankingException;

public class CheckingAccountTest {

	private static final Logger logger = LoggerFactory.getLogger(CheckingAccountTest.class);
	private static final String OWNER1 = "owner1";
	private static final String OWNER2 = "owner2";
	
	@Test
	public void shouldBeTestCheckingAccountForgivenNullParameters() {
		try {
			new CheckingAccount(null, null);
		} catch (Throwable expected) {
			Assert.assertEquals(NullPointerException.class, expected.getClass());
			Assert.assertEquals(BankingMessageUtil.OWNER_INFO.getValue(), expected.getLocalizedMessage());
		}
	}

	@Test
	public void shouldBeTestCheckingAccountForgivenNullOnlyOneParameter() {
		try {
			new CheckingAccount(null);
		} catch (Throwable expected) {
			Assert.assertEquals(NullPointerException.class, expected.getClass());
			Assert.assertEquals(BankingMessageUtil.OWNER_INFO.getValue(), expected.getLocalizedMessage());
		}
	}

	@Test
	public void shouldBeTestCheckingAccountWhenOwnerIsEmptyOnlyOneParameter() {
		try {
			new CheckingAccount(StringUtils.EMPTY);
		} catch (Throwable expected) {
			Assert.assertEquals(BankingException.class, expected.getClass());
			Assert.assertEquals(BankingMessageUtil.OWNER_INFO_MUSS_NOT_BE_EMPTY.getValue(),
					expected.getLocalizedMessage());
		}
	}

	@Test
	public void shouldBeTestCheckingAccountWhenOwnerIsEmpty() {
		try {
			new CheckingAccount(StringUtils.EMPTY, null);
		} catch (Throwable expected) {
			Assert.assertEquals(BankingException.class, expected.getClass());
			Assert.assertEquals(BankingMessageUtil.OWNER_INFO_MUSS_NOT_BE_EMPTY.getValue(),
					expected.getLocalizedMessage());
		}
	}

	@Test
	public void shouldBeTestSavingAccountWhenSetBalanceIsNull() {
		try {
			CheckingAccount checkingAccount = new CheckingAccount(OWNER1);
			checkingAccount.setBalance(null);
		} catch (Throwable expected) {
			Assert.assertEquals(BankingException.class, expected.getClass());
			Assert.assertEquals(BankingMessageUtil.GIVEN_AMOUNT_MUST_NOT_BE_NULL.getValue(),
					expected.getLocalizedMessage());
		}
	}

	@Test
	public void shouldBeTestCheckingAccountWhenBalanceIsNotGiven() {
		CheckingAccount checkingAccount = new CheckingAccount(OWNER1);
		Assert.assertEquals(BigDecimal.ZERO, checkingAccount.getBalance());
	}

	@Test
	public void shouldBeTestCheckingAccountWhenBalanceIsZero() {
		BigDecimal givenBalance = BigDecimal.ZERO;
		CheckingAccount checkingAccount = new CheckingAccount(OWNER1, givenBalance);
		Assert.assertEquals(givenBalance, checkingAccount.getBalance());
	}

	@Test
	public void shouldBeTestCheckingAccountWhenBalanceIsTen() {
		CheckingAccount checkingAccount = new CheckingAccount(OWNER1, BigDecimal.TEN);
		Assert.assertEquals(BigDecimal.TEN, checkingAccount.getBalance());
	}

	@Test
	public void shouldBeTestCheckingAccountWhenOverDraftLimitIsNotPass() {
		CheckingAccount checkingAccount = new CheckingAccount(OWNER1, BigDecimal.TEN);
		Assert.assertEquals(new BigDecimal(CheckingAccount.OVER_DRAFT_LIMIT_DEFAULT_VALUE),
				checkingAccount.getOverDraftLimit());
	}

	@Test
	public void shouldBeTestCheckingAccountWhenOverDraftLimitIsPassedNull() {
		try {
			CheckingAccount checkingAccount = new CheckingAccount(OWNER1, BigDecimal.TEN);
			checkingAccount.setOverDraftLimit(null);
		} catch (Throwable expected) {
			Assert.assertEquals(NullPointerException.class, expected.getClass());
			Assert.assertEquals(BankingMessageUtil.GIVEN_AMOUNT_MUST_NOT_BE_NULL.getValue(),
					expected.getLocalizedMessage());
		}
	}

	@Test
	public void shouldBeTestCheckingAccountWhenOverDraftLimitIsPassedZero() {
		CheckingAccount checkingAccount = new CheckingAccount(OWNER1, BigDecimal.TEN);
		checkingAccount.setOverDraftLimit(BigDecimal.ZERO);
		Assert.assertEquals(BigDecimal.ZERO, checkingAccount.getOverDraftLimit());
	}

	@Test
	public void shouldBeTestCheckingAccountWhenOverDraftLimitIsPassedGreaterThanZero() {
		try {
			CheckingAccount checkingAccount = new CheckingAccount(OWNER1, BigDecimal.TEN);
			checkingAccount.setOverDraftLimit(BigDecimal.ONE);
		} catch (Throwable expected) {
			Assert.assertEquals(BankingException.class, expected.getClass());
			Assert.assertEquals(BankingMessageUtil.GIVEN_OVERDRAFT_MUST_NOT_BE_GREATER_THAN_ZERO.getValue(),
					expected.getLocalizedMessage());
		}
	}

	@Test
	public void shouldBeTestCheckingAccountWhenOverDraftLimitIsPassed() {
		BigDecimal overDraft = new BigDecimal(-100);
		CheckingAccount checkingAccount = new CheckingAccount(OWNER1, BigDecimal.TEN);
		checkingAccount.setOverDraftLimit(overDraft);
		Assert.assertEquals(overDraft, checkingAccount.getOverDraftLimit());
	}

	@Test
	public void shouldBeTestCheckingAccountWhenAddAmountIsNull() {
		BigDecimal givenBalance = BigDecimal.TEN;
		try {
			CheckingAccount checkingAccount = new CheckingAccount(OWNER1, givenBalance);
			checkingAccount.deposit(null);
		} catch (Throwable expected) {
			Assert.assertEquals(NullPointerException.class, expected.getClass());
			Assert.assertEquals(BankingMessageUtil.GIVEN_AMOUNT_MUST_NOT_BE_NULL.getValue(),
					expected.getLocalizedMessage());
		}
	}

	@Test
	public void shouldBeTestCheckingAccountWhenAddAmountIsGreaterThanTen() {
		BigDecimal givenBalance = BigDecimal.TEN;
		try {
			CheckingAccount checkingAccount = new CheckingAccount(OWNER1, givenBalance);
			checkingAccount.deposit(new BigDecimal(-11));
		} catch (Throwable expected) {
			Assert.assertEquals(BankingException.class, expected.getClass());
			Assert.assertEquals(BankingMessageUtil.GIVEN_AMOUNT_MUST_NOT_BE_LESS_THAN_ZERO.getValue(),
					expected.getLocalizedMessage());
		}
	}

	@Test
	public void shouldBeTestCheckingAccountWhenAddAmountIsTen() {
		BigDecimal givenBalance = BigDecimal.TEN;
		BigDecimal wantsToAddAmount = BigDecimal.TEN;
		CheckingAccount checkingAccount = new CheckingAccount(OWNER1, givenBalance);
		checkingAccount.deposit(wantsToAddAmount);
		Assert.assertEquals(givenBalance.add(wantsToAddAmount), checkingAccount.getBalance());
	}

	@Test
	public void shouldBeTestCheckingAccountWithdrawWhenGivenAmountIsNull() {
		try {
			CheckingAccount checkingAccount = new CheckingAccount(OWNER1, BigDecimal.TEN);
			checkingAccount.withdraw(null);
		} catch (Throwable expected) {
			Assert.assertEquals(NullPointerException.class, expected.getClass());
			Assert.assertEquals(BankingMessageUtil.GIVEN_AMOUNT_MUST_NOT_BE_NULL.getValue(),
					expected.getLocalizedMessage());
		}
	}

	@Test
	public void shouldBeTestCheckingAccountWithdrawWhenGivenAmountIsTen() {
		BigDecimal givenBalance = BigDecimal.TEN;
		CheckingAccount checkingAccount = new CheckingAccount(OWNER1, givenBalance);
		checkingAccount.withdraw(givenBalance);
		Assert.assertEquals(BigDecimal.ZERO, checkingAccount.getBalance());
	}

	@Test
	public void shouldBeTestCheckingAccountWithdrawWhenGivenAmountIsLessThanOverDraftLimit() {
		try {
			CheckingAccount checkingAccount = new CheckingAccount(OWNER1, BigDecimal.TEN);
			BigDecimal givenAmountLessThanOverDraft = checkingAccount.getBalance()
					.subtract(checkingAccount.getOverDraftLimit());
			checkingAccount.withdraw(givenAmountLessThanOverDraft.multiply(BigDecimal.TEN));
		} catch (Throwable expected) {
			Assert.assertEquals(BankingException.class, expected.getClass());
			Assert.assertEquals(BankingMessageUtil.WANTS_TO_WITHDRAW_AMOUNT_MUST_NOT_BE_LESS_THAN_OVERDRAFT.getValue(),
					expected.getLocalizedMessage());
		}
	}

	@Test
	public void shouldBeTestCheckingAccountWithdrawWhenGivenAmountIsGreaterThanOverDraftLimit() {
		CheckingAccount checkingAccount = new CheckingAccount(OWNER1, BigDecimal.TEN);
		BigDecimal givenAmountLessThanOverDraft = BigDecimal.valueOf(999);
		checkingAccount.withdraw(givenAmountLessThanOverDraft);
	}

	@Test
	public void shouldBeTestCheckingAccountWithdrawWhenGivenAmountIsEqualOverDraftLimit() {
		CheckingAccount checkingAccount = new CheckingAccount(OWNER1, BigDecimal.TEN);
		BigDecimal givenAmountLessThanOverDraft = BigDecimal.ZERO.subtract(checkingAccount.getOverDraftLimit());
		checkingAccount.withdraw(givenAmountLessThanOverDraft);
	}

	@Test
	public void shouldBeTestCheckingAccountToString() {
		String preparedString = String.format(BankingMessageUtil.ACCOUNT_INFO.getValue(), OWNER1, BigDecimal.TEN);
		CheckingAccount checkingAccount = new CheckingAccount(OWNER1, BigDecimal.TEN);
		Assert.assertEquals(preparedString, checkingAccount.toString());
	}

	@Test
	public void shouldBeTestCheckingAccountTransferedAccountIsNull() {
		try {
			CheckingAccount checkingAccount = new CheckingAccount(OWNER1, BigDecimal.TEN);
			checkingAccount.transfer(null, null);
		} catch (Throwable expected) {
			Assert.assertEquals(BankingException.class, expected.getClass());
			Assert.assertEquals(BankingMessageUtil.TRANSFERED_ACCOUNT_MUST_NOT_NULL.getValue(),
					expected.getLocalizedMessage());
		}
	}

	@Test
	public void shouldBeTestCheckingAccountTransferIsSameOwnerName() {
		try {
			CheckingAccount checkingAccount = new CheckingAccount(OWNER1, BigDecimal.TEN);
			CheckingAccount duplicatedAccount = new CheckingAccount(OWNER1, BigDecimal.TEN);
			checkingAccount.transfer(duplicatedAccount, null);
		} catch (Throwable expected) {
			Assert.assertEquals(BankingException.class, expected.getClass());
			Assert.assertEquals(BankingMessageUtil.GIVEN_ACCOUNTS_ARE_SAME.getValue(), expected.getLocalizedMessage());
		}
	}

	@Test
	public void shouldBeTestCheckingAccountTransferIsSameOwnerNameWithDiffBalance() {
		try {
			CheckingAccount checkingAccount = new CheckingAccount(OWNER1, BigDecimal.TEN);
			CheckingAccount duplicatedAccount = new CheckingAccount(OWNER1, BigDecimal.ONE);
			checkingAccount.transfer(duplicatedAccount, null);
		} catch (Throwable expected) {
			Assert.assertEquals(BankingException.class, expected.getClass());
			Assert.assertEquals(BankingMessageUtil.GIVEN_ACCOUNTS_ARE_SAME.getValue(), expected.getLocalizedMessage());
		}
	}

	@Test
	public void shouldBeTestCheckingAccountTransferIsNotSameWithTransferedAmountIsNull() {
		try {
			CheckingAccount checkingAccount = new CheckingAccount(OWNER1, BigDecimal.TEN);
			CheckingAccount duplicatedAccount = new CheckingAccount(OWNER2, BigDecimal.ONE);
			checkingAccount.transfer(duplicatedAccount, null);
		} catch (Throwable expected) {
			Assert.assertEquals(NullPointerException.class, expected.getClass());
			Assert.assertEquals(BankingMessageUtil.GIVEN_AMOUNT_MUST_NOT_BE_NULL.getValue(),
					expected.getLocalizedMessage());
		}
	}

	@Test
	public void shouldBeTestCheckingAccountTransferIsNotSameWithTransferedAmountIsGreaterFromAccountBalanceAndOverDraft() {
		try {
			CheckingAccount checkingAccount = new CheckingAccount(OWNER1, BigDecimal.ONE);
			CheckingAccount duplicatedAccount = new CheckingAccount(OWNER2, BigDecimal.ONE);
			BigDecimal wantsToTransferAmount = BigDecimal.valueOf(2000);
			checkingAccount.transfer(duplicatedAccount, wantsToTransferAmount);
		} catch (Throwable expected) {
			Assert.assertEquals(BankingException.class, expected.getClass());
			Assert.assertEquals(BankingMessageUtil.WANTS_TO_WITHDRAW_AMOUNT_MUST_NOT_BE_LESS_THAN_OVERDRAFT.getValue(), expected.getLocalizedMessage());
		}
	}

	@Test
	public void shouldBeTestCheckingAccountTransferIsNotSameWithTransferedAmountIsGreaterFromAccountBalance() {
		CheckingAccount checkingAccount = new CheckingAccount(OWNER1, BigDecimal.ONE);
		CheckingAccount duplicatedAccount = new CheckingAccount(OWNER2, BigDecimal.ONE);
		boolean isTransfered = true;
		checkingAccount.transfer(duplicatedAccount, BigDecimal.TEN);
		Assert.assertTrue(isTransfered);
		Assert.assertEquals(BigDecimal.ONE.subtract(BigDecimal.TEN), checkingAccount.getBalance());
		Assert.assertEquals(BigDecimal.ONE.add(BigDecimal.TEN), duplicatedAccount.getBalance());
	}
	
	@Test
	public void shouldBeTestCheckingAccountForDeposit() {
		ExecutorService service = Executors.newFixedThreadPool(3);
		CheckingAccount checkingAccount = new CheckingAccount(OWNER1);
		BigDecimal pastBalance = checkingAccount.getBalance();
		
	    IntStream.range(0, 1000)
	      .forEach(count -> service.submit(new Runnable() {
			public void run() {
				checkingAccount.deposit(BigDecimal.valueOf(1));
			}
		}));
	    try {
			service.awaitTermination(3000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			logger.info("Exception: {}", e.getMessage());
		}
	    
	    Assert.assertEquals(pastBalance.add(BigDecimal.valueOf(1000)), checkingAccount.getBalance());
	 
	}
	
	@Test
	public void shouldBeTestCheckingAccountforWithdraw() {
		ExecutorService service = Executors.newFixedThreadPool(3);
		BigDecimal givenBalance = BigDecimal.valueOf(1000);
		CheckingAccount checkingAccount = new CheckingAccount(OWNER1, givenBalance);
		
		IntStream.range(0, 1000)
	      .forEach(count -> service.submit(new Runnable() {
			public void run() {
				checkingAccount.withdraw(BigDecimal.valueOf(1));
			}
		}));
	    try {
			service.awaitTermination(3000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			logger.info("Exception: {}", e.getMessage());
		}
		
		Assert.assertEquals(givenBalance.subtract(BigDecimal.valueOf(1000)), checkingAccount.getBalance());
	}
	
	@Test
	public void shouldBeTestCheckingAccountfoTransfer() {
		ExecutorService service = Executors.newFixedThreadPool(3);
		BigDecimal fromAccountBalance = BigDecimal.valueOf(1000);
		CheckingAccount fromAccount = new CheckingAccount(OWNER1, fromAccountBalance);
		CheckingAccount toAccount = new CheckingAccount(OWNER2);
		BigDecimal toAccountBalance = toAccount.getBalance();
		
		IntStream.range(0, 1000)
	      .forEach(count -> service.submit(new Runnable() {
			public void run() {
				fromAccount.transfer(toAccount, BigDecimal.valueOf(1));
			}
		}));
	    try {
			service.awaitTermination(3000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			logger.info("Exception: {}", e.getMessage());
		}
		
		Assert.assertEquals(fromAccountBalance.subtract(BigDecimal.valueOf(1000)), fromAccount.getBalance());
		Assert.assertEquals(toAccountBalance.add(BigDecimal.valueOf(1000)), toAccount.getBalance());
	}

}