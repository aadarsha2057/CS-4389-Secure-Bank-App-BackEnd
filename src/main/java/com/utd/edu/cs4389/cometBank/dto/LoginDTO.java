package com.utd.edu.cs4389.cometBank.dto;

import lombok.Getter;
import lombok.Setter;

// A Data Transfer Object (DTO) representing user login information.
@Getter
@Setter
public class LoginDTO {
    private String email;
    private String password;

    // Constructor to initialize the login DTO with user information.
    public LoginDTO(String email, String password) {
        this.password = password;
        this.email = email;
    }
}
