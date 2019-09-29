package com.ing.fundtransfer.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TransactionHistoryDto{

	private Long transactionId;
	private String accountNumber;
	private LocalDateTime transactionDate;
	private Double transferAmount;
	private String transactionType;
    private String statusMessage;
    private Integer statusCode;
	
}
