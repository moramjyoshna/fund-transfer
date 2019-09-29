package com.ing.fundtransfer.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TransferAmountResponseDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long transactionId;
	private String message;
	private Integer statusCode;
}
