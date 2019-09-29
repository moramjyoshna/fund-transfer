package com.ing.fundtransfer.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ing.fundtransfer.dto.LoginRequestDto;
import com.ing.fundtransfer.dto.LoginResponseDto;
import com.ing.fundtransfer.service.LoginService;

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
public class LoginController {

	@Autowired
	LoginService loginService;
	
	/*
	 * This method is used to login the customer by providing valid credentials
	 * 
	 * @Body customerId,password
	 * 
	 * @return LoginResponseDto is the return object which includes firstName,statusMessage.statusCode
	 * 
	 */
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> verify(@RequestBody LoginRequestDto loginRequestDto) {
		log.info("inside login controller");
		LoginResponseDto loginResponseDto = loginService.login(loginRequestDto);
		return new ResponseEntity<>(loginResponseDto, HttpStatus.CREATED);
	}
	
}
