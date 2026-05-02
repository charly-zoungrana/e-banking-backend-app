package com.lyrachtech.ebankingbackend.repositories;

import com.lyrachtech.ebankingbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
