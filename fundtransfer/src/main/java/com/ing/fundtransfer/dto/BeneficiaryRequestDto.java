package com.ing.fundtransfer.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BeneficiaryRequestDto implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long customerId;
	private String beneficiaryAccountNumber;
	private String beneficiaryName;
}
