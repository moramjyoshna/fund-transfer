package com.ing.fundtransfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ing.fundtransfer.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{

	Account findByAccountNumber(String accountNumber);
	
	Account findByCustomerId(Long customerId);
}
