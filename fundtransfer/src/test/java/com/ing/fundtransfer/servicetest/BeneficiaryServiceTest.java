package com.ing.fundtransfer.servicetest;

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


import com.ing.fundtransfer.dto.BeneficiaryListDto;
import com.ing.fundtransfer.dto.BeneficiaryRequestDto;
import com.ing.fundtransfer.dto.BeneficiaryResponseDto;
import com.ing.fundtransfer.entity.Account;
import com.ing.fundtransfer.entity.Beneficiary;

import com.ing.fundtransfer.repository.AccountRepository;
import com.ing.fundtransfer.repository.BeneficiaryRepository;

import com.ing.fundtransfer.service.BeneficiaryServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class BeneficiaryServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceTest.class);
	
	@Mock
	AccountRepository accountRepository;

	@InjectMocks
	BeneficiaryServiceImpl beneficiaryServiceImpl;

	@Mock
	BeneficiaryRepository beneficiaryRepository;

	Account account;

	Account beneficiaryAccount;

	BeneficiaryRequestDto beneficiaryRequestDto;

	Beneficiary beneficiary;

	BeneficiaryListDto beneficiaryDto;

	List<BeneficiaryListDto> listBeneficiaryDto;

	List<Beneficiary> listBeneficiary;

	@Before
	public void setup() {
		account = new Account();
		account.setAccountId(1L);
		account.setAccountNumber("36429173934033");
		account.setAccountType("savings");
		account.setBalance(10000D);
		account.setCustomerId(1L);

		beneficiaryAccount = new Account();

		beneficiaryAccount.setAccountId(1L);
		beneficiaryAccount.setAccountNumber("72904846165874");
		beneficiaryAccount.setAccountType("savings");
		beneficiaryAccount.setBalance(10000D);
		beneficiaryAccount.setCustomerId(3L);

		beneficiaryRequestDto = new BeneficiaryRequestDto();
		beneficiaryRequestDto.setBeneficiaryAccountNumber(beneficiaryAccount.getAccountNumber());
		beneficiaryRequestDto.setBeneficiaryName("pavi");
		beneficiaryRequestDto.setCustomerId(1L);

		beneficiary = new Beneficiary();
		beneficiary.setBeneficiaryAccountNumber("72904846165874");
		beneficiary.setBeneficiaryId(1L);
		beneficiary.setBeneficiaryName("pavi");
		beneficiary.setCustomerAccountNumber("36429173934033");

		listBeneficiary = new ArrayList<>();
		listBeneficiary.add(beneficiary);

	}

	@Test
	public void testAddBeneficiary() {
		logger.info("inside add beneficiary");
		Mockito.when(accountRepository.findByCustomerId(Mockito.anyLong())).thenReturn(account);
		Mockito.when(accountRepository.findByAccountNumber(Mockito.anyString())).thenReturn(beneficiaryAccount);
		Mockito.when(beneficiaryRepository.save(Mockito.any())).thenReturn(beneficiary);
		BeneficiaryResponseDto beneficiaryResponseDto = beneficiaryServiceImpl.addBeneficiary(beneficiaryRequestDto);
		Assert.assertEquals(Long.valueOf(1), beneficiaryResponseDto.getBeneficiaryId());
	}

	@Test
	public void testGetAllBeneficiary() {
		Mockito.when(accountRepository.findByCustomerId(Mockito.anyLong())).thenReturn(account);
		Mockito.when(beneficiaryRepository.findByCustomerAccountNumber(Mockito.anyString())).thenReturn(listBeneficiary);
		List<BeneficiaryListDto> listBeneficiaryDto = beneficiaryServiceImpl.getAllBeneficiary(1L);
		Assert.assertEquals(listBeneficiary.size(), listBeneficiaryDto.size());
	}

}
