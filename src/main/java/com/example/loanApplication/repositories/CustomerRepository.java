package com.example.loanApplication.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.loanApplication.entities.Customer;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
    boolean existsByUsername(String username);
}
