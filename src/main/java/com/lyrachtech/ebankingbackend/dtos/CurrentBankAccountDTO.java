package com.lyrachtech.ebankingbackend.dtos;

import com.lyrachtech.ebankingbackend.enums.AccountStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;



@Data @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public  class CurrentBankAccountDTO extends BankAccountDTO{
    private String id;
    private Date createdAt;
    private Double balance;
    private AccountStatus status;
    private String currency;
    private CustomerDTO customerDTO;
    private Double overDraft;
}
