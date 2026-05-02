package com.lyrachtech.ebankingbackend.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("CA")
@Data @EqualsAndHashCode(callSuper = true)
@NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class CurrentAccount extends BankAccount{
    private Double overDraft;
}
