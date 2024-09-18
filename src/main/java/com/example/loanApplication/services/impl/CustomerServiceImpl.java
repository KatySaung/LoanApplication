package com.example.loanApplication.services.impl;

import com.example.loanApplication.dtos.CustomerDTO;
import com.example.loanApplication.entities.Customer;
import com.example.loanApplication.exceptions.MaxLengthOfNameException;
import com.example.loanApplication.repositories.CustomerRepository;
import com.example.loanApplication.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    private static final int maxNameLength = 255;

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer createCustomer(CustomerDTO customerDTO) {
        if(customerDTO.name().length() > maxNameLength){
            throw new MaxLengthOfNameException("Name cannot exceed 255 in length");
        }

        Customer customer = new Customer();
        customer.setName(customerDTO.name());
        customer.setEmail(customerDTO.email());
        customer.setAge(customerDTO.age());
        customer.setUsername(customerDTO.username());

        return customerRepository.save(customer);

    }


}
