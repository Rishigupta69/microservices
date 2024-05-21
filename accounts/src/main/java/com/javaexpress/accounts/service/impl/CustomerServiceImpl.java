package com.javaexpress.accounts.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaexpress.accounts.dto.AccountsDto;
import com.javaexpress.accounts.dto.CardsDto;
import com.javaexpress.accounts.dto.CustomerDetailsDto;
import com.javaexpress.accounts.dto.LoansDto;
import com.javaexpress.accounts.entity.Accounts;
import com.javaexpress.accounts.entity.Customer;
import com.javaexpress.accounts.exceptions.ResourceNotFoundException;
import com.javaexpress.accounts.repository.AccountsRepository;
import com.javaexpress.accounts.repository.CustomerRepository;
import com.javaexpress.accounts.service.ICustomerService;
import com.javaexpress.accounts.service.clients.CardsFeignClient;
import com.javaexpress.accounts.service.clients.LoansFeignClient;

@Service
public class CustomerServiceImpl  implements ICustomerService{

	@Autowired
	private AccountsRepository accountsRepository;
	
	@Autowired
	private CardsFeignClient cardsFeignClient;
	
	@Autowired
	private LoansFeignClient loasFeignClient;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	
	@Override
	public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {
		// TODO Auto-generated method stub
		Customer  customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
				() -> new ResourceNotFoundException("Customer Mobile Number Not Found "+mobileNumber));
		
		Accounts accounts  = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
				() -> new ResourceNotFoundException("Account not found for  Mobile Number"+mobileNumber));
		
		CardsDto cardDetails = cardsFeignClient.fetchCardDetails(mobileNumber);
		LoansDto loanDetails = loasFeignClient.fetchLoanDetails(mobileNumber);
		
		// customer
		CustomerDetailsDto customerDetailsDto = new CustomerDetailsDto();
		BeanUtils.copyProperties(customer, customerDetailsDto);
		
		// acounts
		AccountsDto accountsDto = new AccountsDto();
		BeanUtils.copyProperties(accounts, accountsDto);
		customerDetailsDto.setAccountsDto(accountsDto);
		
		if(cardDetails != null ) {
			customerDetailsDto.setCardsDto(cardDetails);
		}
		
		if(loanDetails != null) {
			customerDetailsDto.setLoansDto(loanDetails);
		}
		return customerDetailsDto;
	}

	
}
