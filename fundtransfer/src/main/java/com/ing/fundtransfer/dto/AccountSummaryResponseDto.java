package com.ing.fundtransfer.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountSummaryResponseDto implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long accountId;
	private String accountNumber;
	private String accountType;
	private Double balance;
	private String message;
}
