package com.utd.edu.cs4389.cometBank.service;

import com.utd.edu.cs4389.cometBank.model.Account;
import com.utd.edu.cs4389.cometBank.model.AccountInfo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class AccountInformationService {
    private AccountService accountService;
    private TransactionService transactionService;


    public List<AccountInfo> getAccountInformation(String email){
        List<Account> accountList  = accountService.loadAccountForUser(email);
        return accountList.stream().map(obj-> {
            AccountInfo accountInfo = new AccountInfo();
            accountInfo.setAccountNumber(obj.getAccountNumber());
            accountInfo.setRoutingNumber(obj.getAccountNumber());
            accountInfo.setBalance(obj.getAccountType());
            accountInfo.setTransactions(transactionService.getAllTransactions(obj.getAccountNumber()););

            return accountInfo;
        }).toList();
    }
}
