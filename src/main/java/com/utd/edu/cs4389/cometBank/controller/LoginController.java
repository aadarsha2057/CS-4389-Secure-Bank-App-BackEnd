package com.utd.edu.cs4389.cometBank.controller;

import com.utd.edu.cs4389.cometBank.dto.LoginDTO;
import com.utd.edu.cs4389.cometBank.dto.SigninDTO;
import com.utd.edu.cs4389.cometBank.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/bankingApp")
@CrossOrigin(origins = "http://localhost:3000/")
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
    public void createUserRequest(@RequestBody SigninDTO SigninDTO) {
        loginService.createUser(SigninDTO);
    }

}
