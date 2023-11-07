package com.utd.edu.cs4389.cometBank.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Transaction {
    private String description;
    private double amount;
    private LocalDateTime timestamp;

    // Constructors, getters, and setters
}
