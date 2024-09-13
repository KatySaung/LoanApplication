package com.example.loanApplication.dtos;

import java.util.List;

public record CustomerDTO(
        Long customerId,
        String name,
        String email,
        int age,
        String username,
        List<String> roles
) {

}
