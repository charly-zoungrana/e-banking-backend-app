package com.lyrachtech.ebankingbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountDTO {
    private String id;
    private String type;
    private Date createdAt;
    private Double balance;
    private String currency;
}
