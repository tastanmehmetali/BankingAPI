package com.sunhill.banking.service;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sunhill.banking.domain.Account;
import com.sunhill.banking.domain.CheckingAccount;
import com.sunhill.banking.domain.util.BankingMessageUtil;
import com.sunhill.banking.infra.persistance.IAccountDao;
import com.sunhill.banking.service.exception.BankingException;

public class AccountServiceTest {

	private static final String OWNER2 = "owner2";
	private static final String OWNER = "owner";
	private static final String OWNER1 = "owner1";

	@Mock
	private IAccountDao accountDao;

	@InjectMocks
	private AccountService accountService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void shouldBeTestInfoWhenOwnerIsNull() {
    	String accountInfo = accountService.info(null);
    	Assert.assertEquals(BankingMessageUtil.OWNER_INFO.getValue(), accountInfo);
    }
    
    @Test
    public void shouldBeTestInfoWhenOwnerIsEmpty() {
    	String accountInfo = accountService.info(StringUtils.EMPTY);
    	Assert.assertEquals(BankingMessageUtil.ACCOUNT_IS_NOT_EXIST.getValue(), accountInfo);
    }
    
    @Test
    public void shouldBeTestInfoWhenOwnerIsOwner() {
    	String accountInfo = accountService.info(OWNER);
    	Assert.assertEquals(BankingMessageUtil.ACCOUNT_IS_NOT_EXIST.getValue(), accountInfo);
    }
    
    @Test
    public void shouldBeTestDepositWhenOwnerIsNull() {
    	try {
    		accountService.deposit(null, null);
		} catch (Throwable expected) {
			Assert.assertEquals(NullPointerException.class, expected.getClass());
			Assert.assertEquals(BankingMessageUtil.OWNER_INFO.getValue(), expected.getMessage());
		}
    }
    
    @Test(expected = NullPointerException.class)
    public void shouldBeTestDepositWhenOwnerIsEmpty() {
    	String owner = StringUtils.EMPTY;
    	Account accout = accountDao.findAccountByOwnerName(owner);
    	BigDecimal pastBalanceVal = accout.getBalance();
    	BigDecimal wantsToAddAmount = accout.getBalance().multiply(BigDecimal.valueOf(2));
    	accountService.deposit(owner, wantsToAddAmount);
    	
    	Assert.assertEquals(wantsToAddAmount.add(pastBalanceVal), accout.getBalance());
    }
    
    @Test
    public void shouldBeTestDepositWhenOwnerNameIsOwner1() {
    	Account accout = new CheckingAccount(OWNER1, BigDecimal.TEN);
    	when(accountDao.findAccountByOwnerName(OWNER1)).thenReturn(accout);
        
    	BigDecimal pastBalanceVal = accout.getBalance();
    	BigDecimal wantsToAddAmount = accout.getBalance().multiply(BigDecimal.valueOf(2));
    	accountService.deposit(OWNER1, wantsToAddAmount);
    	
    	Assert.assertEquals(wantsToAddAmount.add(pastBalanceVal), accout.getBalance());
    }
    
    @Test
    public void shouldBeTestInfoWhenOwnerNameIsOwner1() {
    	Account accout = new CheckingAccount(OWNER1, BigDecimal.TEN);
    	String preparedOwnerInfo =  String.format(BankingMessageUtil.ACCOUNT_INFO.getValue(), OWNER1, BigDecimal.TEN);
    	when(accountDao.findAccountByOwnerName(OWNER1)).thenReturn(accout);
        
    	String wantsToGetOwnerInfo = accountService.info(OWNER1);
    	Assert.assertEquals(preparedOwnerInfo, wantsToGetOwnerInfo);
    }
    
    @Test
    public void shouldBeTestWithdrawWhenOwnerNameIsNull() {
    	Account accout = new CheckingAccount(OWNER1, BigDecimal.TEN);
    	when(accountDao.findAccountByOwnerName(OWNER1)).thenReturn(accout);
        
    	BigDecimal pastBalanceVal = accout.getBalance();
    	BigDecimal wantsToWithdrawAmount = BigDecimal.ONE;
    	accountService.withdraw(OWNER1, wantsToWithdrawAmount);
    	
    	Assert.assertEquals(pastBalanceVal.subtract(wantsToWithdrawAmount), accout.getBalance());
    }
    
    @Test
    public void shouldBeTestTransferWhenFromOwnerAndToownerIsNull() {
    	try {
    		accountService.transfer(null, null, null);
		} catch (Throwable expected) {
			Assert.assertEquals(NullPointerException.class, expected.getClass());
			Assert.assertEquals(BankingMessageUtil.OWNER_INFO.getValue(), expected.getMessage());
		}
    }
    
    @Test
    public void shouldBeTestTransferWhenFromOwnerIsNullToOwnerIsNotNull() {
    	try {
    		accountService.transfer(null, StringUtils.EMPTY, null);
		} catch (Throwable expected) {
			Assert.assertEquals(NullPointerException.class, expected.getClass());
			Assert.assertEquals(BankingMessageUtil.OWNER_INFO.getValue(), expected.getMessage());
		}
    }
    
    @Test
    public void shouldBeTestTransferWhenFromOwnerIsNotNullToOwnerIsNull() {
    	try {
    		accountService.transfer(StringUtils.EMPTY, null, null);
		} catch (Throwable expected) {
			Assert.assertEquals(BankingException.class, expected.getClass());
			Assert.assertEquals(BankingMessageUtil.ACCOUNT_IS_NOT_EXIST.getValue(), expected.getMessage());
		}
    }
    
    @Test
    public void shouldBeTestTransferWhenFromOwnerAndToOwnerAreNotNull() {
    	try {
    		accountService.transfer(OWNER1, OWNER, null);
		} catch (Throwable expected) {
			Assert.assertEquals(BankingException.class, expected.getClass());
			Assert.assertEquals(BankingMessageUtil.ACCOUNT_IS_NOT_EXIST.getValue(), expected.getMessage());
		}
    }
    
}
