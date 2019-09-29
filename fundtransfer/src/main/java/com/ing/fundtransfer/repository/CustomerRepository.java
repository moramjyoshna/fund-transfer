package com.ing.fundtransfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ing.fundtransfer.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{

	Customer findByCustomerIdAndPassword(Long customerId, String password);

	Customer findByCustomerId(Long customerId);

	Customer findByEmailId(String emailId);

}
