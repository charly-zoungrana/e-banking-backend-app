package com.lyrachtech.ebankingbackend.dtos;

import lombok.Data;

import java.util.List;

@Data
public class CustomerBankAccountsDTO {
    private CustomerDTO customerDTO;
    private List<BankAccountDTO> bankAccountDTOS;
}
