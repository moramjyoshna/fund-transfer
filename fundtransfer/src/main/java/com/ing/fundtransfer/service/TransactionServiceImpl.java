package com.ing.fundtransfer.service;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ing.fundtransfer.dto.ITransactionHistoryDto;
import com.ing.fundtransfer.dto.TransferAmountRequestDto;
import com.ing.fundtransfer.dto.TransferAmountResponseDto;
import com.ing.fundtransfer.entity.Account;
import com.ing.fundtransfer.entity.Beneficiary;
import com.ing.fundtransfer.entity.Transaction;
import com.ing.fundtransfer.exception.CommonException;
import com.ing.fundtransfer.repository.AccountRepository;
import com.ing.fundtransfer.repository.BeneficiaryRepository;
import com.ing.fundtransfer.repository.TransactionRepository;
import com.ing.fundtransfer.util.ExceptionConstants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
/**
 * 
 * @author Jyoshna
 *
 */
@Transactional
@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	BeneficiaryRepository beneficiaryRepository;

	/*
	 * This method is used to transfer the amount by providing valid credentials
	 * 
	 * @Param creditTo,customerId,transferAmount
	 * 
	 * @return TransferAmountResponseDto is the return object which includes
	 * transactionId,message,statusCode
	 * 
	 */

	@Override
	public TransferAmountResponseDto transfer(TransferAmountRequestDto transferAmountRequestDto) {
		log.info("inside transaction service");

		TransferAmountResponseDto transferAmountResponseDto = new TransferAmountResponseDto();

		Transaction debitTransaction = new Transaction();
		Transaction creditTransaction = new Transaction();

		Account fromAccount = accountRepository.findByCustomerId(transferAmountRequestDto.getCustomerId());
		Account toAccount = accountRepository.findByAccountNumber(transferAmountRequestDto.getCreditTo());

		if (fromAccount == null) {
			throw new CommonException(ExceptionConstants.ACCOUNT_NOT_FOUND);
		}

		if (toAccount == null) {
			throw new CommonException(ExceptionConstants.ACCOUNT_NOT_FOUND);
		}

		if (fromAccount.getAccountNumber().equalsIgnoreCase(toAccount.getAccountNumber())) {
			throw new CommonException(ExceptionConstants.INVALID_ACCOUNT);
		}

		Beneficiary existBeneficiary = beneficiaryRepository.findByCustomerAccountNumberAndBeneficiaryAccountNumber(
				fromAccount.getAccountNumber(), toAccount.getAccountNumber());

		if (existBeneficiary == null) {
			throw new CommonException(ExceptionConstants.NOT_BENEFICIARY);
		}

		if (fromAccount.getBalance() < transferAmountRequestDto.getTransferAmount()) {
			throw new CommonException(ExceptionConstants.INVALID_AMOUNT);
		}

		if (transferAmountRequestDto.getTransferAmount() <= 0) {
			throw new CommonException(ExceptionConstants.MINIMUM_AMOUNT);
		}

		debitTransaction.setAccountNumber(fromAccount.getAccountNumber());
		debitTransaction.setTransactionType("debit");
		debitTransaction.setTransferAmount(transferAmountRequestDto.getTransferAmount());
		debitTransaction.setTransactionDate(LocalDateTime.now());
		debitTransaction.setAccount(fromAccount);

		creditTransaction.setAccountNumber(toAccount.getAccountNumber());
		creditTransaction.setTransactionType("credit");
		creditTransaction.setTransferAmount(transferAmountRequestDto.getTransferAmount());
		creditTransaction.setTransactionDate(LocalDateTime.now());
		creditTransaction.setAccount(toAccount);

		double remainingBalance = fromAccount.getBalance() - transferAmountRequestDto.getTransferAmount();
		double updatedBalance = toAccount.getBalance() + transferAmountRequestDto.getTransferAmount();

		fromAccount.setBalance(remainingBalance);
		toAccount.setBalance(updatedBalance);

		accountRepository.save(fromAccount);

		Transaction transaction = transactionRepository.save(debitTransaction);
		if (transaction.getTransactionId() == null) {
			throw new CommonException(ExceptionConstants.TRANSACTION_FAILED);
		}
		accountRepository.save(toAccount);

		transactionRepository.save(creditTransaction);

		transferAmountResponseDto.setMessage("Amount Transferred successfully..");
		transferAmountResponseDto.setTransactionId(transaction.getTransactionId());
		transferAmountResponseDto.setStatusCode(201);
		return transferAmountResponseDto;
	}

	/*
	 * This method is to view transaction details of customer for last week by
	 * providing valid accountNumber
	 * 
	 * @Param accountNumber
	 * 
	 * @return List<ITransactionHistoryDto> is the return object which includes
	 * transactionId,debitFrom,creditTo,transactionType,transferAmount,
	 * transactionDate
	 * 
	 */

	@Override
	public List<ITransactionHistoryDto> viewWeekTransaction(String accountNumber) {
		log.info("inside week transaction");
		LocalDateTime fromDate = LocalDateTime.now().minusWeeks(1L);
		LocalDateTime toDate = LocalDateTime.now();

		Account account = accountRepository.findByAccountNumber(accountNumber);
		if (account == null) {
			throw new CommonException(ExceptionConstants.ACCOUNT_NUMBER_NOT_FOUND);
		}
		if (transactionRepository.findAllByAccountAndTransactionDateBetween(account, fromDate, toDate).isEmpty()) {
			throw new CommonException(ExceptionConstants.WEEK_HISTORY_NOT_FOUND);
		}
		return transactionRepository.findAllByAccountAndTransactionDateBetween(account, fromDate, toDate);

	}

	/*
	 * This method is to view transaction details of customer for selected month by
	 * providing valid accountNumber
	 * 
	 * @Param accountNumber
	 * 
	 * @return List<ITransactionHistoryDto> is the return object which includes
	 * transactionId,debitFrom,creditTo,transactionType,transferAmount,
	 * transactionDate
	 * 
	 */

	@Override
	public List<ITransactionHistoryDto> viewMonthTransaction(String accountNumber, String month, Integer year) {
		Month mon = Month.valueOf(month);
		log.info("inside month transaction");
		YearMonth requestMonth = YearMonth.of(year, mon);
		LocalDateTime fromDate = requestMonth.atDay(1).atTime(0, 0, 0);
		LocalDateTime toDate = requestMonth.atEndOfMonth().atTime(23, 59, 59);

		Account account = accountRepository.findByAccountNumber(accountNumber);
		if (account == null) {
			throw new CommonException(ExceptionConstants.ACCOUNT_NUMBER_NOT_FOUND);
		}
		if (transactionRepository.findAllByAccountAndTransactionDateBetween(account, fromDate, toDate).isEmpty()) {
			throw new CommonException(ExceptionConstants.MONTH_HISTORY_NOT_FOUND);
		}
		return transactionRepository.findAllByAccountAndTransactionDateBetween(account, fromDate, toDate);
	}
	
	@Override
	public List<ITransactionHistoryDto> viewWeekMonthTransaction(String accountNumber, String month, Integer year) {
		Month mon = Month.valueOf(month);
		log.info("inside month transaction");
		YearMonth requestMonth = YearMonth.of(year, mon);
		LocalDateTime fromDate = requestMonth.atDay(1).atTime(0, 0, 0);
		LocalDateTime toDate = requestMonth.atEndOfMonth().atTime(23, 59, 59);

		Account account = accountRepository.findByAccountNumber(accountNumber);
		if (account == null) {
			throw new CommonException(ExceptionConstants.ACCOUNT_NUMBER_NOT_FOUND);
		}
		if (transactionRepository.findAllByAccountAndTransactionDateBetween(account, fromDate, toDate).isEmpty()) {
			throw new CommonException(ExceptionConstants.MONTH_HISTORY_NOT_FOUND);
		}
		return transactionRepository.findAllByAccountAndTransactionDateBetween(account, fromDate, toDate);
	}

}
