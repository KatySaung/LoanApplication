package com.example.loanApplication.services;

import com.example.loanApplication.dtos.CustomerDTO;
import com.example.loanApplication.entities.Customer;
import com.example.loanApplication.exceptions.MaxLengthOfNameException;
import com.example.loanApplication.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private static final int maxNameLength = 255;

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(CustomerDTO customerDTO) {

        if(customerDTO.name().length() > maxNameLength){
            throw new MaxLengthOfNameException("Name exceeds 255 length");
        }

        Customer customer = new Customer();
        customer.setName(customerDTO.name());
        customer.setEmail(customerDTO.email());
        customer.setAge(customerDTO.age());
        customer.setUsername(customerDTO.username());
        customer.setRoles(customerDTO.roles());

        return customerRepository.save(customer);

    }


}
