package com.ing.fundtransfer.service;

import com.ing.fundtransfer.dto.AccountSummaryResponseDto;

public interface AccountSummaryService {

	AccountSummaryResponseDto getAccountSummary(String accountNumber);
}
