package com.sunhill.banking;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BankingApplicationTest {

	@Test
	public void main() {
		BankingApplication.main(new String[] {});
	}
}
