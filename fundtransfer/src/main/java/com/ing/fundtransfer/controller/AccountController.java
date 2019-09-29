package com.ing.fundtransfer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ing.fundtransfer.dto.AccountSummaryResponseDto;
import com.ing.fundtransfer.service.AccountSummaryService;
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
public class AccountController {

	@Autowired
	AccountSummaryService accountSummaryService;
	
	/*
	 * This method is used to get the account details for particular customer.
	 * 
	 * @Param accountNumber
	 * 
	 * @return AccountSummaryResponseDto is the return object which includes
	 * accountNumber,accountType,balance,createdDate
	 * 
	 */
	
	@GetMapping("/{accountNumber}")
	public ResponseEntity<AccountSummaryResponseDto> getAccountSummary(@PathVariable String accountNumber) {
		log.info("inside account summary controller");
		return new ResponseEntity<>(accountSummaryService.getAccountSummary(accountNumber), HttpStatus.OK);
	}
}
