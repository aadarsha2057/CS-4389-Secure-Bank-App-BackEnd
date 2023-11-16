package com.utd.edu.cs4389.cometBank.model;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
public class Transaction {
    @CsvBindByName
    private String transactionId;

    @CsvBindByName
    private String accountNumber;
    @CsvBindByName
    private Double amount;
    @CsvBindByName
    private String timeStamp;

    @CsvBindByName
    private String transactionType;

    @CsvBindByName
    private String description;

    // Constructors, getters, and setters
}
