package com.lyrachtech.ebankingbackend.repositories;

import com.lyrachtech.ebankingbackend.entities.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {
}
