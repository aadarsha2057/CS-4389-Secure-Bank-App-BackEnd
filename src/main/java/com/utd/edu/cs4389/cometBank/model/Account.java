package com.utd.edu.cs4389.cometBank.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account{
    private String email;
    private String accountNumber;
    private Integer routingNumber;
    private String accountType;
    private Double balance;

}
