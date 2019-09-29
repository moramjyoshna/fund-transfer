package com.ing.fundtransfer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.ing.fundtransfer.entity.Beneficiary;


@Repository
public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long>{
	
	List<Beneficiary> findByCustomerAccountNumber(String accountNumber);

	Beneficiary findByCustomerAccountNumberAndBeneficiaryAccountNumber(String customerAccountNumber, String beneficiaryAccountNumber);
	
}
