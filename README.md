# Welcome to BankingAPI

This application contains features that are the skeleton of the bank api. The API has been tried to be constructed for intensive use and high sensitivity.
Initially, it contains 2 different types of accounts; checkingAccount and SavingAccount (Extendable). Each account has an owner and a balance.

Inculded Features:
- deposit
- withdraw
- transfer

# Building & Running

If you have an IDE, firstly import maven project and run:

> **spring-boot:run**


On the root of the project, run:

> **mvn clean build**

## Running tests

Using JUnit for testing:

###  Unit tests

> **mvn clean test**

### Integration tests

> **mvn clean verify**

### Build

> **mvn clean install**

All tests should be run and pass after the project should be build. Then run:

```
java -jar target/BankingAPI-0.0.1-SNAPSHOT.jar
```

## Endpoints

The application will be running on localhost:8080 (**<host>**) and the following endpoints will be available:

|                |ASCII                          |HTML                         |
|----------------|-------------------------------|-----------------------------|
|info			 |`'<host>/account/info/{owner}'`            |owner info            |
|deposit         |`"<host>/account/deposit/{owner}/{amount}"`|to deposit for given owner using amount            |
|withdraw        |`<host>/account/withdraw/{owner}/{amount} `|to withdraw for given owner using amount|
|transfer		 |`<host>/account/transfer/{fromOwner}/{toOwner}/{amount}`|to transfer for given first owner to second owner using given amount|

## Architectural Design and Decisions

### Architecture Used

The application is developed  **_DDD_  (Domain Driven Design)**  and  **Hexagonal Architecture**  for implementation. Domain objects are the central part of the application and the application is developed and tested in isolation from its eventual run-time devices and databases. Logic of the application is located the service layer that based on domain objects and using interfaces for loose coupling etc. Also, the infrastructure layer has implemented what it needs. It gives external connections. Developing it used  **_TDD culture_  (Test Driven Development)**.

### Endpoints

REST service for any clients (_Mobile_,  _Web_  or external app as like  _postman_  etc.)

### Application

SpringBoot, a famous and commonly used framework , is used to develop for the application. SpringBoot has a lot of advantages as like embeded tomcat (not need to download tomcat) etc.

### Storage

The decision is an in-Memory ConcurrentHashMap for storage. (ConcurrentHashMap has <key, value> pair. Also, key is given id and value is given data.) The assignment is focused how to scale more easily and get more performance. Decided architecture (an Hexagonal Architecture) gives flexibility to choose or change the storage structure.
