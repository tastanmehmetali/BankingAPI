package com.sunhill.banking.infra.rest;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.sunhill.banking.domain.util.BankingMessageUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

	private static final String OWNER1 = "owner1";
	private static final String OWNER2 = "owner2";
	private static final String OWNER3 = "owner3";
	private static final String OWNER4 = "owner4";

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void shouldBeTestAccountInfoWhenIsNull() throws Exception {
		this.mockMvc.perform(get("/account/info/" + null)).andExpect(status().isOk())
				.andExpect(content().string(BankingMessageUtil.ACCOUNT_IS_NOT_EXIST.getValue()));
	}

	@Test
	public void shouldBeTestAccountInfoForOwner1() throws Exception {
		String accountInfo = String.format(BankingMessageUtil.ACCOUNT_INFO.getValue(), OWNER2, BigDecimal.valueOf(20));
		this.mockMvc.perform(get(String.format("/account/info/%s", OWNER2))).andExpect(status().isOk())
				.andExpect(content().string(accountInfo));
	}

	@Test
	public void shouldBeTestAccountDepositForOwner1NotIncludeAmount() throws Exception {
		this.mockMvc.perform(get(String.format("/account/deposit/%s", OWNER1)))
				.andExpect(status().is(HttpStatus.NOT_FOUND.value()));
	}

	@Test
	public void shouldBeTestAccountDepositForOwner1IncludeAmountIsNull() throws Exception {
		this.mockMvc.perform(get(String.format("/account/deposit/%s/%s", OWNER1, null)))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
	}

	@Test
	public void shouldBeTestAccountDepositForOwner1IsEmptyIncludeAmount() throws Exception {
		BigDecimal wantsToAdd = BigDecimal.TEN;
		this.mockMvc.perform(get(String.format("/account/deposit/null/%s", wantsToAdd)))
				.andExpect(status().is(HttpStatus.NOT_ACCEPTABLE.value()));
	}

	@Test
	public void shouldBeTestAccountDepositForOwner1IncludeAmount() throws Exception {
		BigDecimal wantsToAdd = BigDecimal.TEN;
		String accountInfo = String.format(BankingMessageUtil.ACCOUNT_INFO.getValue(), OWNER4,
				BigDecimal.valueOf(40).add(wantsToAdd));
		this.mockMvc.perform(get(String.format("/account/deposit/%s/%s", OWNER4, wantsToAdd)))
				.andExpect(status().isOk()).andExpect(content().string(accountInfo));
	}

	@Test
	public void shouldBeTestAccountWithdrawForOwner1NotIncludeAmount() throws Exception {
		this.mockMvc.perform(get(String.format("/account/withdraw/%s", OWNER1)))
				.andExpect(status().is(HttpStatus.NOT_FOUND.value()));
	}

	@Test
	public void shouldBeTestAccountWithdrawForOwner1IncludeAmountIsNull() throws Exception {
		this.mockMvc.perform(get(String.format("/account/withdraw/%s/%s", OWNER1, null)))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
	}

	@Test
	public void shouldBeTestAccountWithdrawForOwner1IsEmptyIncludeAmount() throws Exception {
		BigDecimal wantsToWithdraw = BigDecimal.TEN;
		this.mockMvc.perform(get(String.format("/account/withdraw/null/%s", wantsToWithdraw)))
				.andExpect(status().is(HttpStatus.NOT_ACCEPTABLE.value()));
	}

	@Test
	public void shouldBeTestAccountWithdrawForOwner1IncludeAmount() throws Exception {
		BigDecimal wantsToWithdraw = BigDecimal.TEN;
		this.mockMvc.perform(get(String.format("/account/withdraw/%s/%s", OWNER1, wantsToWithdraw)))
				.andExpect(status().isOk()).andExpect(content().string(String.valueOf(true)));
	}

	@Test
	public void shouldBeTestAccountTransferForOwner1NotIncludeAmount() throws Exception {
		this.mockMvc.perform(get(String.format("/account/transfer/%s", OWNER1)))
				.andExpect(status().is(HttpStatus.NOT_FOUND.value()));
	}

	@Test
	public void shouldBeTestAccountTransferForOwner1AndOwner2NotIncludeAmount() throws Exception {
		this.mockMvc.perform(get(String.format("/account/transfer/%s/%s", OWNER1, OWNER2)))
				.andExpect(status().is(HttpStatus.NOT_FOUND.value()));
	}

	@Test
	public void shouldBeTestAccountTransferForOwner1IsNullIncludeAmount() throws Exception {
		this.mockMvc.perform(get(String.format("/account/transfer/null/%s/%s", OWNER1, BigDecimal.TEN)))
				.andExpect(status().is(HttpStatus.NOT_ACCEPTABLE.value()));
	}

	@Test
	public void shouldBeTestAccountTransferForOwner1AndOwner2AreNotNullIncludeAmount() throws Exception {
		this.mockMvc.perform(get(String.format("/account/transfer/%s/%s/%s", OWNER1, OWNER2, BigDecimal.ONE)))
				.andExpect(status().is(HttpStatus.NOT_ACCEPTABLE.value()));
	}

	@Test
	public void shouldBeTestAccountTransferForOwner1AndOwner3AreNotNullIncludeAmount() throws Exception {
		MvcResult result = this.mockMvc.perform(get(String.format("/account/info/%s", OWNER1)))
				.andExpect(status().isOk()).andReturn();
		String balanceVal = result.getResponse().getContentAsString().split("  ")[1];
		BigDecimal balance = new BigDecimal(balanceVal);

		if (balance.compareTo(BigDecimal.ONE) > -1) {
			this.mockMvc.perform(get(String.format("/account/transfer/%s/%s/%s", OWNER1, OWNER3, BigDecimal.ONE)))
					.andExpect(status().isOk()).andExpect(content().string(String.valueOf(true)));

		}
	}

}
