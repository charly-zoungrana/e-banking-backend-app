package com.lyrachtech.ebankingbackend.dtos;
import com.lyrachtech.ebankingbackend.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import java.util.Date;



@Data  @AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public  class SavingBankAccountDTO extends BankAccountDTO{
    private String id;
    private Date createdAt;
    private Double balance;
    private AccountStatus status;
    private String currency;
    private CustomerDTO customerDTO;
    private Double interestRate;
}
