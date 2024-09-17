package com.example.loanApplication.services;


import com.example.loanApplication.dtos.CustomerDTO;
import com.example.loanApplication.entities.Customer;
import com.example.loanApplication.exceptions.MaxLengthOfNameException;
import com.example.loanApplication.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
                    1L, //if customerID not set by client
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

    }


    //Success Test
    @Test
    public void testCreateCustomer() throws MaxLengthOfNameException {
        // Stub
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        // Act
        Customer createdCustomer = customerService.createCustomer(customerDTO);

         assertAll(
                 () -> assertNotNull(createdCustomer),
                 () -> assertEquals(customerId , createdCustomer.getCustomerId()),
                 () -> assertEquals("Bobby Humphrey", createdCustomer.getName()),
                 () -> assertEquals(100, createdCustomer.getAge()),
                 () -> assertEquals("bhumphrey", createdCustomer.getUsername()),
                 () -> assertTrue(createdCustomer.getRoles().isEmpty())
          );

         verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    public void createCustomerWithMaxLengthOfNameExceptionTest() {
        String maxLength = "a".repeat(256);
        CustomerDTO customerDTO = new CustomerDTO(
                1L,
                maxLength,
                "bobbyHumphrey@fakemail.com",
                100,
                "bhumphrey",
                Collections.emptyList()
        );

        // Assert
        MaxLengthOfNameException thrownException = assertThrows(MaxLengthOfNameException.class, () -> {
            customerService.createCustomer(customerDTO);
        });

        // Verify the exception message
        assertEquals("Name exceeds 255 length", thrownException.getMessage());

    }



    //Failure Test


    //Integration Test


}
