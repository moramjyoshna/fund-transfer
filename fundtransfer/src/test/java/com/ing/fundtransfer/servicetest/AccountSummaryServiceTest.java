package com.ing.fundtransfer.servicetest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ing.fundtransfer.dto.AccountSummaryResponseDto;
import com.ing.fundtransfer.entity.Account;
import com.ing.fundtransfer.repository.AccountRepository;
import com.ing.fundtransfer.service.AccountSummaryServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class AccountSummaryServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceTest.class);
	
	@Mock
	AccountRepository accountRepository;
	
	@InjectMocks
	AccountSummaryServiceImpl accountSummaryServiceImpl;
	
	AccountSummaryResponseDto accountSummaryResponseDto;
	
	String accountNumber;
	
	Account account;
	
	@Before
	public void setup() {
		
		account=new Account();
		account.setAccountId(1L);
		account.setAccountNumber("84053768334955");
		account.setAccountType("debit");
		account.setBalance(2000D);
		account.setCustomerId(1L);
		
		accountNumber="84053768334955";
	}
	
	@Test
	public void testGetAccountSummary() {
		logger.info("inside account summary test");
		Mockito.when(accountRepository.findByAccountNumber(Mockito.anyString())).thenReturn(account);
		AccountSummaryResponseDto accountSummaryResponseDto= accountSummaryServiceImpl.getAccountSummary(accountNumber);
		Assert.assertEquals("84053768334955", accountSummaryResponseDto.getAccountNumber());
		Assert.assertEquals("debit", accountSummaryResponseDto.getAccountType());
	}
}
