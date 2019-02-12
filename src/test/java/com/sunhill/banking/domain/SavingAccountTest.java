package com.sunhill.banking.domain;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import com.sunhill.banking.domain.util.BankingMessageUtil;
import com.sunhill.banking.service.exception.BankingException;
import com.sunhill.banking.service.exception.InvalidAmountException;
import com.sunhill.banking.service.exception.InvalidOwnerException;

public class SavingAccountTest {

	private static final String OWNER = "owner";

	@Test
	public void shouldBeTestSavingAccountWhenOwnerIsNullOnlyOneParameter() {
		try {
			new SavingAccount(null);
		} catch (Throwable expected) {
			Assert.assertEquals(InvalidOwnerException.class, expected.getClass());
			Assert.assertEquals(BankingMessageUtil.OWNER_INFO.getValue(), expected.getLocalizedMessage());
		}
	}

	@Test
	public void shouldBeTestSavingAccountForGivenNullParameters() {
		try {
			new SavingAccount(null, null);
		} catch (Throwable expected) {
			Assert.assertEquals(InvalidOwnerException.class, expected.getClass());
			Assert.assertEquals(BankingMessageUtil.OWNER_INFO.getValue(), expected.getLocalizedMessage());
		}
	}

	@Test
	public void shouldBeTestSavingAccountWhenOwnerIsEmptyOnlyOneParameter() {
		try {
			new SavingAccount(StringUtils.EMPTY);
		} catch (Throwable expected) {
			Assert.assertEquals(InvalidOwnerException.class, expected.getClass());
			Assert.assertEquals(BankingMessageUtil.OWNER_INFO_MUSS_NOT_BE_EMPTY.getValue(),
					expected.getLocalizedMessage());
		}
	}

	@Test
	public void shouldBeTestSavingAccountWhenOwnerIsValidOnlyOneParameter() {
		SavingAccount savingAccount = new SavingAccount(OWNER);
		Assert.assertEquals(OWNER, savingAccount.getOwner());
		Assert.assertEquals(BigDecimal.ZERO, savingAccount.getBalance());
	}

	@Test
	public void shouldBeTestSavingAccountWhenOwnerIsEmpty() {
		try {
			new SavingAccount(StringUtils.EMPTY, null);
		} catch (Throwable expected) {
			Assert.assertEquals(InvalidOwnerException.class, expected.getClass());
			Assert.assertEquals(BankingMessageUtil.OWNER_INFO_MUSS_NOT_BE_EMPTY.getValue(),
					expected.getLocalizedMessage());
		}
	}

	@Test
	public void shouldBeTestSavingAccountWhenBalanceIsNull() {
		try {
			new SavingAccount(OWNER, null);
		} catch (Throwable expected) {
			Assert.assertEquals(InvalidAmountException.class, expected.getClass());
			Assert.assertEquals(BankingMessageUtil.GIVEN_AMOUNT_MUST_NOT_BE_NULL.getValue(),
					expected.getLocalizedMessage());
		}
	}

	@Test
	public void shouldBeTestSavingAccountWhenSetBalanceIsNull() {
		try {
			SavingAccount savingAccount = new SavingAccount(OWNER);
			savingAccount.setBalance(null);
		} catch (Throwable expected) {
			Assert.assertEquals(BankingException.class, expected.getClass());
			Assert.assertEquals(BankingMessageUtil.GIVEN_AMOUNT_MUST_NOT_BE_NULL.getValue(),
					expected.getLocalizedMessage());
		}
	}

	@Test
	public void shouldBeTestSavingAccountWhenBalanceIsLessThanZero() {
		try {
			SavingAccount savingAccount = new SavingAccount(OWNER, new BigDecimal(-1));
		} catch (Throwable expected) {
			Assert.assertEquals(BankingException.class, expected.getClass());
			Assert.assertEquals(BankingMessageUtil.GIVEN_AMOUNT_MUST_NOT_BE_LESS_THAN_ZERO.getValue(),
					expected.getLocalizedMessage());
		}
	}

	@Test
	public void shouldBeTestSavingAccountWhenBalanceIsTen() {
		SavingAccount savingAccount = new SavingAccount(OWNER, BigDecimal.TEN);
		Assert.assertEquals(BigDecimal.TEN, savingAccount.getBalance());
	}

	@Test
	public void shouldBeTestSavingAccountWhenInterestRateNotSet() {
		SavingAccount savingAccount = new SavingAccount(OWNER);
		Assert.assertEquals(BigDecimal.valueOf(SavingAccount.INTEREST_RATE_DEFAULT_VALUE_YEARLY),
				savingAccount.getInterestRate());
	}

	@Test
	public void shouldBeTestSavingAccountWithBalanceWhenInterestRateNotSet() {
		SavingAccount savingAccount = new SavingAccount(OWNER, BigDecimal.TEN);
		Assert.assertEquals(BigDecimal.valueOf(SavingAccount.INTEREST_RATE_DEFAULT_VALUE_YEARLY),
				savingAccount.getInterestRate());
	}

