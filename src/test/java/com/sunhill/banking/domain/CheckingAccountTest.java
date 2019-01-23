package com.sunhill.banking.domain;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import com.sunhill.banking.domain.util.BankingMessageUtil;
import com.sunhill.banking.service.exception.BankingException;

public class CheckingAccountTest {
	
	private static final String OWNER2 = "owner2";
	private static final String OWNER1 = "owner1";

	@Test
	public void shouldBeTestCheckingAccountForgivenNullParameters() {
		try {
			new CheckingAccount(null, null);
		} catch (Throwable expected) {
			Assert.assertEquals(NullPointerException.class, expected.getClass());
		}
	}
	
	@Test
	public void shouldBeTestCheckingAccountForgivenNullOnlyOneParameter() {
		try {
			new CheckingAccount(null);
		} catch (Throwable expected) {
			Assert.assertEquals(NullPointerException.class, expected.getClass());
		}
	}

	@Test
	public void shouldBeTestCheckingAccountWhenOwnerIsEmptyOnlyOneParameter() {
		CheckingAccount checkingAccount = new CheckingAccount(StringUtils.EMPTY);
		Assert.assertEquals(StringUtils.EMPTY, checkingAccount.getOwner());
		Assert.assertEquals(BigDecimal.ZERO, checkingAccount.getBalance());
	}

	@Test
	public void shouldBeTestCheckingAccountWhenOwnerIsEmpty() {
		CheckingAccount checkingAccount = new CheckingAccount(StringUtils.EMPTY, null);
		Assert.assertEquals(StringUtils.EMPTY, checkingAccount.getOwner());
	}

	@Test
	public void shouldBeTestCheckingAccountWhenBalanceIsNull() {
		CheckingAccount checkingAccount = new CheckingAccount(StringUtils.EMPTY, null);
		Assert.assertEquals(BigDecimal.ZERO, checkingAccount.getBalance());
	}

	@Test
	public void shouldBeTestCheckingAccountWhenBalanceIsTen() {
		CheckingAccount checkingAccount = new CheckingAccount(StringUtils.EMPTY, BigDecimal.TEN);
		Assert.assertEquals(BigDecimal.TEN, checkingAccount.getBalance());
	}

	@Test
	public void shouldBeTestCheckingAccountWhenOverDraftLimitIsNotPass() {
		CheckingAccount checkingAccount = new CheckingAccount(StringUtils.EMPTY, BigDecimal.TEN);
		Assert.assertEquals(new BigDecimal(CheckingAccount.OVER_DRAFT_LIMIT_DEFAULT_VALUE), checkingAccount.getOverDraftLimit());
	}

	@Test
	public void shouldBeTestCheckingAccountWhenOverDraftLimitIsPassedNull() {
		CheckingAccount checkingAccount = new CheckingAccount(StringUtils.EMPTY, BigDecimal.TEN);
		checkingAccount.setOverDraftLimit(null);
		Assert.assertEquals(new BigDecimal(CheckingAccount.OVER_DRAFT_LIMIT_DEFAULT_VALUE), checkingAccount.getOverDraftLimit());
	}

	@Test
	public void shouldBeTestCheckingAccountWhenOverDraftLimitIsPassedZero() {
		CheckingAccount checkingAccount = new CheckingAccount(StringUtils.EMPTY, BigDecimal.TEN);
		checkingAccount.setOverDraftLimit(BigDecimal.ZERO);
		Assert.assertEquals(new BigDecimal(CheckingAccount.OVER_DRAFT_LIMIT_DEFAULT_VALUE), checkingAccount.getOverDraftLimit());
	}

	@Test
	public void shouldBeTestCheckingAccountWhenOverDraftLimitIsPassed() {
		BigDecimal overDraft = new BigDecimal(-100);
		CheckingAccount checkingAccount = new CheckingAccount(StringUtils.EMPTY, BigDecimal.TEN);
		checkingAccount.setOverDraftLimit(overDraft);
		Assert.assertEquals(overDraft, checkingAccount.getOverDraftLimit());
	}
	
	@Test
	public void shouldBeTestCheckingAccountWhenAddAmountIsNull() {
		BigDecimal givenBalance = BigDecimal.TEN;
		CheckingAccount checkingAccount = new CheckingAccount(StringUtils.EMPTY, givenBalance);
		checkingAccount.deposit(null);
		Assert.assertEquals(givenBalance, checkingAccount.getBalance());
	}
	
	@Test
	public void shouldBeTestCheckingAccountWhenAddAmountIsTen() {
		BigDecimal givenBalance = BigDecimal.TEN;
		BigDecimal wantsToAddAmount = BigDecimal.TEN;
		CheckingAccount checkingAccount = new CheckingAccount(StringUtils.EMPTY, givenBalance);
		checkingAccount.deposit(wantsToAddAmount);
		Assert.assertEquals(givenBalance.add(wantsToAddAmount), checkingAccount.getBalance());
	}
	
