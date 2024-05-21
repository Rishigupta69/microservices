package com.javaexpress.accounts.service;

import com.javaexpress.accounts.dto.CustomerDetailsDto;

public interface ICustomerService {

	CustomerDetailsDto fetchCustomerDetails(String mobileNumber);
}
