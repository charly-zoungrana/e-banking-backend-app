package com.lyrachtech.ebankingbackend.dtos;

import lombok.Data;

@Data
public class DebitDTO {
    private String accountId;
    private Double amount;
    private String description;
}
