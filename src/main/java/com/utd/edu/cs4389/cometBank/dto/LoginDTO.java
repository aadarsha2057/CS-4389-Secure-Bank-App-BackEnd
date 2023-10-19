package com.utd.edu.cs4389.cometBank.dto;

import lombok.Getter;
import lombok.Setter;

// A Data Transfer Object (DTO) representing user login information.
@Getter
@Setter
public class LoginDTO {
    private String fullName;
    private String password;
    private String email;

    // Constructor to initialize the login DTO with user information.
    public LoginDTO(String fullName, String password, String email) {
        this.fullName = fullName;
        this.password = password;
        this.email = email;
    }
}
