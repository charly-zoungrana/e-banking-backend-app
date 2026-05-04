package com.lyrachtech.ebankingbackend.services;

import com.lyrachtech.ebankingbackend.dtos.*;
import com.lyrachtech.ebankingbackend.entities.*;
import com.lyrachtech.ebankingbackend.enums.OperationType;
import com.lyrachtech.ebankingbackend.exceptions.BalanceNotSufficientException;
import com.lyrachtech.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.lyrachtech.ebankingbackend.exceptions.CustomerNotFound;
import com.lyrachtech.ebankingbackend.mappers.BankAccountMapperImpl;
import com.lyrachtech.ebankingbackend.repositories.AccountOperationRepository;
import com.lyrachtech.ebankingbackend.repositories.BankAccountRepository;
import com.lyrachtech.ebankingbackend.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final CustomerRepository customerRepository;
    private final AccountOperationRepository accountOperationRepository;
    private final BankAccountMapperImpl dtoMapper;

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("Saving a new customer");
        Customer customer=dtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return dtoMapper.fromCustomer(savedCustomer);
    }

    @Override
    public CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFound {
        Customer customer=customerRepository.findById(customerId).orElse(null);

        if(customer==null){
            throw new CustomerNotFound("Customer not found");
        }

        CurrentAccount currentAccount=new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreatedAt(new Date());
        currentAccount.setBalance(initialBalance);
        currentAccount.setOverDraft(overDraft);
        currentAccount.setCustomer(customer);
        currentAccount.setCurrency("MAD");

        CurrentAccount savedCurrentAccount = bankAccountRepository.save(currentAccount);

        return dtoMapper.fromCurrentBankAccount(savedCurrentAccount);
    }


    @Override
    public SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFound {

        Customer customer=customerRepository.findById(customerId).orElse(null);

        if(customer==null){
            throw new CustomerNotFound("Customer not found");
        }

        SavingAccount savingAccount=new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatedAt(new Date());
        savingAccount.setBalance(initialBalance);
        savingAccount.setInterestRate(interestRate);
        savingAccount.setCustomer(customer);
        savingAccount.setCurrency("MAD");
        SavingAccount savedBankAccount = bankAccountRepository.save(savingAccount);
        return dtoMapper.fromSavingBankAccount(savedBankAccount);
    }


    @Override
    public List<CustomerDTO> listCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(dtoMapper::fromCustomer)
                .toList();
    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("Bank Account not found"));
        if(bankAccount instanceof SavingAccount savingAccount){
            SavingBankAccountDTO savingBankAccountDTO=dtoMapper.fromSavingBankAccount(savingAccount);
            return savingBankAccountDTO;

        }else{
            CurrentAccount currentAccount= (CurrentAccount) bankAccount;
            CurrentBankAccountDTO currentBankAccountDTO=dtoMapper.fromCurrentBankAccount(currentAccount);
            return currentBankAccountDTO;
        }
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("Bank Account not found"));
        if(bankAccount.getBalance()<amount){
            throw new BalanceNotSufficientException("Solde insuffisant");
        }
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException{
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("Bank Account not found"));

        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {

        debit(accountIdSource,amount,"Transfer to "+accountIdDestination);
        credit(accountIdDestination,amount,"Transfer from "+accountIdSource);
    }

    @Override
    public List<BankAccountDTO> getBankAccounts(){
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        return bankAccounts.stream()
                .map(bankAccount -> {
                    if(bankAccount instanceof SavingAccount savingAccount){
                        SavingBankAccountDTO savingBankAccountDTO=dtoMapper.fromSavingBankAccount(savingAccount);
                        return savingBankAccountDTO;

                    }else{
                        CurrentAccount currentAccount= (CurrentAccount) bankAccount;
                        CurrentBankAccountDTO currentBankAccountDTO=dtoMapper.fromCurrentBankAccount(currentAccount);
                        return currentBankAccountDTO;
                    }
                })
                .toList();
    }

    @Override
    public CustomerDTO getCustomer(Long customerId) throws CustomerNotFound {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFound("Customer Not Fount"));
        return dtoMapper.fromCustomer(customer);
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        Customer customer=dtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return dtoMapper.fromCustomer(savedCustomer);
    }

    @Override
    public void deleteCustomer(Long customerId){
        customerRepository.deleteById(customerId);
    }

    @Override
    public List<AccountOperationDTO> accountHistory(String accoundId){
        List<AccountOperation> accountHistory = accountOperationRepository.findByBankAccountId(accoundId);
        return accountHistory.stream()
                .map(dtoMapper::fromAccountOperation)
                .toList();
    }

    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("Bank not found"));
        Page<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountId(accountId, PageRequest.of(page, size));
        AccountHistoryDTO accountHistoryDTO=new AccountHistoryDTO();
        List<AccountOperationDTO> historyOperations = accountOperations.getContent().stream()
                .map(dtoMapper::fromAccountOperation)
                .toList();
        accountHistoryDTO.setAccountOperationDTOS(historyOperations);
        accountHistoryDTO.setAccountId(bankAccount.getId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
        return accountHistoryDTO;
    }

    @Override
    public List<CustomerDTO> searchCustomers(String keyword) {

        List<Customer> customers=customerRepository.findByNameContains(keyword);

        return customers.stream()
                .map(dtoMapper::fromCustomer)
                .toList();
    }


}
