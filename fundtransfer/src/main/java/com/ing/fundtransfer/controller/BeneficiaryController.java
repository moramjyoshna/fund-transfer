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
import com.ing.fundtransfer.dto.BeneficiaryListDto;
import com.ing.fundtransfer.dto.BeneficiaryRequestDto;
import com.ing.fundtransfer.dto.BeneficiaryResponseDto;
import com.ing.fundtransfer.service.BeneficiaryService;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@RequestMapping("/beneficiary")
@Slf4j
/**
 * 
 * @author Jyoshna
 *
 */
public class BeneficiaryController {

	@Autowired
	BeneficiaryService beneficiaryService;
	
	/*
	 * This method is used for the customer, can add the beneficiary by providing valid
	 * details.
	 * 
	 * @Body beneficiaryRequestDto is the input object which includes customerId,
	 * beneficiaryAccountNumber,beneficiaryName
	 * 
	 * @return BeneficiaryResponseDto is the return object which includes
	 * beneficiaryId,statusMessage,statusCode
	 * 
	 */
	
	@PostMapping("/save")
	public ResponseEntity<BeneficiaryResponseDto> addBeneficiary(@RequestBody BeneficiaryRequestDto beneficiaryRequestDto) {
		log.info("inside add beneficiary controller");
		BeneficiaryResponseDto beneficiaryResponseDto = beneficiaryService.addBeneficiary(beneficiaryRequestDto);
		return new ResponseEntity<>(beneficiaryResponseDto, HttpStatus.CREATED);
	}

	/*
	 * This method is used to view their beneficiaries of customer
	 * 
	 * @Param customerId
	 * 
	 * @return List<BeneficiaryListDto> is the return object which includes
	 * beneficiaryAccountNumber,addedDate,beneficiaryId,beneficiaryName
	 * 
	 */
	
	@GetMapping("/view/{customerId}")
	public ResponseEntity<List<BeneficiaryListDto>> getAllBeneficiary(@PathVariable("customerId") Long customerId) {
		log.info("inside list of beneficiaries controller");
		List<BeneficiaryListDto> beneficiaryList = beneficiaryService.getAllBeneficiary(customerId);
		return new ResponseEntity<>(beneficiaryList, HttpStatus.OK);
	}
}
