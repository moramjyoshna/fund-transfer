package com.ing.fundtransfer.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BeneficiaryResponseDto implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long beneficiaryId;
	private String statusMessage;
	private Integer statusCode;
}
