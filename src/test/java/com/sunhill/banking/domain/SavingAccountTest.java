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

public class SavingAccountTest {

	private static final String OWNER = "owner";

	@Test
	public void shouldBeTestSavingAccountForgivenNullParameters() {
		try {
			new SavingAccount(null, null);
		} catch (Throwable expected) {
			Assert.assertEquals(NullPointerException.class, expected.getClass());
		}
	}
	
	@Test
	public void shouldBeTestSavingAccountWhenOwnerIsNullOnlyOneParameter() {
		try {
			new SavingAccount(null);
		} catch (Throwable expected) {
			Assert.assertEquals(NullPointerException.class, expected.getClass());
		}
	}

	@Test
	public void shouldBeTestSavingAccountWhenOwnerIsEmptyOnlyOneParameter() {
		SavingAccount savingAccount = new SavingAccount(StringUtils.EMPTY);
		Assert.assertEquals(StringUtils.EMPTY, savingAccount.getOwner());
		Assert.assertEquals(BigDecimal.ZERO, savingAccount.getBalance());
	}

	@Test
	public void shouldBeTestSavingAccountWhenOwnerIsEmpty() {
		SavingAccount savingAccount = new SavingAccount(StringUtils.EMPTY, null);
		Assert.assertEquals(StringUtils.EMPTY, savingAccount.getOwner());
	}

	@Test
	public void shouldBeTestSavingAccountWhenBalanceIsNull() {
		SavingAccount savingAccount = new SavingAccount(StringUtils.EMPTY, null);
		Assert.assertEquals(BigDecimal.ZERO, savingAccount.getBalance());
	}

	@Test
	public void shouldBeTestSavingAccountWhenBalanceIsTen() {
		SavingAccount savingAccount = new SavingAccount(StringUtils.EMPTY, BigDecimal.TEN);
		Assert.assertEquals(BigDecimal.TEN, savingAccount.getBalance());
	}
	
	@Test
	public void shouldBeTestSavingAccountWhenInterestRateNotSet() {
		SavingAccount savingAccount = new SavingAccount(StringUtils.EMPTY);
		Assert.assertEquals(BigDecimal.valueOf(SavingAccount.INTEREST_RATE_DEFAULT_VALUE), savingAccount.getInterestRate());
	}
	
	@Test
	public void shouldBeTestSavingAccountWithBalanceWhenInterestRateNotSet() {
		SavingAccount savingAccount = new SavingAccount(StringUtils.EMPTY, BigDecimal.TEN);
		Assert.assertEquals(BigDecimal.valueOf(SavingAccount.INTEREST_RATE_DEFAULT_VALUE), savingAccount.getInterestRate());
	}
	
	@Test
	public void shouldBeTestSavingAccountWithBalanceWhenInterestRateIsNull() {
		SavingAccount savingAccount = new SavingAccount(StringUtils.EMPTY, BigDecimal.TEN);
		savingAccount.setInterestRate(null);
		Assert.assertEquals(BigDecimal.valueOf(SavingAccount.INTEREST_RATE_DEFAULT_VALUE), savingAccount.getInterestRate());
	}
	
	@Test
	public void shouldBeTestSavingAccountWithBalanceWhenInterestRateIsNotNull() {
		SavingAccount savingAccount = new SavingAccount(StringUtils.EMPTY, BigDecimal.TEN);
		savingAccount.setInterestRate(BigDecimal.ONE);
		Assert.assertEquals(BigDecimal.ONE, savingAccount.getInterestRate());
	}
	
	@Test
	public void shouldBeTestSavingAccountWithBalanceWhenInterestRateIsDouble() {
		BigDecimal givenInterestRate = BigDecimal.valueOf(0.05);
		SavingAccount savingAccount = new SavingAccount(StringUtils.EMPTY, BigDecimal.TEN);
		savingAccount.setInterestRate(givenInterestRate);
		Assert.assertEquals(givenInterestRate, savingAccount.getInterestRate());
	}
	
	@Test
	public void shouldBeTestSavingAccountWithBalanceWhenInterestRateIsLessThanZero() {
		BigDecimal givenInterestRate = BigDecimal.valueOf(-0.05);
		SavingAccount savingAccount = new SavingAccount(StringUtils.EMPTY, BigDecimal.TEN);
		savingAccount.setInterestRate(givenInterestRate);
		Assert.assertEquals(BigDecimal.valueOf(SavingAccount.INTEREST_RATE_DEFAULT_VALUE), savingAccount.getInterestRate());
	}
	
	@Test
	public void shouldBeTestSavingAccountPayInterestWhenInterestRateIsDefault() {
		BigDecimal givenBalance = BigDecimal.TEN;
		SavingAccount savingAccount = new SavingAccount(StringUtils.EMPTY, givenBalance);
		savingAccount.payInterest();
		BigDecimal calcInterest = givenBalance.add(givenBalance.multiply(BigDecimal.valueOf(SavingAccount.INTEREST_RATE_DEFAULT_VALUE)));
		Assert.assertEquals(calcInterest, savingAccount.getBalance());
	}
	
