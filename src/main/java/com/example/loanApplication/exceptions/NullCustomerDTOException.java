package com.example.loanApplication.exceptions;

public class NullCustomerDTOException extends RuntimeException {
    public NullCustomerDTOException(String message){
        super(message);
    }
}
