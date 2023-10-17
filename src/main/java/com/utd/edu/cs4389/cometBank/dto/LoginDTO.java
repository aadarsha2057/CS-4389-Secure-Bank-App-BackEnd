package com.utd.edu.cs4389.cometBank.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    private String fullName;
    private String password;
    private String email;
    public LoginDTO(String fullName, String password, String email) {
        this.fullName = fullName;
        this.password = password;
        this.email = email;
    }
}