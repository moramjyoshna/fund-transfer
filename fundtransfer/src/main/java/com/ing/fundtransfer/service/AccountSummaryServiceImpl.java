package com.ing.fundtransfer.service;

import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ing.fundtransfer.dto.AccountSummaryResponseDto;
import com.ing.fundtransfer.entity.Account;
import com.ing.fundtransfer.exception.CommonException;
import com.ing.fundtransfer.repository.AccountRepository;
import com.ing.fundtransfer.util.ExceptionConstants;

import lombok.extern.slf4j.Slf4j;
@Slf4j
/**
 * 
 * @author Jyoshna
 *
 */
@Service
public class AccountSummaryServiceImpl implements AccountSummaryService, Serializable{

	private static final long serialVersionUID = 1L;

	@Autowired
	transient AccountRepository accountRepository;
	
	/*
	 * This method is used to view the customer account summary by providing valid accountNumber
	 * 
	 * @Param accountNumber
	 * 
	 * @return AccountSummaryResponseDto is the return object which includes accountId,accountNumber,accountType,balance,statusMessage,statusCode
	 * 
	 */
	
	@Override
	public AccountSummaryResponseDto getAccountSummary(String accountNumber) {
		log.info("inside account summary service");
		Account account=accountRepository.findByAccountNumber(accountNumber);
		if (account==null) {
			throw new CommonException(ExceptionConstants.ACCOUNT_NOT_FOUND);
		}	

		AccountSummaryResponseDto accountSummaryResponse = new AccountSummaryResponseDto();
		accountSummaryResponse.setMessage(ExceptionConstants.PLEASE_FIND_ACCOUNT_DETAILS);
		accountSummaryResponse.setAccountId(account.getAccountId());
		accountSummaryResponse.setAccountType(account.getAccountType());
		accountSummaryResponse.setAccountNumber(account.getAccountNumber());
		accountSummaryResponse.setBalance(account.getBalance());
		return accountSummaryResponse;

	}
	
}
