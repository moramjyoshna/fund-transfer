package com.ing.fundtransfer.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerRequestDto implements Serializable{

	private static final long serialVersionUID = 1L;
	private String firstName;
	private String lastName;
	private String emailId;
	private Long phoneNumber;
	private String dateOfBirth;
	
}
