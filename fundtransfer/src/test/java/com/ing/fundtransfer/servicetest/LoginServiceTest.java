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

import com.ing.fundtransfer.dto.LoginRequestDto;
import com.ing.fundtransfer.dto.LoginResponseDto;
import com.ing.fundtransfer.entity.Customer;
import com.ing.fundtransfer.repository.AccountRepository;
import com.ing.fundtransfer.repository.CustomerRepository;
import com.ing.fundtransfer.service.LoginServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {

private static final Logger logger = LoggerFactory.getLogger(CustomerServiceTest.class);
	
	@Mock
	CustomerRepository customerRepository;
	
	@Mock
	AccountRepository accountRepository;
	
	@InjectMocks
	LoginServiceImpl loginServiceImpl;
	
	Customer customer;
	
	LoginResponseDto loginResponseDto;
	
	LoginRequestDto loginRequestDto;
	
	@Before
	public void setup() {
		
		customer=new Customer();
		customer.setCustomerId(1L);
		customer.setPassword("@^+=&");
		customer.setFirstName("Jyoshna");
		customer.setLastName("M");
		
		loginRequestDto= new LoginRequestDto();
		loginRequestDto.setCustomerId(1L);
		loginRequestDto.setPassword("@^+=&");
	}
	
	@Test
	public void testLogin() {
		logger.info("inside login test");
		Mockito.when(customerRepository.findByCustomerIdAndPassword(Mockito.anyLong(), Mockito.anyString())).thenReturn(customer);
		LoginResponseDto loginResponseDto= loginServiceImpl.login(loginRequestDto);
		loginResponseDto.setStatusMessage("Login Successfull");
		Assert.assertEquals("Login Successfull", loginResponseDto.getStatusMessage());
		Assert.assertEquals("Jyoshna",loginResponseDto.getFirstName());
		Assert.assertEquals("M", loginResponseDto.getLastName());
	}
}
