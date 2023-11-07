package com.utd.edu.cs4389.cometBank.service;

import com.utd.edu.cs4389.cometBank.dto.LoginDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Slf4j
@Component
@Service("loginService")
public class LoginServiceImpl implements LoginService {

    private Long temp = 1L;

    // Default constructor
    public LoginServiceImpl() {
    }

    @Override
    public boolean checkUserCredentials(LoginDTO loginDTO) {
        try {
            // Open the credentials file for reading
            BufferedReader br = new BufferedReader(new FileReader("creds.txt"));

            String line;
            boolean found = false;

            // Read the file line by line and check for matching credentials
            while ((line = br.readLine()) != null) {
                String[] splitStr = line.split("\\|");
                if (splitStr[0].equals(loginDTO.getFullName()) &&
                    splitStr[1].equals(loginDTO.getPassword()) &&
                    splitStr[2].equals(loginDTO.getEmail())) {
                    found = true;
                    break;
                }
            }
            // Return true if credentials are found, otherwise return false
            return found;

        } catch (Exception e) {
            // Log the exception
            log.error("Error while checking user credentials.", e);
        }
        // Return false in case of exceptions
        return false;
    }

    @Override
    public void createUser(LoginDTO loginDTO) {
        // Create a string to append to the credentials file
        String textToAppend = loginDTO.getFullName() + "|" + loginDTO.getPassword() + "|" + loginDTO.getEmail() + "|0" + "\n";
        Path path = Paths.get("creds.txt");
        try {
            // Append the new user information to the credentials file
            Files.write(path, textToAppend.getBytes(), StandardOpenOption.APPEND);
        } catch (Exception e) {
            // Log the exception
            log.error("Error while creating a new user.", e);
        }
    }
}
