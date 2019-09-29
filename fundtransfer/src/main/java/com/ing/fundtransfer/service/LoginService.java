package com.ing.fundtransfer.service;

import com.ing.fundtransfer.dto.LoginRequestDto;
import com.ing.fundtransfer.dto.LoginResponseDto;

public interface LoginService {
	
	LoginResponseDto login(LoginRequestDto loginRequestDto);
}
