package com.utd.edu.cs4389.cometBank.service;

import com.utd.edu.cs4389.cometBank.dto.LoginDTO;
import com.utd.edu.cs4389.cometBank.dto.SigninDTO;

// This interface defines methods for user authentication and user creation.
public interface LoginService {
    
    // Check the user's credentials for login.
    boolean checkUserCredentials(LoginDTO loginDTO);

    // Create a new user using the provided LoginDTO.
    void createUser(SigninDTO SigninDTO);

}
