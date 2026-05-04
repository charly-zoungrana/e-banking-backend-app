package com.lyrachtech.ebankingbackend.dtos;

import com.lyrachtech.ebankingbackend.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data @NoArgsConstructor @AllArgsConstructor
public class AccountOperationDTO {

    private Long id;
    private Date operationDate;
    private Double amount;
    private OperationType type;
    private String description;
}
