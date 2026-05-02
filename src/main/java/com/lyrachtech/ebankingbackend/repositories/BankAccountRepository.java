package com.lyrachtech.ebankingbackend.repositories;

import com.lyrachtech.ebankingbackend.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
