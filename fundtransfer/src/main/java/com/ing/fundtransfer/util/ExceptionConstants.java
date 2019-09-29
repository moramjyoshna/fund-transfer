package com.ing.fundtransfer.util;

public class ExceptionConstants {
	
	private ExceptionConstants() {
	    throw new IllegalStateException("Utility class");
	  }
	
	public static final String INVALID_FIRST_NAME="Please enter first name in string";
	public static final String INVALID_LAST_NAME="Please enter last name in string";
	public static final String INVALID_MOBILE_NUMBER = "Please enter a valid mobile number";
	public static final String INVALID_EMAIL = "please enter a valid email Id";
	public static final String EXIST_EMAIL = "Email Id already exist";
	public static final String INVALID_AGE = "Age should be greater than 18";
	public static final String REGISTRATION_FAILED = "Registration failed..";
	public static final String CUSTOMER_NOT_FOUND = "Invalid credentials";
	public static final String INVALID_CUSTOMER="Invalid Customer";
	public static final String BENEFICIARY_NOT_FOUND = "Beneficiary account not exist";
	public static final String ADD_BENEFICIARY_FAILED = "Add beneficiary failed..";
	public static final String NO_BENEFICIARY_ADDED = "No beneficiaries have added";
	public static final String ACCOUNT_NOT_FOUND = "No accounts found...!";
	public static final String ACCOUNT_NUMBER_NOT_FOUND = "Such Account Number not exists!!!";
	public static final String MINIMUM_AMOUNT = "Please transfer valid amount";
	public static final String INVALID_BENEFICIARY_ACCOUNT = "Invalid beneficiary account";
	public static final String EXIST_BENEFICIARY_ACCOUNT = "Already added as Beneficiary";
	public static final String NOT_BENEFICIARY = "Not Benficiary";
	public static final String INVALID_ACCOUNT = "From and To account should not be same";
	public static final String INVALID_AMOUNT = "No Minimum Balance, cant transfer amount";
	public static final String TRANSACTION_FAILED = "Transaction failed";
	public static final String WEEK_HISTORY_NOT_FOUND = "No transactions found for last week";
	public static final String MONTH_HISTORY_NOT_FOUND = "No transactions found for this month and year";
	public static final String PLEASE_FIND_ACCOUNT_DETAILS = "Please find account details";
	public static final String NO_TRANSACTION_RECORD_FOUND_EXCEPTION = "No Transaction records found";
}
