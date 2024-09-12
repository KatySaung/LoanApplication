package com.example.loanApplication.dtos;

public record CustomerDTO(
        Long customerId,
        String name,
        String email,
        int age,
        String username
) {

}
