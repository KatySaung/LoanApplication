package com.example.loanApplication.services;


import com.example.loanApplication.dtos.CustomerDTO;
import com.example.loanApplication.entities.Customer;
import com.example.loanApplication.exceptions.CustomerNotFoundException;
import com.example.loanApplication.exceptions.DuplicateCustomerException;
import com.example.loanApplication.exceptions.MaxLengthOfNameException;
import com.example.loanApplication.exceptions.NullCustomerDTOException;
import com.example.loanApplication.repositories.CustomerRepository;
import com.example.loanApplication.services.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
            customer.setEmail("bobbyhumphrey@fakemail.com");
            customer.setAge(100);
            customer.setUsername("bhumphrey");
            customer.setRoles(Collections.emptyList());

    }

    //Success Test
    @Test
    public void testCreateCustomer(){
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
        //Arrange: set up
        String maxLength = "a".repeat(256);
        CustomerDTO customerDTO = new CustomerDTO(
                null,
                maxLength,
                "bobbyHumphrey@fakemail.com",
                100,
                "bhumphrey",
                Collections.emptyList()
        );

        // Act: assertThrows to execute behavior
        MaxLengthOfNameException thrownException = assertThrows(MaxLengthOfNameException.class, () -> {
            customerServiceImpl.createCustomer(customerDTO);
        });

        // Assert: Verify the exception message
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

        //Arrange
        when(customerRepository.existsByUsername(customerDTO.username())).thenReturn(true);

         DuplicateCustomerException duplicateCustomerException = assertThrows(DuplicateCustomerException.class, () -> {
            customerServiceImpl.createCustomer(customerDTO);
        });

        //Act and Assert
         assertEquals("Username already exists", duplicateCustomerException.getMessage());
    }


    //Test for Successful Retrieval of Customer
    @Test
    public void testGetCustomerByIdSuccess(){

        //Mock repo. Arrange happened in @BeforeEach setUp() method
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        //Act
        CustomerDTO result = customerServiceImpl.getCustomerById(customerId);

        //Assert
        assertNotNull(result);
        assertEquals(customerDTO.name(), result.name());
        assertEquals(customerDTO.email(), result.email());

    }

    //Test for Customer Not Found Scenario
    @Test
//    @DisplayName()
    public void testGetCustomerByIdNotFound(){

        //Arrange
        Long noExistingId = 0000L;
        when(customerRepository.findById(noExistingId)).thenReturn(Optional.empty());

        //Act
        CustomerNotFoundException customerNotFoundException = assertThrows(CustomerNotFoundException.class, () ->{
            customerServiceImpl.getCustomerById(noExistingId);
        });

        //Assert
        assertEquals("Customer not found with this id: " + noExistingId, customerNotFoundException.getMessage());

    }


    //Test for Updating Customer Details
    @Test
    public void testUpdateCustomerByIdSuccess(){
        //Arrange
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerDTO updatedCustomerDTO = new CustomerDTO(
                customerId,
                "Bob Humphrey",
                "bobbyHumphrey123@fakemail.com",
                21,
                "bobhumphrey_updated",
                Collections.emptyList()
        );

        //Act
        CustomerDTO result = customerServiceImpl.updateCustomerById(customerId, updatedCustomerDTO);

        //Assert
        assertNotNull(result);
        assertEquals("Bob Humphrey", result.name());
        assertEquals("bobbyHumphrey123@fakemail.com", result.email());
        assertEquals(21, result.age());
        assertEquals("bobhumphrey_updated", result.username());
        verify(customerRepository).findById(customerId);
        verify(customerRepository).save(any(Customer.class));

    }

    //Test for Deleting a Customer
    @Test
    public void testDeleteCustomerByIdSuccess(){
        //Arrange
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        //Act
        customerServiceImpl.deleteCustomerById(customerId);

        //Assert
        verify(customerRepository).findById(customerId);
        verify(customerRepository).delete(customer);

    }

    @Test
    public void testDeleteCustomerByIdNotFound(){
        //Arrange
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(CustomerNotFoundException.class, () ->{
            customerServiceImpl.deleteCustomerById(customerId);
        });


        //verify behavior
        verify(customerRepository).findById(customerId);
        verify(customerRepository, never()).delete(any(Customer.class));

    }



    //Test for Exception When Deleting Non-Existent Customer



    //Failure Test



    //Integration Test


}
