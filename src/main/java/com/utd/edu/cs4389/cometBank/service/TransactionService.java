package com.utd.edu.cs4389.cometBank.service;

import com.utd.edu.cs4389.cometBank.model.Transaction;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {
    private List<Transaction> transactions = new ArrayList<>();
    private static final String TRANSACTION_FILE = "transactions.txt";

    public TransactionService() {
        loadTransactionsFromFile();
    }

    public List<Transaction> getAllTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        saveTransactionsToFile();
    }

    private void loadTransactionsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(TRANSACTION_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String description = parts[0];
                    double amount = Double.parseDouble(parts[1]);
                    LocalDateTime timestamp = LocalDateTime.parse(parts[2]);
                    //transactions.add(new Transaction(description, amount, timestamp));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveTransactionsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TRANSACTION_FILE))) {
            for (Transaction transaction : transactions) {
                String line = String.format("%s,%.2f,%s", transaction.getDescription(), transaction.getAmount(),
                        transaction.getTimestamp());
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
