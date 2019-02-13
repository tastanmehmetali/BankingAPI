package com.sunhill.banking.domain;

public interface ICalculateInterest {
	
	/**
	 * payInterest to calcualte a given parameter(termInYears) [compound interest method]
	 * 
	 * @param termInYears
	 */
	void payInterest(int termInYears);
}
