package com.lyrachtech.ebankingbackend.dtos;

import lombok.Data;

@Data
public class CreditDTO {
    private String accountId;
    private Double amount;
    private String description;
}
