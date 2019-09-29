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

import com.ing.fundtransfer.dto.CustomerRequestDto;
import com.ing.fundtransfer.dto.CustomerResponseDto;
import com.ing.fundtransfer.entity.Account;
import com.ing.fundtransfer.entity.Customer;
import com.ing.fundtransfer.repository.AccountRepository;
import com.ing.fundtransfer.repository.CustomerRepository;
import com.ing.fundtransfer.service.CustomerServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceTest.class);
	
	@Mock
	CustomerRepository customerRepository;
	
	@Mock
	AccountRepository accountRepository;
	
	@InjectMocks
	CustomerServiceImpl customerServiceImpl;

	CustomerRequestDto customerRequestDto;
	
	CustomerResponseDto customerResponseDto;
	
	Customer customer;
	
	Account account;
	
	@Before
	public void setup() {
		customerRequestDto=new CustomerRequestDto();
		customerRequestDto.setFirstName("Jyoshna");
		customerRequestDto.setLastName("M");
		customerRequestDto.setDateOfBirth("1996-04-12");
		customerRequestDto.setEmailId("jyoshnam12@gmail.com");
		customerRequestDto.setPhoneNumber(7598163292L);
		
		customer=new Customer();
		customer.setCustomerId(1L);
		customer.setEmailId("jyoshnam12@gmail");
		customer.setFirstName("Jyoshna");
		customer.setLastName("M");
		customer.setPassword("@^+=&");
		customer.setPhoneNumber(7598163292L);
		
		account=new Account();
		account.setAccountId(1L);
		account.setAccountNumber("84053768334955");
		account.setAccountType("debit");
		account.setBalance(2000D);
		account.setCustomerId(1L);
	}
	
	@Test
	public void testCreate() {
		logger.info("inside create test");
		Mockito.when(customerRepository.findByEmailId(Mockito.anyString())).thenReturn(null);
		Mockito.when(customerRepository.save(Mockito.any())).thenReturn(customer);
		Mockito.when(accountRepository.save(Mockito.any())).thenReturn(account);
		customerResponseDto= customerServiceImpl.create(customerRequestDto);
		Assert.assertEquals("84053768334955", customerResponseDto.getAccountNumber());
	}
}
