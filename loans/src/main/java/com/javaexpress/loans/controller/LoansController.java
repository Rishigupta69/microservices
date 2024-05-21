package com.javaexpress.loans.controller;


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

import com.javaexpress.loans.dto.LoansContactInfo;
import com.javaexpress.loans.dto.LoansDto;
import com.javaexpress.loans.service.ILoansService;

import lombok.extern.slf4j.Slf4j;



@RestController
@RequestMapping(path = "api")
@Slf4j
public class LoansController {

    @Autowired
    private ILoansService iLoansService;
    
    @Value("${build.version}")
	private String buildVersion;
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private LoansContactInfo loansContactInfo;

    @PostMapping("/create")
    public String createLoan(@RequestParam String mobileNumber) {
        iLoansService.createLoan(mobileNumber);
        return "Loan created successfully";
    }

    @GetMapping("/fetch")
    public LoansDto fetchLoanDetails(@RequestParam String mobileNumber) {
    	log.info("fetchLoanDetails");
        return iLoansService.fetchLoan(mobileNumber);
    }

    @PutMapping("/update")
    public Boolean updateLoanDetails(@RequestBody LoansDto loansDto) {
        return iLoansService.updateLoan(loansDto);
    }

    @DeleteMapping("/delete")
    public Boolean deleteLoanDetails(@RequestParam String mobileNumber) {
        return iLoansService.deleteLoan(mobileNumber);
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
	public LoansContactInfo getContactInfo() {
		return loansContactInfo;
	}
}
