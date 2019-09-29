package com.ing.fundtransfer.dto;

import java.time.LocalDateTime;

public interface ITransactionHistoryDto {

	Long getTransactionId();
	String getAccountNumber();
	LocalDateTime getTransactionDate();
	String getTransactionType();
	Double getTransferAmount();
}
