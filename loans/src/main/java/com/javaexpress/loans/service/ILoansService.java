package com.javaexpress.loans.service;

import com.javaexpress.loans.dto.LoansDto;

public interface ILoansService {

    void createLoan(String mobileNumber);
    LoansDto fetchLoan(String mobileNumber);
    boolean updateLoan(LoansDto loansDto);
    boolean deleteLoan(String mobileNumber);

}
