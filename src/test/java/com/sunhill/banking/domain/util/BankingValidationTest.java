package com.sunhill.banking.domain.util;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import com.sunhill.banking.domain.CheckingAccount;
import com.sunhill.banking.domain.util.BankingValidation;

public class BankingValidationTest {

	@Test
	public void privateConstructorTest() throws Exception {
	    Constructor<BankingValidation> validationConstructor = BankingValidation.class.getDeclaredConstructor();
	    Assert.assertFalse(validationConstructor.isAccessible());
	    validationConstructor.setAccessible(true);
	    validationConstructor.newInstance((Object[]) null);
	}
	
	@Test
	public void shouldBeTestCheckAndReturnValueForGivenAttribute() {
		BigDecimal checkedAttribute = BankingValidation.checkAndReturnValueForGivenAttribute(null);
		Assert.assertEquals(BigDecimal.ZERO, checkedAttribute);
	}
	
	@Test
	public void shouldBeTestCheckAndReturnValueForGivenAttributeIsNotNull() {
		BigDecimal checkedAttribute = BankingValidation.checkAndReturnValueForGivenAttribute(BigDecimal.TEN);
		Assert.assertEquals(BigDecimal.TEN, checkedAttribute);
	}
	
	@Test
	public void shouldBeTestGivenBothAccountsAreNull() {
		boolean isSame = BankingValidation.isGivenAccountsAreNotSame(null, null);
		Assert.assertFalse(isSame);
	}
	
	@Test
	public void shouldBeTestGivenFirstAccountIsNullOthersNotNull() {
		boolean isSame = BankingValidation.isGivenAccountsAreNotSame(null, new CheckingAccount(StringUtils.EMPTY));
		Assert.assertFalse(isSame);
	}
	
	@Test
	public void shouldBeTestGivenSecondAccountIsNullOthersNotNull() {
		boolean isSame = BankingValidation.isGivenAccountsAreNotSame(new CheckingAccount(StringUtils.EMPTY), null);
		Assert.assertFalse(isSame);
	}

	@Test
	public void shouldBeTestGivenBothAccountsAreNotNullAndSameValue() {
		boolean isSame = BankingValidation.isGivenAccountsAreNotSame(new CheckingAccount(StringUtils.EMPTY), new CheckingAccount(StringUtils.EMPTY));
		Assert.assertFalse(isSame);
	}

	@Test
	public void shouldBeTestGivenBothAccountsAreNotNullAndDifferent() {
		boolean isSame = BankingValidation.isGivenAccountsAreNotSame(new CheckingAccount(StringUtils.EMPTY), new CheckingAccount("owner"));
		Assert.assertTrue(isSame);
	}
	
	@Test
	public void shouldBeTestGivenAmountNull() {
		BigDecimal amount = BankingValidation.calculateGivenAmountGraterThanEqualZero(null);
		Assert.assertEquals(BigDecimal.ZERO, amount);
	}
	
	@Test
	public void shouldBeTestGivenAmountZero() {
		BigDecimal amount = BankingValidation.calculateGivenAmountGraterThanEqualZero(BigDecimal.ZERO);
		Assert.assertEquals(BigDecimal.ZERO, amount);
	}
	
	@Test
	public void shouldBeTestGivenAmountTen() {
		BigDecimal amount = BankingValidation.calculateGivenAmountGraterThanEqualZero(BigDecimal.TEN);
		Assert.assertEquals(BigDecimal.TEN, amount);
	}
	
}
