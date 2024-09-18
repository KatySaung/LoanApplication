package com.example.loanApplication.exceptions;

public class DuplicateCustomerException extends RuntimeException{
    public DuplicateCustomerException(String message){
        super(message);
    }
}