	@Test
	public void shouldBeTestSavingAccountPayInterestWhenInterestRateIsNull() {
		BigDecimal givenBalance = BigDecimal.TEN;
		SavingAccount savingAccount = new SavingAccount(StringUtils.EMPTY, givenBalance);
		savingAccount.setInterestRate(null);
		savingAccount.payInterest();
		BigDecimal calcInterest = givenBalance.add(givenBalance.multiply(BigDecimal.valueOf(SavingAccount.INTEREST_RATE_DEFAULT_VALUE)));
		Assert.assertEquals(calcInterest, savingAccount.getBalance());
	}
	
	@Test
	public void shouldBeTestSavingAccountPayInterestWhenInterestRateIsNotNull() {
		BigDecimal givenBalance = BigDecimal.TEN;
		BigDecimal givenInterestRate = BigDecimal.valueOf(0.01);
		SavingAccount savingAccount = new SavingAccount(StringUtils.EMPTY, givenBalance);
		savingAccount.setInterestRate(givenInterestRate);
		savingAccount.payInterest();
		BigDecimal calcInterest = givenBalance.add(givenBalance.multiply(givenInterestRate));
		Assert.assertEquals(calcInterest, savingAccount.getBalance());
	}
	
	@Test
	public void shouldBeTestSavingAccountWhenAddAmountIsNull() {
		BigDecimal givenBalance = BigDecimal.TEN;
		SavingAccount savingAccount = new SavingAccount(StringUtils.EMPTY, givenBalance);
		savingAccount.deposit(null);
		Assert.assertEquals(givenBalance, savingAccount.getBalance());
	}
	
	@Test
	public void shouldBeTestSavingAccountWhenAddAmountIsTen() {
		BigDecimal givenBalance = BigDecimal.TEN;
		BigDecimal wantsToAddAmount = BigDecimal.TEN;
		SavingAccount savingAccount = new SavingAccount(StringUtils.EMPTY, givenBalance);
		savingAccount.deposit(wantsToAddAmount);
		Assert.assertEquals(givenBalance.add(wantsToAddAmount), savingAccount.getBalance());
	}
	
	@Test
	public void shouldBeTestSavingAccountWithdrawWhenGivenAmountIsNull() {
		SavingAccount savingAccount = new SavingAccount(StringUtils.EMPTY, BigDecimal.TEN);
		boolean canWithdraw = savingAccount.withdraw(null);
		Assert.assertTrue(canWithdraw);
	}
	
	@Test
	public void shouldBeTestSavingAccountWithdrawWhenGivenAmountIsTen() {
		SavingAccount savingAccount = new SavingAccount(StringUtils.EMPTY, BigDecimal.TEN);
		boolean canWithdraw = savingAccount.withdraw(BigDecimal.TEN);
		Assert.assertTrue(canWithdraw);
	}
	
	@Test
	public void shouldBeTestSavingAccountWithdrawWhenGivenAmountIsBiggerThanBalance() {
		SavingAccount savingAccount = new SavingAccount(StringUtils.EMPTY, BigDecimal.ONE);
		boolean canWithdraw = savingAccount.withdraw(BigDecimal.TEN);
		Assert.assertFalse(canWithdraw);
	}
	
	@Test
	public void shouldBeTestSavingAccountToString() {
		String preparedString = String.format(BankingMessageUtil.ACCOUNT_INFO.getValue(), OWNER, BigDecimal.TEN);
		SavingAccount savingAccount = new SavingAccount(OWNER, BigDecimal.TEN);
		Assert.assertEquals(preparedString, savingAccount.toString());
	}
	
	@Test
	public void shouldBeTestSavingAccountForDeposit() {
		ExecutorService service = Executors.newFixedThreadPool(3);
		SavingAccount savingAccount = new SavingAccount(OWNER);
		BigDecimal pastBalance = savingAccount.getBalance();

	    IntStream.range(0, 1000)
	      .forEach(count -> service.submit(new Runnable() {
			public void run() {
				savingAccount.deposit(BigDecimal.valueOf(1));
			}
		}));
	    try {
			service.awaitTermination(3000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	    
	    Assert.assertEquals(pastBalance.add(BigDecimal.valueOf(1000)), savingAccount.getBalance());
	}
	
	@Test
	public void shouldBeTestSavingAccountforWithdraw() {
		ExecutorService service = Executors.newFixedThreadPool(3);
		BigDecimal givenBalance = BigDecimal.valueOf(1000);
		SavingAccount savingAccount = new SavingAccount(OWNER, givenBalance);
		IntStream.range(0, 1000)
	      .forEach(count -> service.submit(new Runnable() {
			public void run() {
				savingAccount.withdraw(BigDecimal.valueOf(1));
			}
		}));
	    try {
			service.awaitTermination(3000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Assert.assertEquals(givenBalance.subtract(BigDecimal.valueOf(1000)), savingAccount.getBalance());
	}
	
	
}
