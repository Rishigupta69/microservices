package com.javaexpress.accounts.service.clients;

import org.springframework.stereotype.Component;

import com.javaexpress.accounts.dto.LoansDto;

@Component
public class LoansFallBack implements LoansFeignClient {

	@Override
	public LoansDto fetchLoanDetails(String mobileNumber) {
		return null;
	}

	
}
