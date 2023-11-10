package com.utd.edu.cs4389.cometBank.controller;

import com.utd.edu.cs4389.cometBank.dto.LoginDTO;
import com.utd.edu.cs4389.cometBank.dto.SignupDTO;
import com.utd.edu.cs4389.cometBank.service.LoginService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public boolean loginRequest(@RequestBody LoginDTO loginDTO, HttpSession session) {
        // Calls the LoginService to check user credentials
        return loginService.checkUserCredentials(loginDTO, session);
    }

    // Endpoint for creating a new user
    @PostMapping("/signup")
    public ResponseEntity<?> createUserRequest(@RequestBody SignupDTO signupDTO) {
        try {
            loginService.createUser(signupDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build(); // Signifies successful creation
        } catch (RuntimeException e) {
            log.error("Signup error: ", e);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage()); // Return the error message to the client
        } catch (Exception e) {
            log.error("Internal server error: ", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred during signup");
        }
    }

}
