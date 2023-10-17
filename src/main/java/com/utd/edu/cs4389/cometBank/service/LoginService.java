package com.utd.edu.cs4389.cometBank.service;

import com.utd.edu.cs4389.cometBank.dto.LoginDTO;

public interface LoginService {
    boolean checkUserCredentials(LoginDTO loginDTO);

    void createUser(LoginDTO loginDTO);
}
