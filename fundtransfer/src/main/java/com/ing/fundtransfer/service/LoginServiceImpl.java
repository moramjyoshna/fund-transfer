package com.ing.fundtransfer.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ing.fundtransfer.dto.LoginRequestDto;
import com.ing.fundtransfer.dto.LoginResponseDto;
import com.ing.fundtransfer.entity.Customer;
import com.ing.fundtransfer.exception.CommonException;
import com.ing.fundtransfer.repository.AccountRepository;
import com.ing.fundtransfer.repository.CustomerRepository;
import com.ing.fundtransfer.util.ExceptionConstants;

import lombok.extern.slf4j.Slf4j;
@Slf4j
/**
 * 
 * @author Jyoshna
 *
 */
@Service
public class LoginServiceImpl implements LoginService, Serializable{

	private static final long serialVersionUID = 1L;

	@Autowired
	transient CustomerRepository customerRepository;

	@Autowired
	transient AccountRepository accountRepository;
	
	/*
	 * This method is used to login the customer by providing valid credentials
	 * 
	 * @Param customerId,password
	 * 
	 * @return LoginResponseDto is the return object which includes firstName,lastName,statusMessage,statusCode
	 * 
	 */

	@Override
	public LoginResponseDto login(LoginRequestDto loginRequestDto) {
		log.info("inside login service");
		Customer customer = customerRepository.findByCustomerIdAndPassword(loginRequestDto.getCustomerId(),loginRequestDto.getPassword());
		if (customer==null) {
			throw new CommonException(ExceptionConstants.CUSTOMER_NOT_FOUND);
		}
		
		customer.setPassword(loginRequestDto.getPassword());
		customer.setCustomerId(loginRequestDto.getCustomerId());
		
		LoginResponseDto loginResponseDto=new LoginResponseDto();
		loginResponseDto.setFirstName(customer.getFirstName());
		loginResponseDto.setLastName(customer.getLastName());
		loginResponseDto.setStatusCode(201);
		loginResponseDto.setStatusMessage("Log in successfull");
		return loginResponseDto;
		
	}

}