	@Test
	public void shouldBeTestSavingAccountWithBalanceWhenInterestRateIsNull() {
		try {
			SavingAccount savingAccount = new SavingAccount(OWNER, BigDecimal.TEN);
			savingAccount.setInterestRate(null);
		} catch (Throwable expected) {
			Assert.assertEquals(NullPointerException.class, expected.getClass());
			Assert.assertEquals(BankingMessageUtil.GIVEN_AMOUNT_MUST_NOT_BE_NULL.getValue(),
					expected.getLocalizedMessage());
		}
	}

	@Test
	public void shouldBeTestSavingAccountWithBalanceWhenInterestRateIsLessThanEqualZero() {
		try {
			SavingAccount savingAccount = new SavingAccount(OWNER, BigDecimal.TEN);
			savingAccount.setInterestRate(BigDecimal.ZERO);
		} catch (Throwable expected) {
			Assert.assertEquals(BankingException.class, expected.getClass());
			Assert.assertEquals(BankingMessageUtil.GIVEN_INTEREST_RATE_MUST_NOT_BE_LESS_THAN_ZERO.getValue(),
					expected.getLocalizedMessage());
		}
	}

	@Test
	public void shouldBeTestSavingAccountWithBalanceWhenInterestRateIsLessThanZero() {
		try {
			BigDecimal givenInterestRate = BigDecimal.valueOf(-5);
			SavingAccount savingAccount = new SavingAccount(OWNER, BigDecimal.TEN);
			savingAccount.setInterestRate(givenInterestRate);
		} catch (Throwable expected) {
			Assert.assertEquals(BankingException.class, expected.getClass());
			Assert.assertEquals(BankingMessageUtil.GIVEN_INTEREST_RATE_MUST_NOT_BE_LESS_THAN_ZERO.getValue(),
					expected.getLocalizedMessage());
		}
	}

	// TODO
	@Test
	public void shouldBeTestSavingAccountWithBalanceWhenInterestRateIsGreaterThanEqualOne() {
		SavingAccount savingAccount = new SavingAccount(OWNER, BigDecimal.TEN);
		savingAccount.setInterestRate(BigDecimal.ONE);
	}

	@Test
	public void shouldBeTestSavingAccountWithBalanceWhenInterestRateIsDouble() {
		BigDecimal givenInterestRate = BigDecimal.valueOf(5);
		SavingAccount savingAccount = new SavingAccount(OWNER, BigDecimal.TEN);
		savingAccount.setInterestRate(givenInterestRate);
		Assert.assertEquals(givenInterestRate.divide(BigDecimal.valueOf(100)), savingAccount.getInterestRate());
	}

	@Test
	public void shouldBeTestSavingAccountPayInterestWhenGivenPeriodMonthlyIsZero() {
		try {
			BigDecimal givenBalance = BigDecimal.TEN;
			SavingAccount savingAccount = new SavingAccount(OWNER, givenBalance);
			savingAccount.payInterest(0);
		} catch (Throwable expected) {
			Assert.assertEquals(BankingException.class, expected.getClass());
			Assert.assertEquals(
					BankingMessageUtil.GIVEN_PERIOD_MONTHLY_MUST_BE_GREATER_THAN_ZERO_FOR_CALCULATE_INTEREST.getValue(),
					expected.getLocalizedMessage());
		}
	}

	@Test
	public void shouldBeTestSavingAccountPayInterestWhenGivenPeriodMonthlyIsLessThanZero() {
		try {
			BigDecimal givenBalance = BigDecimal.TEN;
			SavingAccount savingAccount = new SavingAccount(OWNER, givenBalance);
			savingAccount.payInterest(-1);
		} catch (Throwable expected) {
			Assert.assertEquals(BankingException.class, expected.getClass());
			Assert.assertEquals(
					BankingMessageUtil.GIVEN_PERIOD_MONTHLY_MUST_BE_GREATER_THAN_ZERO_FOR_CALCULATE_INTEREST.getValue(),
					expected.getLocalizedMessage());
		}
	}

	@Test
	public void shouldBeTestSavingAccountPayInterestWhenInterestRateIsDefault() {
		BigDecimal givenBalance = BigDecimal.TEN;
		SavingAccount savingAccount = new SavingAccount(OWNER, givenBalance);
		int monthlyPeriod = 1;
		savingAccount.payInterest(monthlyPeriod);
		BigDecimal givenInterestRate = savingAccount.getInterestRate().multiply(BigDecimal.valueOf(100));
		BigDecimal calculatedBalance = calculateInterestForTest(givenBalance, givenInterestRate, monthlyPeriod);
		Assert.assertEquals(calculatedBalance, savingAccount.getBalance());
	}

	@Test
	public void shouldBeTestSavingAccountPayInterestWhenInterestRateIsNull() {
		try {
			BigDecimal givenBalance = BigDecimal.TEN;
			SavingAccount savingAccount = new SavingAccount(OWNER, givenBalance);
			savingAccount.setInterestRate(null);
			savingAccount.payInterest(1);
			BigDecimal calcInterest = givenBalance
					.add(givenBalance.multiply(BigDecimal.valueOf(SavingAccount.INTEREST_RATE_DEFAULT_VALUE_YEARLY)));
		} catch (Throwable expected) {
			Assert.assertEquals(NullPointerException.class, expected.getClass());
			Assert.assertEquals(BankingMessageUtil.GIVEN_AMOUNT_MUST_NOT_BE_NULL.getValue(),
					expected.getLocalizedMessage());
		}
	}

