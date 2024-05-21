package com.javaexpress.loans.service.impl;


import com.javaexpress.loans.dto.LoansDto;
import com.javaexpress.loans.entity.Loans;
import com.javaexpress.loans.exception.LoanAlreadyExistsException;
import com.javaexpress.loans.exception.ResourceNotFoundException;
import com.javaexpress.loans.repository.LoansRepository;
import com.javaexpress.loans.service.ILoansService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoansServiceImpl implements ILoansService {

    private LoansRepository loansRepository;

    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loans> optionalLoans= loansRepository.findByMobileNumber(mobileNumber);
        if(optionalLoans.isPresent()){
            throw new LoanAlreadyExistsException("Loan already registered with given mobileNumber "+mobileNumber);
        }
        loansRepository.save(createNewLoan(mobileNumber));
    }

    private Loans createNewLoan(String mobileNumber) {
        Loans newLoan = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType("HOME_LOAN");
        newLoan.setTotalLoan(1000000);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(1000000);
        return newLoan;
    }


    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("L")
        );
        LoansDto loansDto = new LoansDto();
        BeanUtils.copyProperties(loans, loansDto);
        return loansDto;
    }

    @Override
    public boolean updateLoan(LoansDto loansDto) {
        Loans loans = loansRepository.findByLoanNumber(loansDto.getLoanNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loan not found with given loanNumber: "+loansDto.getLoanNumber()));
        BeanUtils.copyProperties(loansDto, loans);
        loansRepository.save(loans);
        return  true;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan not found with given mobileNumber: "+mobileNumber)
        );
        loansRepository.deleteById(loans.getLoanId());
        return true;
    }


}
