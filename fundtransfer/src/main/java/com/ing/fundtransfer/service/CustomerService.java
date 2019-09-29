package com.ing.fundtransfer.service;

import com.ing.fundtransfer.dto.CustomerRequestDto;
import com.ing.fundtransfer.dto.CustomerResponseDto;

public interface CustomerService {

	CustomerResponseDto create(CustomerRequestDto customerRequestDto);
}
