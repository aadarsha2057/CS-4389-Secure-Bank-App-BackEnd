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
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Slf4j
@Component
@Service("loginService")
public class LoginServiceImpl implements LoginService {

    private Long temp = 1L;
    
    // Add a secret key for encryption
    private static final String SECRET_KEY = "YourSecretKey123"; // Change this to a secure value

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
                        splitStr[2].equals(loginDTO.getEmail())) {
                    // Decrypt stored password for comparison
                    String storedPassword = decrypt(splitStr[1]);
                    if (storedPassword.equals(loginDTO.getPassword())) {
                        found = true;
                        break;
                    }
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
        String encryptedPassword = encrypt(loginDTO.getPassword());
        String textToAppend = loginDTO.getFullName() + "|" + encryptedPassword + "|" + loginDTO.getEmail() + "|0" + "\n";
        Path path = Paths.get("creds.txt");
        try {
            // Append the new user information to the credentials file
            Files.write(path, textToAppend.getBytes(), StandardOpenOption.APPEND);
        } catch (Exception e) {
            // Log the exception
            log.error("Error while creating a new user.", e);
        }
    }

    // Encryption method
    private String encrypt(String strToEncrypt) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes()));
        } catch (Exception e) {
            log.error("Error while encrypting password.", e);
        }
        return null;
    }

    // Decryption method
    private String decrypt(String strToDecrypt) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            log.error("Error while decrypting password.", e);
        }
        return null;
    }
}
