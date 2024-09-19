package com.example.loanApplication.services.impl;

import com.example.loanApplication.dtos.CustomerDTO;
import com.example.loanApplication.entities.Customer;
import com.example.loanApplication.exceptions.CustomerNotFoundException;
import com.example.loanApplication.exceptions.DuplicateCustomerException;
import com.example.loanApplication.exceptions.MaxLengthOfNameException;
import com.example.loanApplication.exceptions.NullCustomerDTOException;
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
        if(customerDTO == null){
            throw new NullCustomerDTOException("CustomerDTO requires valid input, cannot be null");
        }

        if(customerDTO.name().length() > maxNameLength){
            throw new MaxLengthOfNameException("Name cannot exceed 255 in length");
        }

        if(customerRepository.existsByUsername(customerDTO.username())){
            throw new DuplicateCustomerException("Username already exists");
        }

        Customer customer = new Customer();
        customer.setName(customerDTO.name());
        customer.setEmail(customerDTO.email());
        customer.setAge(customerDTO.age());
        customer.setUsername(customerDTO.username());

        return customerRepository.save(customer);

    }

    @Override
    public CustomerDTO getCustomerById(Long id){
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with this id: " + id));
        return convertToDTO(customer);
    }

    private CustomerDTO convertToDTO(Customer customer){
        return new CustomerDTO(customer.getCustomerId(), customer.getName(),customer.getEmail(),customer.getAge(),customer.getUsername(), customer.getRoles());
    }


}
