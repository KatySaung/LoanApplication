package com.example.loanApplication.services;


import com.example.loanApplication.dtos.CustomerDTO;
import com.example.loanApplication.entities.Customer;
import com.example.loanApplication.exceptions.DuplicateCustomerException;
import com.example.loanApplication.exceptions.MaxLengthOfNameException;
import com.example.loanApplication.exceptions.NullCustomerDTOException;
import com.example.loanApplication.repositories.CustomerRepository;
import com.example.loanApplication.services.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestCustomerService {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerServiceImpl;

    private Long customerId;
    private CustomerDTO customerDTO;
    private Customer customer;

    @BeforeEach
    public void setUp(){
            customerId = 1L;
            customerDTO = new CustomerDTO(
                    null, //auto-generated
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
        Customer createdCustomer = customerServiceImpl.createCustomer(customerDTO);

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
                null,
                maxLength,
                "bobbyHumphrey@fakemail.com",
                100,
                "bhumphrey",
                Collections.emptyList()
        );

        // Assert
        MaxLengthOfNameException thrownException = assertThrows(MaxLengthOfNameException.class, () -> {
            customerServiceImpl.createCustomer(customerDTO);
        });

        // Verify the exception message
        assertEquals("Name cannot exceed 255 in length", thrownException.getMessage());

    }

    @Test
    public void createCustomerWithNullCustomerDTOExceptionTest(){
        assertThrows(NullCustomerDTOException.class, () -> {
            customerServiceImpl.createCustomer(null);
        });
    }

    //Test for Handling Duplicate Customer usernames
    @Test
    public void createCustomerWithDuplicateUsernameTest(){
        when(customerRepository.existsByUsername(customerDTO.username())).thenReturn(true);

         DuplicateCustomerException duplicateCustomerException = assertThrows(DuplicateCustomerException.class, () -> {
            customerServiceImpl.createCustomer(customerDTO);
        });

         assertEquals("Username already exists", duplicateCustomerException.getMessage());
    }



    //Test for Successful Retrieval of Customer


    //Test for Customer Not Found Scenario


    //Test for Updating Customer Details



    //Test for Deleting a Customer



    //Test for Exception When Deleting Non-Existent Customer



    //Failure Test



    //Integration Test


}
