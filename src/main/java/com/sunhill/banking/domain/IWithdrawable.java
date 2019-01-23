package com.sunhill.banking.domain;

import java.math.BigDecimal;

public interface IWithdrawable {
	public boolean withdraw(BigDecimal amount);
}
