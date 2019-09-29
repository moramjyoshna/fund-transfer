package com.ing.fundtransfer.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ing.fundtransfer.dto.ITransactionHistoryDto;
import com.ing.fundtransfer.entity.Account;
import com.ing.fundtransfer.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>{
	
	List<ITransactionHistoryDto> findAllByAccountAndTransactionDateBetween(Account account, LocalDateTime fromDate, LocalDateTime toDate);
	
}
