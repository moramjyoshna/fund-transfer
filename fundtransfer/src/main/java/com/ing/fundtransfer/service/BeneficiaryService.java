package com.ing.fundtransfer.service;

import java.util.List;

import com.ing.fundtransfer.dto.BeneficiaryListDto;
import com.ing.fundtransfer.dto.BeneficiaryRequestDto;
import com.ing.fundtransfer.dto.BeneficiaryResponseDto;


public interface BeneficiaryService {

	BeneficiaryResponseDto addBeneficiary(BeneficiaryRequestDto beneficiaryRequestDto);
	
	List<BeneficiaryListDto> getAllBeneficiary(Long customerId);
}
