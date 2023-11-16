package com.utd.edu.cs4389.cometBank.model;


import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Account{
    private String email;
    private String accountNumber;
    private Integer routingNumber;
    private String accountType;

    private Double balance;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountNumber.equals(account.accountNumber) && routingNumber.equals(account.routingNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, routingNumber);
    }
}
