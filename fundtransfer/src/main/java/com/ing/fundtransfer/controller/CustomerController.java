package com.ing.fundtransfer.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ing.fundtransfer.dto.CustomerRequestDto;
import com.ing.fundtransfer.dto.CustomerResponseDto;
import com.ing.fundtransfer.service.CustomerService;
import lombok.extern.slf4j.Slf4j;


@RestController
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@RequestMapping("/customer")
@Slf4j
/**
 * 
 * @author Jyoshna
 *
 */
public class CustomerController {

	@Autowired
	CustomerService customerService;
	
	/*
	 * This method is used to register the customer by providing valid details, while
	 * registering it will generate password for the user. Once user registered
	 * successfully it will generate account for the user.
	 * 
	 * @Body firstName,lastName,dateOfBirth,mobileNumber,emailId
	 * 
	 * @return CustomerResponseDto is the return object which includes
	 * customerId,password,accountNumber,statusMessage,statusCode
	 * 
	 */
	
	@PostMapping("/save")
	public ResponseEntity<CustomerResponseDto> register(@RequestBody CustomerRequestDto customerRequestDto) {
		log.info("inside customer register controller");
		CustomerResponseDto customerResponseDto = customerService.create(customerRequestDto);
		return new ResponseEntity<>(customerResponseDto, HttpStatus.CREATED);
	}
}
