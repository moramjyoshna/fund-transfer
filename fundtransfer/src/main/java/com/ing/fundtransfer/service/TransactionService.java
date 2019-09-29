package com.ing.fundtransfer.service;

import java.util.List;

import com.ing.fundtransfer.dto.ITransactionHistoryDto;
import com.ing.fundtransfer.dto.TransferAmountRequestDto;
import com.ing.fundtransfer.dto.TransferAmountResponseDto;

public interface TransactionService {

	TransferAmountResponseDto transfer(TransferAmountRequestDto transferAmountRequestDto);
	
	List<ITransactionHistoryDto> viewWeekTransaction(String accountNumber);
	
	List<ITransactionHistoryDto> viewMonthTransaction(String accountNumber, String month, Integer year);
	
	List<ITransactionHistoryDto> viewWeekMonthTransaction(String accountNumber, String month, Integer year);
	
}
