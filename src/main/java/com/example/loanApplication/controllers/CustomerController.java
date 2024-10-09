package com.example.loanApplication.controllers;

import com.example.loanApplication.dtos.CustomerDTO;
import com.example.loanApplication.entities.Customer;
import com.example.loanApplication.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService; //test interface not actual service CustomerServiceImpl

    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO){
        Customer createdCustomer = customerService.createCustomer(customerDTO);

        //convert createdCustomer from Customer to CustomerDTO (see CustomerService interface)
        CustomerDTO createdCustomerDTO = new CustomerDTO(
                createdCustomer.getCustomerId(),
                createdCustomer.getName(),
                createdCustomer.getEmail(),
                createdCustomer.getAge(),
                createdCustomer.getUsername(),
                Collections.emptyList()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomerDTO);

    }
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id){
        CustomerDTO customerDTO = customerService.getCustomerById(id);
        return ResponseEntity.ok(customerDTO);
    }



}
