package com.lyrachtech.ebankingbackend.entities;

import com.lyrachtech.ebankingbackend.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TYPE",discriminatorType = DiscriminatorType.STRING,length=4)
@Data @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public abstract class BankAccount {
    @Id
    private String id;
    private Date createdAt;
    private Double balance;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    private String currency;
    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;
    @OneToMany(mappedBy = "bankAccount")
    private List<AccountOperation> accountOperations;
}
