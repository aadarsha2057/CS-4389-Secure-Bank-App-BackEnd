package com.utd.edu.cs4389.cometBank.dto;

public class User {
    private String name;
    private String accountNumber;
    private String routingNumber;
    private double accountBalance;

    // Constructors
    public User() {
    }

    public User(String name, String accountNumber, String routingNumber, double accountBalance) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.routingNumber = routingNumber;
        this.accountBalance = accountBalance;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getRoutingNumber() {
        return routingNumber;
    }

    public void setRoutingNumber(String routingNumber) {
        this.routingNumber = routingNumber;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    // Override toString method for better debugging/logging
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", routingNumber='" + routingNumber + '\'' +
                ", accountBalance=" + accountBalance +
                '}';
    }
}

