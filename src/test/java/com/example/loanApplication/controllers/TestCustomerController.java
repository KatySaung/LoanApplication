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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

        customer = new Customer();
        customer.setName(customerDTO.name());
        customer.setEmail(customerDTO.email());
        customer.setAge(customerDTO.age());
        customer.setUsername(customerDTO.username());
        customer.setRoles(Collections.emptyList());

    }

    //Success (Controller perform correct CRUD methods under normal conditions)
    @Test
    public void testCreateCustomer() throws Exception {

        //Arrange: Behavior of mock service
//        Customer savedCustomer = new Customer();
//        savedCustomer.setCustomerId(1L);
//        savedCustomer.setName(customerDTO.name());
//        savedCustomer.setEmail(customerDTO.email());
//        savedCustomer.setAge(customerDTO.age());
//        savedCustomer.setUsername(customerDTO.username());
//        savedCustomer.setRoles(customerDTO.roles());

        when(customerService.createCustomer(any(CustomerDTO.class))).thenReturn(customer);

        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO)))
                .andExpect(status().isCreated());
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.name").value("Bobby Humphrey"))
//                .andExpect(jsonPath("$.email").value("bobbyhumphrey@fakemail.com"))
//                .andExpect(jsonPath("$.age").value(100));

        //verify()
        verify(customerService, times(1)).createCustomer(any(CustomerDTO.class));

    }

    //Retrieve customer
//    @Test
//    public void testGetCustomerById(){
//        //Arrange:
//        when(customerService.getCustomerById(customerId)).thenReturn(customerDTO);
//
//        //Act
//        ResponseEntity<CustomerDTO> response = customerController.getCustomerById(customerId);
//
//        //Assert
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(customerDTO, response.getBody());
//
//    }

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
