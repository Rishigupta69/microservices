package com.javaexpress.accounts.service.impl;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaexpress.accounts.dto.AccountsDto;
import com.javaexpress.accounts.dto.CustomerDto;
import com.javaexpress.accounts.entity.Accounts;
import com.javaexpress.accounts.entity.Customer;
import com.javaexpress.accounts.exceptions.CustomerAlreadyExistsException;
import com.javaexpress.accounts.exceptions.ResourceNotFoundException;
import com.javaexpress.accounts.mapper.AccountsMapper;
import com.javaexpress.accounts.mapper.CustomerMapper;
import com.javaexpress.accounts.repository.AccountsRepository;
import com.javaexpress.accounts.repository.CustomerRepository;
import com.javaexpress.accounts.service.IAccountsService;

import lombok.AllArgsConstructor;

@Service
//@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {
	
	@Autowired
	private AccountsRepository accountsRepository;
	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public void createAccount(CustomerDto customerDto) {
		// convert customerDto to Customer Entity
		var customer  = CustomerMapper.mapToCustomer(customerDto, new Customer());
		
		// validate mobileNumber in database
		Optional<Customer> optoinalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
		if(optoinalCustomer.isPresent()) {
			throw new CustomerAlreadyExistsException("Customer already registered with given mobile Number: "+customerDto.getMobileNumber());
		}
		// save the customer information in db
		var dbCustomer = customerRepository.save(customer);
		
		// createnewAccount 
		accountsRepository.save(createNewAccount(dbCustomer));
	}

	private Accounts createNewAccount(Customer dbCustomer) {
		Accounts newAccount = new Accounts();
		newAccount.setCustomerId(dbCustomer.getCustomerId());
		newAccount.setAccountType("SAVINGS");
		newAccount.setBranchAddress("Manikonda,123 street Belgium");
		long randomAccountNumber = 10000000L + new Random().nextInt(900000000);
		newAccount.setAccountNumber(randomAccountNumber);
		return newAccount;
		
	}

	@Override
	public CustomerDto fetchAccount(String mobileNumber) {
		Customer  customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
				() -> new ResourceNotFoundException("Customer Mobile Number Not Found "+mobileNumber));
		
		Accounts accounts  = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
				() -> new ResourceNotFoundException("Account not found for  Mobile Number"+mobileNumber));
		
		CustomerDto customerDto = new CustomerDto();
	    BeanUtils.copyProperties(customer, customerDto);
	    AccountsDto accountsDto = new AccountsDto();
	    BeanUtils.copyProperties(accounts, accountsDto);
		//CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
		//customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
		customerDto.setAccountsDto(accountsDto);
		return customerDto;
	}

	@Override
	public boolean updateAccount(CustomerDto customerDto) {
		boolean isUpdated = false;
		AccountsDto accountsDto = customerDto.getAccountsDto();
		if(accountsDto != null ) {
			Accounts accounts  = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
					() -> new ResourceNotFoundException("Account not found "+accountsDto.getAccountNumber()));
			// convert accountsDto to accounts entity class
			AccountsMapper.mapToAccounts(accounts, accountsDto);
			accounts = accountsRepository.save(accounts);
			
			Long customerId = accounts.getCustomerId();
			Customer customer = customerRepository.findById(customerId).orElseThrow(
					() -> new ResourceNotFoundException("CustomerId Not Found"));
			CustomerMapper.mapToCustomer(customerDto, customer);
			customerRepository.save(customer);
			isUpdated = true;
		} else {
			throw new RuntimeException("Update Operation Failed. Please try again or contact dev team");
		}
		return isUpdated;
	}

	@Override
	public boolean deleteAccount(String mobileNumber) {
		Customer  customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
				() -> new ResourceNotFoundException("Customer Mobile Number Not Found "+mobileNumber));
		accountsRepository.deleteByCustomerId(customer.getCustomerId());
		customerRepository.deleteById(customer.getCustomerId());
		return true;
	}
	
}
