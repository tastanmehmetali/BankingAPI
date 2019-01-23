package com.sunhill.banking.infra.persistance;

import org.springframework.stereotype.Repository;

import com.sunhill.banking.domain.Account;

@Repository
public interface IAccountDao {

	Account findAccountByOwnerName(String owner);
	
}
