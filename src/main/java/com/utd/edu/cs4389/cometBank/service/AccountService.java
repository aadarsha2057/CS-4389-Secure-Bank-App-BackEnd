package com.utd.edu.cs4389.cometBank.service;

import com.opencsv.*;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.utd.edu.cs4389.cometBank.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.utd.edu.cs4389.cometBank.model.Account;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AccountService {
    private static final String ACCOUNT_FILE = "accounts.csv";
    private final CSVParser parser = new CSVParserBuilder().withSeparator(',').build();


    public synchronized List<Account> loadAccountForUser(String email) {
        try (FileReader filereader = new FileReader(ACCOUNT_FILE);
             CSVReader csvReader = new CSVReaderBuilder(filereader)
                     .withCSVParser(parser)
                     .build()) {

            CsvToBean<Account> csvToBean = new CsvToBeanBuilder<Account>(csvReader)
                    .withType(Account.class)
                    .build();

            List<Account> accountList = csvToBean.parse();
            return accountList.stream().filter(account -> account.getEmail().equals(email)).toList();
        } catch (Exception e) {
            log.error("Error Reading From File {}", e.getMessage());
        }
        return Collections.emptyList();
    }

    public synchronized void updateAccountBalance(Transaction transaction) {
        List<Account> accountList = new ArrayList<>();
        try (FileReader filereader = new FileReader(ACCOUNT_FILE);
             CSVReader csvReader = new CSVReaderBuilder(filereader)
                     .withCSVParser(parser)
                     .build()) {

            CsvToBean<Account> csvToBean = new CsvToBeanBuilder<Account>(csvReader)
                    .withType(Account.class)
                    .build();
            accountList = csvToBean.parse();
        } catch (Exception e) {
            log.error("Error Reading From File {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }


        accountList.stream()
                .filter(account -> account.getAccountNumber().equals(transaction.getAccountNumber()))
                .forEach(account -> {
                    if (transaction.getTransactionType().equals("DEBIT")) {
                        if (account.getBalance() > transaction.getAmount()) {
                            account.setBalance(account.getBalance() - transaction.getAmount());
                        } else {
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Low Account balance");
                        }

                    } else if (transaction.getTransactionType().equals("CREDIT")) {
                        account.setBalance(account.getBalance() + transaction.getAmount());
                    }
                });
        //
        String[] header = {"accountNumber", "routingNumber", "accountType", "balance", "email"};
        List<String[]> updateData = accountList.stream().map(obj -> new String[]{
                obj.getAccountNumber(),
                obj.getRoutingNumber().toString(),
                obj.getAccountType(),
                obj.getBalance().toString(),
                obj.getEmail()
        }).collect(Collectors.toList());
        updateData.add(0, header);
        try (CSVWriter writer = new CSVWriter(new FileWriter(ACCOUNT_FILE))) {
            writer.writeAll(updateData);
        } catch (Exception e) {
            log.error("Error Writing To File {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }

    }

    public synchronized void addAccount(String email, String accountType) {
        String[] header = {"accountNumber", "routingNumber", "accountType", "balance", "email"};
        if (!Files.exists(Paths.get("", ACCOUNT_FILE))) {
            try (CSVWriter writer = new CSVWriter(new FileWriter(ACCOUNT_FILE))) {
                writer.writeNext(header);
            } catch (Exception e) {
                log.error("Error Writing Header To File {}", e.getMessage());
            }

        }
        List<Account> accountList = new ArrayList<>();
        try (FileReader filereader = new FileReader(ACCOUNT_FILE);
             CSVReader csvReader = new CSVReaderBuilder(filereader)
                     .withCSVParser(parser)
                     .build()) {

            CsvToBean<Account> csvToBean = new CsvToBeanBuilder<Account>(csvReader)
                    .withType(Account.class)
                    .build();
            accountList = csvToBean.parse();
        } catch (Exception e) {
            log.error("Error Reading From File {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }





        boolean isDuplicate  = true;
        while (isDuplicate){
            int accountNumber = getRandomNumber();
            Integer routingNumber = getRandomNumber();
            Account account = new Account();
            account.setAccountType(accountType);
            account.setAccountNumber(Integer.toString(accountNumber));
            account.setRoutingNumber(routingNumber);
            account.setEmail(email);
            account.setBalance(0.0);
            if(!accountList.contains(account)){
                accountList.add(account);
                isDuplicate = false;
            }
        }
        List<String[]> updateData = accountList.stream().map(obj -> new String[]{
                obj.getAccountNumber(),
                obj.getRoutingNumber().toString(),
                obj.getAccountType(),
                obj.getBalance().toString(),
                obj.getEmail()
        }).collect(Collectors.toList());
        updateData.add(0, header);
        try (CSVWriter writer = new CSVWriter(new FileWriter(ACCOUNT_FILE))) {
            writer.writeAll(updateData);
        } catch (Exception e) {
            log.error("Error Writing To File {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }
    public int getRandomNumber() {
        return (int) ((Math.random() * (999999999 - 100000000 )) + 100000000);
    }

}
