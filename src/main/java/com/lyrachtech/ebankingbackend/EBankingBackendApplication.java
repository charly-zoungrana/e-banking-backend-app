package com.lyrachtech.ebankingbackend;

import com.lyrachtech.ebankingbackend.dtos.BankAccountDTO;
import com.lyrachtech.ebankingbackend.dtos.CurrentBankAccountDTO;
import com.lyrachtech.ebankingbackend.dtos.CustomerDTO;
import com.lyrachtech.ebankingbackend.dtos.SavingBankAccountDTO;
import com.lyrachtech.ebankingbackend.exceptions.CustomerNotFound;
import com.lyrachtech.ebankingbackend.mappers.BankAccountMapperImpl;
import com.lyrachtech.ebankingbackend.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class EBankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EBankingBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService,
                                        BankAccountMapperImpl dtoMapper) {

        return args -> {
            Stream.of("Phanuel","Lucas","Laurene").forEach(name->{
                CustomerDTO customer=new CustomerDTO();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                bankAccountService.saveCustomer(customer);
            });

            bankAccountService.listCustomers().forEach(customer -> {
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random()*9000,9000, customer.getId());
                    bankAccountService.saveSavingBankAccount(Math.random()*9000,5.5,customer.getId());

                } catch (CustomerNotFound e) {
                    e.printStackTrace();
                }
            });
            List<BankAccountDTO> bankAccounts = bankAccountService.getBankAccounts();
            for(BankAccountDTO bankAccount:bankAccounts){
                for(int i=0;i<10;i++){
                    String accountId;
                    if(bankAccount instanceof SavingBankAccountDTO savingBankAccountDTO){
                        accountId=savingBankAccountDTO.getId();
                    }else{
                        accountId=((CurrentBankAccountDTO)bankAccount).getId();
                    }
                    bankAccountService.credit(accountId,100000+Math.random()*120000,"Credit");
                    bankAccountService.debit(accountId,1000+Math.random()*9000,"Debit");
                }

            }
        };

    }

    //@Bean
    CommandLineRunner start(){
      return args -> {

      } ;
    }

}
