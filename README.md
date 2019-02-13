
# Welcome to BankingAPI

This application contains features that are the skeleton of the bank api. The API has been tried to be constructed for intensive use and high sensitivity.
Initially, it contains 2 different types of accounts; checkingAccount and SavingAccount (Extendable). Each account has an owner and a balance.

Inculded Features:

 - deposit
	- to add given amount the balance
	- ex:
	```
	CheckingAccount account = new CheckingAccount("owner1", BigDecimal.TEN);
	// created account balance is 10
	account.getBalance(); // return 10

	// to add 1
	account.deposit(BigDecimal.ONE); // 10 + 1
	account.getBalance(); // return 11
	```
 - withdraw
	- to reduce given amount from the balance
	- ex:
	```
	SavingAccount account = new SavingAccount("owner2", BigDecimal.TEN);
	// created account balance is 10
	account.getBalance(); // return 10

	// to reduce 1
	account.withdraw(BigDecimal.ONE); // 10 - 1
	account.getBalance(); // return 9
	```
 - transfer
	- to transfer the given owner, has to be valid (CheckingAccount) owner and the given amount to be transferred value.
	- to do cash transfers between checking accounts
	- ex:
	```
	CheckingAccount fromAccount = new CheckingAccount("owner1", BigDecimal.TEN);
	CheckingAccount toAccount = new CheckingAccount("owner3", BigDecimal.ZERO);
	// created account balance is 10
	fromAccount.getBalance(); // return 10
	toAccount.getBalance(); // return 0

	fromAccount.transfer(toAccount, BigDecimal.ONE); // transfer process
	fromAccount.getBalance(); // return 9
	toAccount.getBalance(); // return 1
	```
 - payInterest
	- to calculate with given periods (month) for interest rate and to add result the balance. (compound interest method)   
	- ex:
	```
	SavingAccount account = new SavingAccount("owner4", BigDecimal.TEN);
	// created account balance is 10
	account.getBalance(); // return 10
	account.getInterestRate(); // return 0.02 (default value)
	
	// to calculate interest rate
	account.payInterest(2);
	account.getBalance(); // return 10.03
	```
Others;

 - overDraftLimit; to give extra limit

# Building & Running

If you have an IDE, firstly install java then import maven project:

- to import project
	> **existing maven project**
- to use as a library
	> **import as an external library**

On the root of the project, run:

> **mvn clean build**

## Running tests

Using JUnit for testing:

###  Unit tests

> **mvn clean test**

### Build

> **mvn clean install**

## What's changed

- Spring is removed
- to validate more inputs
- to handle more cases
	- different custom exception
- changed/fixed method logic

## Architectural Design and Decisions

### Architecture Used

The application is developed  **_DDD_**  (Domain Driven Design)  for implementation. 
Domain objects are the central part of the application and the application is developed and tested in isolation from its eventual run-time devices and databases. The logic of the application is located the service layer based on domain objects and used interfaces for loose coupling etc. 
Additionally, whem developing, it used  **_TDD culture_  (Test Driven Development)**. Red, Green, and Refactor is the main approach to focus into three phases.