package com.lyrachtech.ebankingbackend;

import com.lyrachtech.ebankingbackend.entities.AccountOperation;
import com.lyrachtech.ebankingbackend.entities.CurrentAccount;
import com.lyrachtech.ebankingbackend.entities.Customer;
import com.lyrachtech.ebankingbackend.entities.SavingAccount;
import com.lyrachtech.ebankingbackend.enums.AccountStatus;
import com.lyrachtech.ebankingbackend.enums.OperationType;
import com.lyrachtech.ebankingbackend.repositories.AccountOperationRepository;
import com.lyrachtech.ebankingbackend.repositories.BankAccountRepository;
import com.lyrachtech.ebankingbackend.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EBankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EBankingBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository){

        return args -> {
            Stream.of("Hassan","Marcel","Brian","Charly").forEach(name->{
                Customer customer=Customer.builder()
                        .name(name)
                        .email(name+"@gmail.com")
                        .build();
                customerRepository.save(customer);
            });

            customerRepository.findAll().forEach(customer -> {

                CurrentAccount currentAccount=CurrentAccount.builder()
                        .id(UUID.randomUUID().toString())
                        .balance(Math.random()*10000)
                        .overDraft(2000.0)
                        .customer(customer)
                        .currency("MAD")
                        .status(AccountStatus.CREATED)
                        .createdAt(new Date())
                        .build();

                bankAccountRepository.save(currentAccount);

                SavingAccount savingAccount=SavingAccount.builder()
                        .id(UUID.randomUUID().toString())
                        .balance(Math.random()*10000)
                        .interestRate(5.5)
                        .customer(customer)
                        .currency("MAD")
                        .status(AccountStatus.CREATED)
                        .createdAt(new Date())
                        .build();

                bankAccountRepository.save(savingAccount);
            });

            bankAccountRepository.findAll().forEach(bankAccount -> {
                for(int i=0;i<10;i++){
                    AccountOperation accountOperation=AccountOperation.builder()
                            .amount(Math.random()*500)
                            .operationDate(new Date())
                            .type(Math.random()>0.5? OperationType.DEBIT:OperationType.CREDIT)
                            .bankAccount(bankAccount)
                            .build();
                    accountOperationRepository.save(accountOperation);
                }
            });
        };
    }

}
