package com.utd.edu.cs4389.cometBank.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class AccountInfo {
    private String name;
    private String accountNumber;
    private String accountType;
    private String routingNumber;
    private String balance;
    private List<Transaction> transactions;
}
