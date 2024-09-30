package com.example.loanApplication.controllers;

import com.example.loanApplication.dtos.CustomerDTO;
import com.example.loanApplication.entities.Customer;
import com.example.loanApplication.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestCustomerController {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private Long customerId;
    private CustomerDTO customerDTO;

    @BeforeEach
    public void setUp(){

        customerDTO = new CustomerDTO(
                null, //auto-generated
                "Bobby Humphrey",
                "bobbyhumphrey@fakemail.com",
                100,
                "bhumphrey",
                Collections.emptyList()
        );
    }

    //Helper method to convert customer to customerDTO to reduce boilerplate code in test create Customer method
    private Customer createCustomerEntity(){
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setName(customerDTO.name());
        customer.setEmail(customerDTO.email());
        customer.setAge(customerDTO.age());
        customer.setUsername(customerDTO.username());
        customer.setRoles(Collections.emptyList());
        return customer;

    }

    //Success (Controller perform correct CRUD methods under normal conditions)
    @Test
    public void testCreateCustomer(){
        Customer createdCustomer= createCustomerEntity();

        //Arrange: Behavior of mock service
        when(customerService.createCustomer(any(CustomerDTO.class))).thenReturn(createdCustomer);

        //Act: Call controller method
        ResponseEntity<CustomerDTO> response = customerController.createCustomer(customerDTO);

        //Assert: Check response status
        assertEquals(HttpStatus.OK, response.getStatusCode());

        //Assert: make sure the response body is not null
        CustomerDTO responseBody = response.getBody(); //make sure response body is not null
        assertNotNull(responseBody, "Response body cannot be null");

        //Assert: Check response body
        assertEquals(customerDTO.name(), response.getBody().name());

    }

    //Retrieve customer
    @Test
    public void testGetCustomerById(){
        //Arrange:
        when(customerService.getCustomerById(customerId)).thenReturn(customerDTO);

        //Act
        ResponseEntity<CustomerDTO> response = customerController.getCustomerById(customerId);

        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customerDTO, response.getBody());

    }

    //Update Customer
    @Test
    public void testUpdateCustomerId(){
        //Arrange
        customerId = 1L;
        CustomerDTO updatedCustomerDTO = new CustomerDTO(
                customerId,

        );
        //Act

        //Assert


    }


    //Delete Customer



    //Failure (Controller to handle errors properly)
    //Create customer with invalid data
    //Retrieve Customer not found
    //Update Non-Existent Customer
    //Delete Non-Existent Customer


    //Edge cases
    //Create Customer with null input
    //Retrieve Customer with Invalid ID
    //Update Customer with Null Data
    //Handling Empty Request Bodies



}
