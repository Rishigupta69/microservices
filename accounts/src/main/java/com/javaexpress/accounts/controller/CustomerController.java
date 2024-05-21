package com.javaexpress.accounts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaexpress.accounts.dto.CustomerDetailsDto;
import com.javaexpress.accounts.service.ICustomerService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api")
@Slf4j
public class CustomerController {
	
	@Autowired
	private ICustomerService iCustomerService;

	@GetMapping("fetchCustomerDetails")
	public CustomerDetailsDto fetchCustomerDetails(@RequestParam String mobileNumber) {
		log.info("fetchCustomer Details");
		return iCustomerService.fetchCustomerDetails(mobileNumber);
	}
}
