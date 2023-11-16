package com.utd.edu.cs4389.cometBank.controller;

import com.utd.edu.cs4389.cometBank.model.AccountInfo;
import com.utd.edu.cs4389.cometBank.service.AccountInformationService;
import com.utd.edu.cs4389.cometBank.service.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountController {
    private AccountService accountService;
    private AccountInformationService accountInformationService;

    public AccountController(AccountService accountService, AccountInformationService accountInformationService) {
        this.accountService = accountService;
        this.accountInformationService = accountInformationService;
    }

    @GetMapping("accountInfo")
    public List<AccountInfo> getAccountInformation(@RequestParam String email){
        return accountInformationService.getAccountInformation(email);

    }
}
