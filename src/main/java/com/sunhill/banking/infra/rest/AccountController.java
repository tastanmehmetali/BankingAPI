package com.sunhill.banking.infra.rest;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sunhill.banking.service.IAccountService;
import com.sunhill.banking.service.exception.BankingException;

@RestController()
@RequestMapping("/account")
public class AccountController {

	@Autowired
	IAccountService accountService;
	
	@GetMapping(value = "/info/{owner}")
	public ResponseEntity<String> executeInfo(@PathVariable(value = "owner") final String owner) {
		String accountInfo = accountService.info(owner);
		return new ResponseEntity<>(accountInfo, HttpStatus.OK);
		
	}

	@GetMapping(value = "/deposit/{owner}/{amount}")
	public ResponseEntity<String> executeDeposit(@PathVariable(value = "owner", required=true) final String owner, @PathVariable(value = "amount", required=true) final BigDecimal amount) {
		try {
			String accountInfo = accountService.deposit(owner, amount);
			return new ResponseEntity<>(accountInfo, HttpStatus.OK);			
		} catch (BankingException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@GetMapping(value = "/withdraw/{owner}/{amount}")
	public ResponseEntity<String> executeWithdraw(@PathVariable(value = "owner", required=true) final String owner, @PathVariable(value = "amount", required=true) final BigDecimal amount) {
		try {
			boolean isWithdraw = accountService.withdraw(owner, amount);
			return new ResponseEntity<>(String.valueOf(isWithdraw), HttpStatus.OK);			
		} catch (BankingException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@GetMapping(value = "/transfer/{fromOwner}/{toOwner}/{amount}")
	public ResponseEntity<String> executeTransfer(@PathVariable(value = "fromOwner", required=true) final String fromOwner, @PathVariable(value = "toOwner", required=true) final String toOwner, @PathVariable(value = "amount", required=true) final BigDecimal amount) {
		try {
			boolean isTransfer = accountService.transfer(fromOwner, toOwner, amount);
			return new ResponseEntity<>(String.valueOf(isTransfer), HttpStatus.OK);			
		} catch (BankingException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
		}
	}

}
