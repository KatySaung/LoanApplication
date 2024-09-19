package com.example.loanApplication.services;

import com.example.loanApplication.dtos.CustomerDTO;
import com.example.loanApplication.entities.Customer;

public interface CustomerService {
    Customer createCustomer(CustomerDTO customerDTO);
    CustomerDTO getCustomerById(Long id);
}
