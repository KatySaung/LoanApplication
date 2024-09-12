package com.example.loanApplication.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity

public class Customer {

    @Id
    private Long customerId;

    private String name;
    private String email;
    private String address;
    private int age;
    private String username;
    private List<String> roles;

    //No-args constructor
    public Customer(){
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    //All-args constructor
    public Customer(Long customerId, String name, String email, String address, int age, String username) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.address = address;
        this.age = age;
        this.username = username;
    }

    //Constructor with name, email, address
    public Customer(String name, String email, String address) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.age = 0;//age initialized with default value zero.
    }

    //Getters and Setters
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getRoles(){
        return roles;
    }
    public void setRoles(List<String> roles){
        this.roles = roles;
    }
}
