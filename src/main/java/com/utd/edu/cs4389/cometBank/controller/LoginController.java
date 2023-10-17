package com.utd.edu.cs4389.cometBank.controller;

import com.utd.edu.cs4389.cometBank.dto.LoginDTO;
import com.utd.edu.cs4389.cometBank.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/bankingApp")
public class LoginController {
    @Autowired
    private LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    public LoginController() {
    }

    @PostMapping("/login")
    public boolean loginRequest(@RequestBody LoginDTO loginDTO) {
        return loginService.checkUserCredentials(loginDTO);
    }

    @PostMapping("/create/user")
    public void createUserRequest(@RequestBody LoginDTO loginDTO) {
        loginService.createUser(loginDTO);
    }

}
