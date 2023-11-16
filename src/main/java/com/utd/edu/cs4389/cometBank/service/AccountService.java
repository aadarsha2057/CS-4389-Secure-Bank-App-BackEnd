package com.utd.edu.cs4389.cometBank.service;

import com.opencsv.*;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import  com.utd.edu.cs4389.cometBank.model.Account;

import java.io.FileReader;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class AccountService {
    private static final String ACCOUNT_FILE = "account.csv";
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




}
