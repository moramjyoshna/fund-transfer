package com.ing.fundtransfer.servicetest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import com.ing.fundtransfer.dto.ITransactionHistoryDto;
import com.ing.fundtransfer.dto.TransactionHistoryDto;
import com.ing.fundtransfer.dto.TransferAmountRequestDto;
import com.ing.fundtransfer.dto.TransferAmountResponseDto;
import com.ing.fundtransfer.entity.Account;
import com.ing.fundtransfer.entity.Beneficiary;
import com.ing.fundtransfer.entity.Transaction;
import com.ing.fundtransfer.repository.AccountRepository;
import com.ing.fundtransfer.repository.BeneficiaryRepository;
import com.ing.fundtransfer.repository.TransactionRepository;
import com.ing.fundtransfer.service.TransactionServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceTest.class);

	@Mock
	TransactionRepository transactionRepository;

	@Mock
	AccountRepository accountRepository;
	
	@Mock
	BeneficiaryRepository beneficiaryRepository;

	@InjectMocks
	TransactionServiceImpl transactionServiceImpl;

	TransferAmountResponseDto transferAmountResponseDto;

	TransferAmountRequestDto transferAmountRequestDto;

	Account debitFromAccount;
	
	Account paidToAccount;
	
	Transaction transaction;
	
	Beneficiary beneficiary;
	
    Account account;

	String accountNumber;
	
	String month;
	
	Integer year;

	ProjectionFactory factory;
	
	List<ITransactionHistoryDto> listTransactionResponseDto ;
	
	ITransactionHistoryDto transactionResponseDto ;
	
	TransactionHistoryDto transactionHistoryDto;

	@Before
	public void setup() {
		
		beneficiary = new Beneficiary();
		beneficiary.setBeneficiaryAccountNumber("84053768334955");

		LocalDateTime toDate = LocalDateTime.now();
		debitFromAccount = new Account();
		debitFromAccount.setAccountId(1L);
		debitFromAccount.setAccountNumber("84053768334955");
		debitFromAccount.setAccountType("savings");
		debitFromAccount.setBalance(1000D);
		debitFromAccount.setCustomerId(1L);

		paidToAccount = new Account();
		paidToAccount.setAccountId(1L);
		paidToAccount.setAccountNumber("72904846165874");
		paidToAccount.setAccountType("savings");
		paidToAccount.setBalance(1000D);
		paidToAccount.setCustomerId(1L);

		transaction = new Transaction();
		transaction.setTransactionId(1L);
		transaction.setTransactionDate(toDate);
		transaction.setTransactionType("debit");

		transferAmountRequestDto = new TransferAmountRequestDto();
		transferAmountRequestDto.setCreditTo("84053768334955");
		transferAmountRequestDto.setTransferAmount(1000D);
		transferAmountRequestDto.setCustomerId(1L);
		
		factory = new SpelAwareProxyProjectionFactory();
		listTransactionResponseDto = new ArrayList<ITransactionHistoryDto>();
		transactionResponseDto = factory.createProjection(ITransactionHistoryDto.class);

		listTransactionResponseDto.add(transactionResponseDto);

		accountNumber = "84053768334955";
		month = "SEPTEMBER";
		year = 2019;

		account=new Account();
		account.setAccountId(1L);
		account.setAccountNumber("84053768334955");
		account.setAccountType("Savings");
		account.setBalance(1000D);
		account.setCreateDate(LocalDate.of(2019, 9, 25));
		account.setCustomerId(1L);

	}

	@Test
	public void testTransfer() {
		logger.info("inside create test");
		Mockito.when(accountRepository.findByCustomerId(Mockito.anyLong())).thenReturn(debitFromAccount);
		Mockito.when(accountRepository.findByAccountNumber(Mockito.anyString())).thenReturn(paidToAccount);
		Mockito.when(beneficiaryRepository.findByCustomerAccountNumberAndBeneficiaryAccountNumber(Mockito.anyString(),
				Mockito.anyString())).thenReturn(beneficiary);
		Mockito.when(transactionRepository.save(Mockito.any())).thenReturn(transaction);
		TransferAmountResponseDto transferAmountResponseDto = transactionServiceImpl.transfer(transferAmountRequestDto);
		Assert.assertEquals(Long.valueOf(1), transferAmountResponseDto.getTransactionId());

	}

	@Test
	public void testViewByWeek() {
		Mockito.when(accountRepository.findByAccountNumber(Mockito.any())).thenReturn(account);
		Mockito.when(transactionRepository.findAllByAccountAndTransactionDateBetween(Mockito.any(), Mockito.any(),
				Mockito.any())).thenReturn(listTransactionResponseDto);

		List<ITransactionHistoryDto> resultList = transactionServiceImpl.viewWeekTransaction(accountNumber);

		Assert.assertNotNull(resultList);
	}

//	@Test
//	public void testViewByMonth() {
//
//		Mockito.when(accountRepository.findByAccountNumber(Mockito.any())).thenReturn(account);
//		Mockito.when(transactionRepository.findAllByAccountAndTransactionDateBetween(Mockito.any(), Mockito.any(),
//				Mockito.any())).thenReturn(listTransactionResponseDto);
//
//		List<ITransactionHistoryDto> resultList = transactionServiceImpl.viewMonthTransaction(accountNumber, month,
//				year);
//
//		Assert.assertNotNull(resultList);
//	}
	
//	@Test
//	public void testViewByMonthWeek() {
//
//		Mockito.when(accountRepository.findByAccountNumber(Mockito.any())).thenReturn(account);
//		Mockito.when(transactionRepository.findAllByAccountAndTransactionDateBetween(Mockito.any(), Mockito.any(),
//				Mockito.any())).thenReturn(transactionHistoryDto);
//
//		List<ITransactionHistoryDto> resultList = transactionServiceImpl.viewMonthTransaction(accountNumber, month,
//				year);
//
//		Assert.assertNotNull(resultList);
//	}

}
