package com.lyrachtech.ebankingbackend.services;

import com.lyrachtech.ebankingbackend.dtos.*;


import com.lyrachtech.ebankingbackend.exceptions.BalanceNotSufficientException;
import com.lyrachtech.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.lyrachtech.ebankingbackend.exceptions.CustomerNotFound;
import com.lyrachtech.ebankingbackend.exceptions.UnAuthorizedAccountOperation;

import java.util.List;

public interface BankAccountService {

    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFound;
    SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFound;
    List<CustomerDTO> listCustomers();
    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId,double amount,String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId,double amount,String description) throws BankAccountNotFoundException;
    void transfer(String accountIdSource,String accountIdDestination,double amount) throws BankAccountNotFoundException, BalanceNotSufficientException, UnAuthorizedAccountOperation;

    List<BankAccountDTO> getBankAccounts();

    CustomerDTO getCustomer(Long customerId) throws CustomerNotFound;

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long customerId);

    List<AccountOperationDTO> accountHistory(String accoundId);

    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;

    List<CustomerDTO> searchCustomers(String keyword);

    CustomerBankAccountsDTO getCustomerBankAccounts(Long customerId);

}