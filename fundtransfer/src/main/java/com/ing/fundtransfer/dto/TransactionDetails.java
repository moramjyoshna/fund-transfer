package com.ing.fundtransfer.dto;

import java.time.LocalDate;

public interface TransactionDetails {

	Double getTransferAmount();

	LocalDate getTransactionDate();

	String getCreditTo();
}
