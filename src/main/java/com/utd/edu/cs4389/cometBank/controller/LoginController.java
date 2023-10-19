package com.utd.edu.cs4389.cometBank.controller;

import com.utd.edu.cs4389.cometBank.dto.LoginDTO;
import com.utd.edu.cs4389.cometBank.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/bankingApp")
public class LoginController {

    @Autowired
    private LoginService loginService;

    // Constructor for injecting LoginService
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    // Default constructor
    public LoginController() {
    }

    // Endpoint for handling login requests
    @PostMapping("/login")
    public boolean loginRequest(@RequestBody LoginDTO loginDTO) {
        // Calls the LoginService to check user credentials
        return loginService.checkUserCredentials(loginDTO);
    }

    // Endpoint for creating a new user
    @PostMapping("/create/user")
    public void createUserRequest(@RequestBody LoginDTO loginDTO) {
        // Calls the LoginService to create a new user
        loginService.createUser(loginDTO);
    }
}
