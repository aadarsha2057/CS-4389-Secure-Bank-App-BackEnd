package com.utd.edu.cs4389.cometBank.controller;

import com.utd.edu.cs4389.cometBank.model.Transaction;
import com.utd.edu.cs4389.cometBank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("getAllTransactions")
    public List<Transaction> getAllTransactions(@RequestParam String accountNumber) {
        return transactionService.getAllTransactions(accountNumber);
    }

    @PostMapping("addTransaction")
    public void getAllTransactions(@RequestBody Transaction transaction) {
         transactionService.addTransaction(transaction);
    }
}
