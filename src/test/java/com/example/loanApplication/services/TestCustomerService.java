package com.example.loanApplication.services;


import com.example.loanApplication.dtos.CustomerDTO;
import com.example.loanApplication.entities.Customer;
import com.example.loanApplication.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestCustomerService {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private Long customerId;
    private CustomerDTO customerDTO;
    private Customer customer;

    @BeforeEach
    public void setUp(){
        customerId = 1L;
        customerDTO = new CustomerDTO(
                null, //if customerID not set by client
                "Bobby Humphrey",
                "bobbyhumphrey@fakemail.com",
                100,
                "bhumphrey",
                Collections.emptyList()
        );
        customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setName("Bobby Humphrey");
        customer.setAge(100);
        customer.setUsername("bhumphrey");
        customer.setRoles(Collections.emptyList());

        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> {
            Customer savedCustomer = invocation.getArgument(0);
            savedCustomer.setCustomerId(customerId);
            return savedCustomer;
        });

    }

    @Test
    public void testCreateCustomer(){

        customer.setCustomerId(customerId);
        customer.setName("Bobby Humphrey");
        customer.setEmail();


    }



}
