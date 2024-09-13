package com.example.loanApplication.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.loanApplication.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
}
