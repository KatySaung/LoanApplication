package com.example.loanApplication.controllers;

import com.example.loanApplication.dtos.CustomerDTO;
import com.example.loanApplication.entities.Customer;
import com.example.loanApplication.services.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerController.class)
public class TestCustomerController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;  // POST AND PUT

    @MockBean
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private Long customerId;
    private CustomerDTO customerDTO;
    private Customer customer;

    @BeforeEach
    public void setUp(){

        customerDTO = new CustomerDTO(
                null, //auto-generated
                "Bobby Humphrey",  // This is the full customer name
                "bobbyhumphrey@fakemail.com",
                100,
                "bhumphrey",
                Collections.emptyList()
        );
    }

    //Success (Controller perform correct CRUD methods under normal conditions)
    @Test
    public void testCreateCustomer() throws Exception {
        Long customerId = 1L;
        Customer createdCustomer = new Customer();

        createdCustomer.setCustomerId(customerId);
        createdCustomer.setName(customerDTO.name());
        createdCustomer.setEmail(customerDTO.email());
        createdCustomer.setAge(customerDTO.age());
        createdCustomer.setUsername(customerDTO.username());
        createdCustomer.setRoles(Collections.emptyList());


        when(customerService.createCustomer(any(CustomerDTO.class))).thenReturn(createdCustomer);

        mockMvc.perform(post("/customers")
                        .with(user("username").roles("USER"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.customerId").value(customerId))
                .andExpect(jsonPath("$.name").value("Bobby Humphrey"))
                .andExpect(jsonPath("$.email").value("bobbyhumphrey@fakemail.com"))
                .andExpect(jsonPath("$.age").value(100));

        //verify()
        verify(customerService, times(1)).createCustomer(customerDTO);

    }

    //Retrieve customer
    @Test
    public void testGetCustomerById() throws Exception{
        //Arrange:
        Long customerId1 = 2L;
        CustomerDTO customerDTO = new CustomerDTO(
                customerId1,
                "Mackie",
                "Mackie@samashemail.com",
                70,
                "Mackie2404VLZ4",
                Collections.emptyList()
        );

        when(customerService.getCustomerById(customerId1)).thenReturn(customerDTO);

        //Act
        mockMvc.perform(get("/customers/{id}", customerId1)
                .with(user("username").roles("USER"))
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(customerId1))
                .andExpect(jsonPath("$.name").value(customerDTO.name()))
                .andExpect(jsonPath("$.email").value(customerDTO.email()))
                .andExpect(jsonPath("$.age").value(customerDTO.age()))
                .andExpect(jsonPath("$.username").value(customerDTO.username()));

        //Verify
        verify(customerService).getCustomerById(customerId1);

    }

    //Update Customer
//    @Test
//    public void testUpdateCustomerId(){
//        //Arrange
//        customerId = 1L;
//        CustomerDTO updatedCustomerDTO = new CustomerDTO(
//                customerId,
//                //in test use assertThat to make assertions about outcomes of test or to use doThrow() to make an exception handling here?
//
//        );
        //Act

        //Assert


//    }


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
