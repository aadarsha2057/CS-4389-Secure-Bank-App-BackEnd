package com.utd.edu.cs4389.cometBank.service;

import com.utd.edu.cs4389.cometBank.dto.LoginDTO;
import com.utd.edu.cs4389.cometBank.dto.SignupDTO;
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
        validateInput(loginDTO.getEmail(), loginDTO.getPassword(), null);
        try {
            // Open the credentials file for reading
            BufferedReader br = new BufferedReader(new FileReader("creds.txt"));

            String line;
            boolean found = false;
            String encryptedPw = encrypt(loginDTO.getPassword());

            // Read the file line by line and check for matching credentials
            while ((line = br.readLine()) != null) {
                String[] splitStr = line.split("\\|");
                if (splitStr[1].equals(encryptedPw) && splitStr[2].equals(loginDTO.getEmail())) {
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
    public void createUser(SignupDTO signupDTO) {
        validateInput(signupDTO.getEmail(), signupDTO.getPassword(), signupDTO.getFullName());
        // Create a string to append to the credentials file
        String encryptedPw = encrypt(signupDTO.getPassword());
        String textToAppend = signupDTO.getFullName() + "|" + encryptedPw + "|" + signupDTO.getEmail() + "|0|0" + "\n";
        Path path = Paths.get("creds.txt");
        try {
            // Append the new user information to the credentials file
            Files.write(path, textToAppend.getBytes(), StandardOpenOption.APPEND);
        } catch (Exception e) {
            // Log the exception
            log.error("Error while creating a new user.", e);
        }
    }

    private void validateInput(String email, String password, String name) {
        boolean isValid = true;
        if(email.contains("|") == true || password.contains("|") == true) { isValid = false; }
        if(name != null && name.contains("|") == true) { isValid = false; }
        if(isValid == false) {
            throw new RuntimeException("login attempt cannot contain '|'");
        }
    }

    private String encrypt(String toEncrypt) {
        StringBuilder ciphertext = new StringBuilder();

        for (char ch : toEncrypt.toCharArray()) {
            if (Character.isLetter(ch)) {
                char base = Character.isLowerCase(ch) ? 'a' : 'A';
                ciphertext.append((char) ((ch - base + 5) % 26 + base));
            } else if (Character.isDigit(ch)) {
                ciphertext.append((char) ((ch - '0' + 5) % 10 + '0'));
            } else {
                ciphertext.append(ch);
            }
        }
        return ciphertext.toString();
    }

    private String decrypt(String toDecypt) {
        StringBuilder plaintext = new StringBuilder();

        for (char ch : toDecypt.toCharArray()) {
            if (Character.isLetter(ch)) {
                char base = Character.isLowerCase(ch) ? 'a' : 'A';
                int shifted = (ch - base - 5) % 26;
                if (shifted < 0) {
                    shifted += 26;
                }
                plaintext.append((char) (shifted + base));
            } else if (Character.isDigit(ch)) {
                int shifted = (ch - '0' - 5) % 10;
                if (shifted < 0) {
                    shifted += 10;
                }
                plaintext.append((char) (shifted + '0'));
            } else {
                plaintext.append(ch);
            }
        }
        return plaintext.toString();
    }
}