	@Test
	public void shouldBeTestSavingAccountPayInterestWhenInterestRateIsNotDefault() {
		BigDecimal givenBalance = BigDecimal.TEN;
		BigDecimal givenInterestRate = BigDecimal.valueOf(12);
		SavingAccount savingAccount = new SavingAccount(OWNER, givenBalance);
		savingAccount.setInterestRate(givenInterestRate);
		int monthlyPeriod = 1;
		savingAccount.payInterest(monthlyPeriod);
		BigDecimal calculatedBalance = calculateInterestForTest(givenBalance, givenInterestRate, monthlyPeriod);
		Assert.assertEquals(calculatedBalance, savingAccount.getBalance());
	}

	private BigDecimal calculateInterestForTest(BigDecimal givenBalance, BigDecimal givenInterestRate, int monthlyPeriod) {
		BigDecimal monthlyInterestRate = givenInterestRate.divide(BigDecimal.valueOf(12), MathContext.DECIMAL64).divide(BigDecimal.valueOf(100));
		BigDecimal calcFactor = monthlyInterestRate.add(BigDecimal.ONE);
		BigDecimal calculatedBalance = givenBalance.multiply(calcFactor.pow(monthlyPeriod)).setScale(2, RoundingMode.HALF_DOWN);
		
		return calculatedBalance;
	}
	
	@Test
	public void shouldBeTestSavingAccountWhenAddAmountIsNull() {
		try {
			BigDecimal givenBalance = BigDecimal.TEN;
			SavingAccount savingAccount = new SavingAccount(OWNER, givenBalance);
			savingAccount.deposit(null);
		} catch (Throwable expected) {
			Assert.assertEquals(InvalidAmountException.class, expected.getClass());
			Assert.assertEquals(BankingMessageUtil.GIVEN_AMOUNT_MUST_NOT_BE_NULL.getValue(),
					expected.getLocalizedMessage());
		}
	}

	@Test
	public void shouldBeTestSavingAccountWhenAddAmountIsTen() {
		BigDecimal givenBalance = BigDecimal.TEN;
		BigDecimal wantsToAddAmount = BigDecimal.TEN;
		SavingAccount savingAccount = new SavingAccount(OWNER, givenBalance);
		savingAccount.deposit(wantsToAddAmount);
		Assert.assertEquals(givenBalance.add(wantsToAddAmount), savingAccount.getBalance());
	}

	@Test
	public void shouldBeTestSavingAccountWithdrawWhenGivenAmountIsNull() {
		try {
			SavingAccount savingAccount = new SavingAccount(OWNER, BigDecimal.TEN);
			savingAccount.withdraw(null);
		} catch (Throwable expected) {
			Assert.assertEquals(InvalidAmountException.class, expected.getClass());
			Assert.assertEquals(BankingMessageUtil.GIVEN_AMOUNT_MUST_NOT_BE_NULL.getValue(),
					expected.getLocalizedMessage());
		}
	}

	@Test
	public void shouldBeTestSavingAccountWithdrawWhenGivenAmountIsLessThanBalance() {
		try {
			BigDecimal givenBalance = BigDecimal.TEN;
			SavingAccount savingAccount = new SavingAccount(OWNER, givenBalance);
			savingAccount.withdraw(givenBalance.add(BigDecimal.ONE));
		} catch (Throwable expected) {
			Assert.assertEquals(BankingException.class, expected.getClass());
			Assert.assertEquals(BankingMessageUtil.WANTS_TO_WITHDRAW_AMOUNT_MUST_NOT_BE_LESS_THAN_BALANCE.getValue(),
					expected.getLocalizedMessage());
		}
	}

	@Test
	public void shouldBeTestSavingAccountWithdrawWhenGivenAmountIsEqualBalance() {
		BigDecimal givenBalance = BigDecimal.TEN;
		SavingAccount savingAccount = new SavingAccount(OWNER, givenBalance);
		savingAccount.withdraw(givenBalance);
		Assert.assertEquals(BigDecimal.ZERO, savingAccount.getBalance());
	}

	@Test
	public void shouldBeTestSavingAccountWithdrawWhenGivenAmountIsBiggerThanBalance() {
		try {
			SavingAccount savingAccount = new SavingAccount(OWNER, BigDecimal.ONE);
			savingAccount.withdraw(BigDecimal.TEN);
		} catch (Throwable expected) {
			Assert.assertEquals(BankingException.class, expected.getClass());
			Assert.assertEquals(BankingMessageUtil.WANTS_TO_WITHDRAW_AMOUNT_MUST_NOT_BE_LESS_THAN_BALANCE.getValue(),
					expected.getLocalizedMessage());
		}
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

		IntStream.range(0, 1000).forEach(count -> service.submit(new Runnable() {
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
		IntStream.range(0, 1000).forEach(count -> service.submit(new Runnable() {
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
