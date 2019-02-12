package com.sunhill.banking.domain.util;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import com.sunhill.banking.domain.CheckingAccount;
import com.sunhill.banking.service.exception.BankingException;
import com.sunhill.banking.service.exception.InvalidAmountException;
import com.sunhill.banking.service.exception.InvalidOwnerException;

public class BankingValidationTest {

	private static final String OWNER1 = "owner1";
	private static final String OWNER2 = "owner2";

	@Test
	public void privateConstructorTest() throws Exception {
		Constructor<BankingValidation> validationConstructor = BankingValidation.class.getDeclaredConstructor();
		Assert.assertFalse(validationConstructor.isAccessible());
		validationConstructor.setAccessible(true);
		validationConstructor.newInstance((Object[]) null);
	}

	@Test
	public void shouldBeTestCheckAndReturnValueForGivenAttribute() {
		try {
			BankingValidation.checkNullAndReturnValueForGivenAttribute(null);
		} catch (Throwable expected) {
			Assert.assertEquals(InvalidAmountException.class, expected.getClass());
			Assert.assertEquals(BankingMessageUtil.GIVEN_AMOUNT_MUST_NOT_BE_NULL.getValue(),
					expected.getLocalizedMessage());
		}
	}

	@Test
	public void shouldBeTestGivenFirstAccountIsNullOthersNotNull() {
		try {
			BankingValidation.isGivenAccountsAreNotSame(null, new CheckingAccount(StringUtils.EMPTY));
		} catch (Throwable expected) {
			Assert.assertEquals(InvalidOwnerException.class, expected.getClass());
			Assert.assertEquals(BankingMessageUtil.OWNER_INFO_MUSS_NOT_BE_EMPTY.getValue(),
					expected.getLocalizedMessage());
		}

	}

	@Test
	public void shouldBeTestGivenSecondAccountIsNullOtherIsNull() {
		try {
			BankingValidation.isGivenAccountsAreNotSame(new CheckingAccount(StringUtils.EMPTY), null);
		} catch (Throwable expected) {
			Assert.assertEquals(InvalidOwnerException.class, expected.getClass());
			Assert.assertEquals(BankingMessageUtil.OWNER_INFO_MUSS_NOT_BE_EMPTY.getValue(),
					expected.getLocalizedMessage());
		}
	}

	@Test
	public void shouldBeTestGivenFirstAccountIsNullOthersNotEmpty() {
		try {
			BankingValidation.isGivenAccountsAreNotSame(null, new CheckingAccount(OWNER1));
		} catch (Throwable expected) {
			Assert.assertEquals(BankingException.class, expected.getClass());
			Assert.assertEquals(BankingMessageUtil.GIVEN_ACCOUNT_MUST_NOT_BE_NULL.getValue(),
					expected.getLocalizedMessage());
		}

	}

	@Test
	public void shouldBeTestGivenSecondAccountIsNullOtherIsNotEmpty() {
		try {
			BankingValidation.isGivenAccountsAreNotSame(new CheckingAccount(OWNER1), null);
		} catch (Throwable expected) {
			Assert.assertEquals(BankingException.class, expected.getClass());
			Assert.assertEquals(BankingMessageUtil.GIVEN_ACCOUNT_MUST_NOT_BE_NULL.getValue(),
					expected.getLocalizedMessage());
		}
	}

	@Test
	public void shouldBeTestGivenBothAccountsAreNotNullAndSameValue() {
		boolean isNotSame = BankingValidation.isGivenAccountsAreNotSame(new CheckingAccount(OWNER1), new CheckingAccount(OWNER1));
		Assert.assertFalse(isNotSame);
	}

	@Test
	public void shouldBeTestGivenBothAccountsAreNotNullAndDifferent() {
		boolean isNotSame = BankingValidation.isGivenAccountsAreNotSame(new CheckingAccount(OWNER1),
				new CheckingAccount(OWNER2));
		Assert.assertTrue(isNotSame);
	}

	@Test
	public void shouldBeTestGivenAmountNull() {
		try {
			BankingValidation.calculateGivenAmountGraterThanEqualZero(null);
		} catch (Throwable expected) {
			Assert.assertEquals(InvalidAmountException.class, expected.getClass());
		}
	}

	@Test
	public void shouldBeTestGivenAmountZero() {
		BigDecimal calculatedResult = BankingValidation.calculateGivenAmountGraterThanEqualZero(BigDecimal.ZERO);
		Assert.assertEquals(BigDecimal.ZERO, calculatedResult);
	}

	@Test
	public void shouldBeTestGivenAmountTen() {
		BigDecimal amount = BankingValidation.calculateGivenAmountGraterThanEqualZero(BigDecimal.TEN);
		Assert.assertEquals(BigDecimal.TEN, amount);
	}

	@Test
	public void shouldBeTestCheckAndReturnValueForGivenAttributeIsNotNull() {
		BigDecimal checkedAttribute = BankingValidation.checkNullAndReturnValueForGivenAttribute(BigDecimal.TEN);
		Assert.assertEquals(BigDecimal.TEN, checkedAttribute);
	}

	@Test
	public void shouldBeTestGivenBothAccountsAreNull() {
		try {
			boolean isNotSame = BankingValidation.isGivenAccountsAreNotSame(null, null);
			Assert.assertFalse(isNotSame);
		} catch (Throwable expected) {
			Assert.assertEquals(BankingException.class, expected.getClass());
			Assert.assertEquals(BankingMessageUtil.GIVEN_ACCOUNT_MUST_NOT_BE_NULL.getValue(),
					expected.getLocalizedMessage());
		}
	}

	@Test
	public void shouldBeTestGivenOneOfThemIsNullOtherIsNotNull() {
		try {
			boolean isNotSame = BankingValidation.isGivenAccountsAreNotSame(null, new CheckingAccount(OWNER1));
			Assert.assertFalse(isNotSame);
		} catch (Throwable expected) {
			Assert.assertEquals(BankingException.class, expected.getClass());
			Assert.assertEquals(BankingMessageUtil.GIVEN_ACCOUNT_MUST_NOT_BE_NULL.getValue(),
					expected.getLocalizedMessage());
		}
	}

	@Test
	public void shouldBeTestGivenOneOfThemIsNullOtherIsNotNullVersion2() {
		try {
			boolean isNotSame = BankingValidation.isGivenAccountsAreNotSame(new CheckingAccount(OWNER1), null);
			Assert.assertFalse(isNotSame);
		} catch (Throwable expected) {
			Assert.assertEquals(BankingException.class, expected.getClass());
			Assert.assertEquals(BankingMessageUtil.GIVEN_ACCOUNT_MUST_NOT_BE_NULL.getValue(),
					expected.getLocalizedMessage());
		}
	}

	@Test
	public void shouldBeTestGivenBothAreNotSame() {
		boolean isNotSame = BankingValidation.isGivenAccountsAreNotSame(new CheckingAccount(OWNER1),
				new CheckingAccount(OWNER2));
		Assert.assertTrue(isNotSame);
	}

}
