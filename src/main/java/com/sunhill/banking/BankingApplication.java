package com.sunhill.banking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankingApplication {

	private static final Logger logger = LoggerFactory.getLogger(BankingApplication.class);

	/**
	 * 
	 * @param args that required arguments 
	 * 		to initialize, start and configure the web applicaiton 
	 */
	public static void main(final String[] args) {
		logger.info("BankingApplication started!");
		SpringApplication.run(BankingApplication.class, args);
	}
}
