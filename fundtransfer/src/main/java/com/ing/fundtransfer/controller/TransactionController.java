package com.ing.fundtransfer.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ing.fundtransfer.dto.ITransactionHistoryDto;
import com.ing.fundtransfer.dto.TransferAmountRequestDto;
import com.ing.fundtransfer.dto.TransferAmountResponseDto;
import com.ing.fundtransfer.service.TransactionService;

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
public class TransactionController {

	@Autowired
	TransactionService transactionService;
	
	/*
	 * This method is used to fund transfer by providing valid credentials
	 * 
	 * @Body customerId,creditTo,transferAmount
	 * 
	 * @return TransferAmountResponseDto is the return object which includes transactionId,statusMessage.statusCode
	 * 
	 */
	
	@PostMapping("/transfer")
	public ResponseEntity<TransferAmountResponseDto> amountTransfer(@RequestBody TransferAmountRequestDto transferAmountRequestDto) {
		log.info("inside fund transafer controller");
		TransferAmountResponseDto transferAmountResponseDto = transactionService.transfer(transferAmountRequestDto);
		return new ResponseEntity<>(transferAmountResponseDto, HttpStatus.OK);
	}
	
	/*
	 * This method is used to view last week transactions by providing valid credentials
	 * 
	 * @Param accountNumber
	 * 
	 * @return List<ITransactionHistoryDto> is the return object which includes transactionId,accountNumber,transactionDate,transactionType,transferAmount
	 * 
	 */
		
	@GetMapping("/transaction/{accountNumber}")
	public List<ITransactionHistoryDto> viewWeekTransaction(@PathVariable("accountNumber") String accountNumber){
		log.info("inside week transaction controller");
		return transactionService.viewWeekTransaction(accountNumber);
	}
	
	/*
	 * This method is used to view selected month transactions by providing valid credentials
	 * 
	 * @Param accountNumber,month,year
	 * 
	 * @return List<ITransactionHistoryDto> is the return object which includes transactionId,accountNumber,transactionDate,transactionType,transferAmount
	 * 
	 */
	
	@GetMapping("/transaction/{accountNumber}/{month}/{year}")
	public List<ITransactionHistoryDto> viewMonthTransaction(@PathVariable("accountNumber") String accountNumber, @PathVariable("month") String month, @PathVariable("year") Integer year){
		log.info("inside month transaction controller");
		return transactionService.viewMonthTransaction(accountNumber, month, year);
	}
}
