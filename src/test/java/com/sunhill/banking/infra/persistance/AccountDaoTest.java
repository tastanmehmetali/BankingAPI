package com.sunhill.banking.infra.persistance;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.sunhill.banking.domain.Account;

public class AccountDaoTest {

	@InjectMocks
	AccountDao accountDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
	@Test
	public void shouldBeTestAccountWhenOwnerIsNull() {
		Account account = accountDao.findAccountByOwnerName(null);
		Assert.assertNull(account);
	}
    
	@Test
	public void shouldBeTestAccountWhenOwnerIsEmpty() {
		Account account = accountDao.findAccountByOwnerName(StringUtils.EMPTY);
		Assert.assertNull(account);
	}
    
	@Test
	public void shouldBeTestAccountWhenOwnerNameIsOwner1() {
		Account account = accountDao.findAccountByOwnerName("owner1");
		Assert.assertNotNull(account);
	}
	
	
}
