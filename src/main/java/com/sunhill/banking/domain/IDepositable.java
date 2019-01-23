package com.sunhill.banking.domain;

import java.math.BigDecimal;

public interface IDepositable {
	public void deposit(BigDecimal amount);
}
