package com.javaexpress.accounts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaexpress.accounts.dto.AccountsContactInfo;
import com.javaexpress.accounts.dto.CustomerDto;
import com.javaexpress.accounts.service.IAccountsService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "api")
@Slf4j
public class AccountsController {

	@Autowired
	private IAccountsService iAccountsService;
	
	@Value("${build.version}")
	private String buildVersion;
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private AccountsContactInfo accountsContactInfo;
	
	@PostMapping(name = "/create",produces = {"application/xml","application/json"},consumes = {"application/xml","application/json"})
	public String createAccount(@RequestBody CustomerDto customerDto) {
		log.info("AccountsController :: createAccount");
		iAccountsService.createAccount(customerDto);
		return "Account created successfully";
	}
	
	@GetMapping("/fetch")
	public CustomerDto fetchAccountDetails(@RequestParam String mobileNumber) {
		return iAccountsService.fetchAccount(mobileNumber);
	}
	
	@PutMapping("/update")
	public Boolean updateAccountDetails(@RequestBody CustomerDto customerDto) {
		return iAccountsService.updateAccount(customerDto);
	}
	
	@DeleteMapping("/delete")
	public Boolean deleteAccountDetails(@RequestParam String mobileNumber) {
		return iAccountsService.deleteAccount(mobileNumber);
	}
	
	@GetMapping("build-info")
	public String getBuildVersion() {
		return buildVersion;
	}
	
	@GetMapping("java-version")
	public String getJavaVersion() {
		return environment.getProperty("JAVA_HOME");
	}
	
	@GetMapping("contact-info")
	public AccountsContactInfo getContactInfo() {
		return accountsContactInfo;
	}
	
}
