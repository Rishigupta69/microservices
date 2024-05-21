package com.javaexpress.accounts.service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaexpress.accounts.dto.LoansDto;


@FeignClient(name="LOANS",fallback = LoansFallBack.class)
public interface LoansFeignClient {

	@GetMapping("api/fetch")
	public LoansDto fetchLoanDetails(@RequestParam String mobileNumber);
}
