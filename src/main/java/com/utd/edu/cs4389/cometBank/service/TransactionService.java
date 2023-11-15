package com.utd.edu.cs4389.cometBank.service;

import com.opencsv.*;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.utd.edu.cs4389.cometBank.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class TransactionService {
    private static final String TRANSACTION_FILE = "transaction.csv";
    private final CSVParser parser = new CSVParserBuilder().withSeparator(',').build();
    public TransactionService() {

    }

    public List<Transaction> getAllTransactions(String accountNumber) {
        return loadTransactionsFromFile().stream().filter(transaction -> accountNumber.equals(transaction.getAccountNumber())).toList();
    }

    private synchronized List<Transaction> loadTransactionsFromFile() {

        try (FileReader filereader = new FileReader(TRANSACTION_FILE);
                CSVReader csvReader = new CSVReaderBuilder(filereader)
                        .withCSVParser(parser)
                        .build()) {

            CsvToBean<Transaction> csvToBean = new CsvToBeanBuilder<Transaction>(csvReader)
                    .withType(Transaction.class)
                    .build();

            return csvToBean.parse();
        } catch (Exception e) {
            log.error("Error Reading From File {}", e.getMessage());
        }
        return Collections.emptyList();
    }

    public synchronized void addTransaction(Transaction transaction) {
        String[] header = {"transactionId", "accountNumber", "amount","description","timeStamp"};
        if(!Files.exists(Paths.get("",TRANSACTION_FILE))) {
            try(CSVWriter writer = new CSVWriter(new FileWriter(TRANSACTION_FILE))) {
                writer.writeNext(header);
            }catch(Exception e){
                log.error("Error Writing Header To File {}", e.getMessage());
            }

        }
        try (CSVWriter writer = new CSVWriter(new FileWriter(TRANSACTION_FILE,true))) {
            String[] dataToAppend = {
                    UUID.randomUUID().toString(),
                    transaction.getAccountNumber(),
                    String.valueOf(transaction.getAmount()),
                    transaction.getDescription(),
                    Instant.now().toString()
            };
            writer.writeNext(dataToAppend);

        } catch (Exception e) {
            log.error("Error Writing To File {}", e.getMessage());
        }
    }


}