package com.utd.edu.cs4389.cometBank.controller;

import com.utd.edu.cs4389.cometBank.service.AccountInformationService;
import com.utd.edu.cs4389.cometBank.service.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
    private AccountService accountService;
    private AccountInformationService accountInformationService;

    public AccountController(AccountService accountService, AccountInformationService accountInformationService) {
        this.accountService = accountService;
        this.accountInformationService = accountInformationService;
    }

    @GetMapping("accountInfo")
    public void getAccountInformation(@RequestParam String email){
        accountInformationService.getAccountInformation(email);

    }
}
