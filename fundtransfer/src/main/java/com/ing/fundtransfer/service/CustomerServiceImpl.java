package com.ing.fundtransfer.service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ing.fundtransfer.dto.CustomerRequestDto;
import com.ing.fundtransfer.dto.CustomerResponseDto;
import com.ing.fundtransfer.entity.Account;
import com.ing.fundtransfer.entity.Customer;
import com.ing.fundtransfer.exception.CommonException;
import com.ing.fundtransfer.repository.AccountRepository;
import com.ing.fundtransfer.repository.CustomerRepository;
import com.ing.fundtransfer.util.ExceptionConstants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
/**
 * 
 * @author Jyoshna
 *
 */
@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	AccountRepository accountRepository;

	Random rand;

	/*
	 * This method is used to register the customer by providing valid details,
	 * while registering it will generate password for the customer. Once user
	 * registered successfully it will generate account for the customer.
	 * 
	 * @Param firstName,lastName,gender,dateOfBirth,phoneNumber,emailId
	 * 
	 * @return CustomerResponseDto is the return object which includes
	 * customerId,password,accountNumber,statusMessage,statusCode
	 * 
	 */

	@Override
	public CustomerResponseDto create(CustomerRequestDto customerRequestDto) {

		log.info("inside customer create service");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String birthDay = customerRequestDto.getDateOfBirth();
		LocalDate dob = LocalDate.parse(birthDay, formatter);

		if (!validateFirstName(customerRequestDto.getFirstName())) {
			throw new CommonException(ExceptionConstants.INVALID_FIRST_NAME);
		}

		if (!validateLastName(customerRequestDto.getLastName())) {
			throw new CommonException(ExceptionConstants.INVALID_LAST_NAME);
		}

		if (!validAge(dob)) {
			throw new CommonException(ExceptionConstants.INVALID_AGE);
		}

		if (!validPhoneNumber(customerRequestDto.getPhoneNumber())) {
			throw new CommonException(ExceptionConstants.INVALID_MOBILE_NUMBER);
		}

		if (!validEmailId(customerRequestDto.getEmailId())) {
			throw new CommonException(ExceptionConstants.INVALID_EMAIL);
		}

		Customer checkCustomerEmail = customerRepository.findByEmailId(customerRequestDto.getEmailId());
		if (checkCustomerEmail != null) {
			throw new CommonException(ExceptionConstants.EXIST_EMAIL);
		}

		Customer customer = new Customer();
		customer.setFirstName(customerRequestDto.getFirstName());
		customer.setLastName(customerRequestDto.getLastName());
		customer.setEmailId(customerRequestDto.getEmailId());
		customer.setPassword(generatePassword());
		customer.setPhoneNumber(customerRequestDto.getPhoneNumber());
		customer.setDateOfBirth(dob);
        Customer customerResponse = customerRepository.save(customer);

		Account account = new Account();
		account.setAccountNumber(accountNumber());
		LocalDate date = LocalDate.now();
		account.setCreateDate(date);
		account.setAccountType("Savings");
		account.setBalance(2000D);
		account.setCustomerId(customer.getCustomerId());
        Account customerAccount = accountRepository.save(account);
		log.info("Account Registered Successfully");

		CustomerResponseDto customerResponseDto = new CustomerResponseDto();
		customerResponseDto.setCustomerId(customerResponse.getCustomerId());
		customerResponseDto.setAccountNumber(customerAccount.getAccountNumber());
		customerResponseDto.setPassword(customerResponse.getPassword());
		customerResponseDto.setStatusCode(201);
		customerResponseDto.setStatusMessage("Customer Registered successfully and account created");
		return customerResponseDto;
	}

	private boolean validateFirstName(String firstName) {
		String name = ("^[a-zA-Z]*$");
		return firstName.matches(name);
	}

	private boolean validateLastName(String lastName) {
		String name = ("^[a-zA-Z]*$");
		return lastName.matches(name);
	}

	private boolean validEmailId(String email) {
		Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(email);
		return (m.find() && m.group().equals(email));
	}

	private boolean validPhoneNumber(Long number) {
		String num = number.toString();
		Pattern p = Pattern.compile("^[0-9]{10}$");
		Matcher m = p.matcher(num);
		return (m.find() && m.group().equals(num));
	}

	private boolean validAge(LocalDate birthDate) {
		boolean result = false;
		int birthYear = birthDate.getYear();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int age = year - birthYear;
		if (age > 18) {
			result = true;
		}
		return result;
	}

	public String accountNumber() {
		rand = new Random();
		String str = null ;
		StringBuilder number = new StringBuilder();
		for (int i = 0; i < 14; i++) {
			int n = rand.nextInt(10) + 0;
			number.append(n);
			str = number.toString();
		}
		return str;
	}

	public String generatePassword() {
		SecureRandom r = new SecureRandom();
		final String alphaCaps = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final String alpha = "abcdefghijklmnopqrstuvwxyz";
		final String numeric = "0123456789";
		final String specialChars = "!@#$%^&*_=+-/";
		int length = 5;
		String str = null ;
		String dic = alphaCaps + alpha + numeric + specialChars;
		StringBuilder result= new StringBuilder();
		for (int i = 0; i < length; i++) {
			int index = r.nextInt(dic.length());
			result.append(dic.charAt(index));
			str=result.toString();
		}

		return str;
	}

}
