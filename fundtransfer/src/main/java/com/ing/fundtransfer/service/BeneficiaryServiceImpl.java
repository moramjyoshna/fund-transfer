package com.ing.fundtransfer.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ing.fundtransfer.dto.BeneficiaryListDto;
import com.ing.fundtransfer.dto.BeneficiaryRequestDto;
import com.ing.fundtransfer.dto.BeneficiaryResponseDto;
import com.ing.fundtransfer.entity.Account;
import com.ing.fundtransfer.entity.Beneficiary;
import com.ing.fundtransfer.exception.CommonException;
import com.ing.fundtransfer.repository.AccountRepository;
import com.ing.fundtransfer.repository.BeneficiaryRepository;
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
public class BeneficiaryServiceImpl implements BeneficiaryService {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	BeneficiaryRepository beneficiaryRepository;

	/*
	 * This method is used to add the beneficiary to customer account by providing
	 * valid details
	 * 
	 * @Param customerId, beneficiaryAccountNumber, beneficiaryName
	 * 
	 * @return BeneficiaryResponseDto is the return object which includes
	 * beneficiaryId,statusMessage,statusCode
	 * 
	 */

	@Override
	public BeneficiaryResponseDto addBeneficiary(BeneficiaryRequestDto beneficiaryRequestDto) {
		log.info("inside add beneficiary Service");
		Account customerAccount = accountRepository.findByCustomerId(beneficiaryRequestDto.getCustomerId());
		Account beneficiaryAccount = accountRepository
				.findByAccountNumber(beneficiaryRequestDto.getBeneficiaryAccountNumber());

		if (customerAccount.getAccountNumber().equalsIgnoreCase(beneficiaryRequestDto.getBeneficiaryAccountNumber())) {
			throw new CommonException(ExceptionConstants.INVALID_BENEFICIARY_ACCOUNT);
		}

		if (beneficiaryAccount == null) {
			throw new CommonException(ExceptionConstants.BENEFICIARY_NOT_FOUND);
		}
		
		if(beneficiaryRepository.findByCustomerAccountNumberAndBeneficiaryAccountNumber(customerAccount.getAccountNumber(), beneficiaryAccount.getAccountNumber())!=null) {
			throw new CommonException(ExceptionConstants.EXIST_BENEFICIARY_ACCOUNT);
		}
		Beneficiary beneficiary = new Beneficiary();
		beneficiary.setBeneficiaryAccountNumber(beneficiaryRequestDto.getBeneficiaryAccountNumber());
		beneficiary.setCustomerAccountNumber(customerAccount.getAccountNumber());
		beneficiary.setBeneficiaryName(beneficiaryRequestDto.getBeneficiaryName());
		beneficiary.setAddedDate(LocalDate.now());
		Beneficiary response = beneficiaryRepository.save(beneficiary);
		if (response.getBeneficiaryId() == null) {
			throw new CommonException(ExceptionConstants.ADD_BENEFICIARY_FAILED);
		}
		BeneficiaryResponseDto beneficiaryResponseDto = new BeneficiaryResponseDto();
		beneficiaryResponseDto.setBeneficiaryId(response.getBeneficiaryId());
		beneficiaryResponseDto.setStatusMessage("Beneficiary added successfully");
		beneficiaryResponseDto.setStatusCode(201);
		return beneficiaryResponseDto;
	}

	/*
	 * This method is used to view the list of beneficiaries for particular customer
	 * by providing valid details
	 * 
	 * @Param customerId
	 * 
	 * @return List<BeneficiaryListDto> is the return object which includes
	 * beneficiaryId,beneficiaryName,beneficiaryAccountNumber,addedDate
	 * 
	 */

	@Override
	public List<BeneficiaryListDto> getAllBeneficiary(Long customerId) {
		List<BeneficiaryListDto> beneficiaryListDto = new ArrayList<>();
		Account customerAccount = accountRepository.findByCustomerId(customerId);
		if (customerAccount == null) {
			throw new CommonException(ExceptionConstants.INVALID_CUSTOMER);
		}
		List<Beneficiary> beneficiaryList = beneficiaryRepository
				.findByCustomerAccountNumber(customerAccount.getAccountNumber());

		beneficiaryList.stream().forEach(b -> {
			BeneficiaryListDto listBeneficiaryDto = new BeneficiaryListDto();
			listBeneficiaryDto.setBeneficiaryAccountNumber(b.getBeneficiaryAccountNumber());
			listBeneficiaryDto.setAddedDate(b.getAddedDate());
			listBeneficiaryDto.setBeneficiaryId(b.getBeneficiaryId());
			listBeneficiaryDto.setBeneficiaryName(b.getBeneficiaryName());
			beneficiaryListDto.add(listBeneficiaryDto);
		});
		if (beneficiaryListDto.isEmpty()) {
			throw new CommonException(ExceptionConstants.NO_BENEFICIARY_ADDED);
		}
		return beneficiaryListDto;
	}
	
}