	@Test
	public void shouldBeTestCheckingAccountWithdrawWhenGivenAmountIsNull() {
		CheckingAccount checkingAccount = new CheckingAccount(StringUtils.EMPTY, BigDecimal.TEN);
		boolean canWithdraw = checkingAccount.withdraw(null);
		Assert.assertTrue(canWithdraw);
	}
	
	@Test
	public void shouldBeTestCheckingAccountWithdrawWhenGivenAmountIsTen() {
		CheckingAccount checkingAccount = new CheckingAccount(StringUtils.EMPTY, BigDecimal.TEN);
		boolean canWithdraw = checkingAccount.withdraw(BigDecimal.TEN);
		Assert.assertTrue(canWithdraw);
	}
	
	@Test
	public void shouldBeTestCheckingAccountWithdrawWhenGivenAmountIsLessThanOverDraftLimit() {
		CheckingAccount checkingAccount = new CheckingAccount(StringUtils.EMPTY, BigDecimal.TEN);
		BigDecimal givenAmountLessThanOverDraft = checkingAccount.getBalance().subtract(checkingAccount.getOverDraftLimit());
		boolean canWithdraw = checkingAccount.withdraw(givenAmountLessThanOverDraft.multiply(BigDecimal.TEN));
		Assert.assertFalse(canWithdraw);
	}
	
	@Test
	public void shouldBeTestCheckingAccountToString() {
		String ownerName = "owner";
		String preparedString = String.format(BankingMessageUtil.ACCOUNT_INFO.getValue(), ownerName, BigDecimal.TEN);
		CheckingAccount checkingAccount = new CheckingAccount(ownerName, BigDecimal.TEN);
		Assert.assertEquals(preparedString, checkingAccount.toString());
	}
	
	@Test(expected = BankingException.class)
	public void shouldBeTestCheckingAccountTransferedAccountIsNull() {
		CheckingAccount checkingAccount = new CheckingAccount(OWNER1, BigDecimal.TEN);
		checkingAccount.transfer(null, null);
	}

	@Test(expected = BankingException.class)
	public void shouldBeTestCheckingAccountTransferIsSameOwnerName() {
		CheckingAccount checkingAccount = new CheckingAccount(OWNER1, BigDecimal.TEN);
		CheckingAccount duplicatedAccount = new CheckingAccount(OWNER1, BigDecimal.TEN);
		checkingAccount.transfer(duplicatedAccount, null);
	}

	@Test(expected = BankingException.class)
	public void shouldBeTestCheckingAccountTransferIsSameOwnerNameWithDiffBalance() {
		CheckingAccount checkingAccount = new CheckingAccount(OWNER1, BigDecimal.TEN);
		CheckingAccount duplicatedAccount = new CheckingAccount(OWNER1, BigDecimal.ONE);
		checkingAccount.transfer(duplicatedAccount, null);
	}

	@Test
	public void shouldBeTestCheckingAccountTransferIsNotSameWithTransferedAmountIsNull() {
		CheckingAccount checkingAccount = new CheckingAccount(OWNER1, BigDecimal.TEN);
		CheckingAccount duplicatedAccount = new CheckingAccount(OWNER2, BigDecimal.ONE);
		boolean isTransfered = checkingAccount.transfer(duplicatedAccount, null);
		Assert.assertTrue(isTransfered);
		Assert.assertEquals(BigDecimal.TEN, checkingAccount.getBalance());
		Assert.assertEquals(BigDecimal.ONE, duplicatedAccount.getBalance());
	}

	@Test
	public void shouldBeTestCheckingAccountTransferIsNotSameWithTransferedAmountIsGreaterFromAccountBalanceAndOverDraft() {
		CheckingAccount checkingAccount = new CheckingAccount(OWNER1, BigDecimal.ONE);
		CheckingAccount duplicatedAccount = new CheckingAccount(OWNER2, BigDecimal.ONE);
		BigDecimal wantsToTransferAmount = BigDecimal.valueOf(2000);
		boolean isTransfered = checkingAccount.transfer(duplicatedAccount, wantsToTransferAmount);
		Assert.assertFalse(isTransfered);
	}

	@Test
	public void shouldBeTestCheckingAccountTransferIsNotSameWithTransferedAmountIsGreaterFromAccountBalance() {
		CheckingAccount checkingAccount = new CheckingAccount(OWNER1, BigDecimal.ONE);
		CheckingAccount duplicatedAccount = new CheckingAccount(OWNER2, BigDecimal.ONE);
		boolean isTransfered = checkingAccount.transfer(duplicatedAccount, BigDecimal.TEN);
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
			e.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
		}
		
		Assert.assertEquals(fromAccountBalance.subtract(BigDecimal.valueOf(1000)), fromAccount.getBalance());
		Assert.assertEquals(toAccountBalance.add(BigDecimal.valueOf(1000)), toAccount.getBalance());
	}
	
}
