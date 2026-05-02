package com.lyrachtech.ebankingbackend.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("SA")
@Data @EqualsAndHashCode(callSuper = true)
@NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class SavingAccount extends BankAccount{
    private Double interestRate;
}
