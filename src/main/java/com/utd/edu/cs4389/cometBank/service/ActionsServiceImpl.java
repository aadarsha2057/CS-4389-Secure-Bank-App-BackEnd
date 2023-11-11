package com.utd.edu.cs4389.cometBank.service;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@Component
@Service("actionsService")
public class ActionsServiceImpl implements ActionsService{

    @Override
    public void withdrawFromChecking(Double amt, HttpSession session) {
        String email = session.getAttribute("email").toString();
        String password = session.getAttribute("password").toString();
        String[] parts = getLine(email, password);
        String oldLine = parts.toString();

        if(amt > Double.parseDouble(parts[3]) || amt <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid amount.");
        }
        parts[3] = "" + (Double.parseDouble(parts[3]) - amt);
        updateDocument(oldLine, (parts[0] + "|" + parts[1] + "|" + parts[2] + "|" + parts[3] + "|" + parts[4]));
    }

    @Override
    public void depositToChecking(Double amt, HttpSession session) {
        String email = session.getAttribute("email").toString();
        String password = session.getAttribute("password").toString();
        String[] parts = getLine(email, password);
        String oldLine = parts.toString();

        parts[3] = "" + (Double.parseDouble(parts[3]) + amt);
        updateDocument(oldLine, (parts[0] + "|" + parts[1] + "|" + parts[2] + "|" + parts[3] + "|" + parts[4]));
    }

    @Override
    public void withdrawFromSaving(Double amt, HttpSession session) {
        String email = session.getAttribute("email").toString();
        String password = session.getAttribute("password").toString();
        String[] parts = getLine(email, password);
        String oldLine = parts.toString();

        if(amt > Double.parseDouble(parts[4]) || amt <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid amount.");
        }
        parts[4] = "" + (Double.parseDouble(parts[4]) - amt);
        updateDocument(oldLine, (parts[0] + "|" + parts[1] + "|" + parts[2] + "|" + parts[3] + "|" + parts[4]));
    }

    @Override
    public void depositToSaving(Double amt, HttpSession session) {
        String email = session.getAttribute("email").toString();
        String password = session.getAttribute("password").toString();
        String[] parts = getLine(email, password);
        String oldLine = parts.toString();

        parts[4] = "" + (Double.parseDouble(parts[4]) + amt);
        updateDocument(oldLine, (parts[0] + "|" + parts[1] + "|" + parts[2] + "|" + parts[3] + "|" + parts[4]));
    }

    @Override
    public void checkingToSavingTransfer(Double amt, HttpSession session) {
        String email = session.getAttribute("email").toString();
        String password = session.getAttribute("password").toString();
        String[] parts = getLine(email, password);
        String oldLine = parts.toString();

        if(amt > Double.parseDouble(parts[3]) || amt <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid amount.");
        }
        parts[3] = "" + (Double.parseDouble(parts[3]) - amt);
        parts[4] = "" + (Double.parseDouble(parts[4]) + amt);
        updateDocument(oldLine, (parts[0] + "|" + parts[1] + "|" + parts[2] + "|" + parts[3] + "|" + parts[4]));
    }

    @Override
    public void savingToCheckingTransfer(Double amt, HttpSession session) {
        String email = session.getAttribute("email").toString();
        String password = session.getAttribute("password").toString();
        String[] parts = getLine(email, password);
        String oldLine = parts.toString();

        if(amt > Double.parseDouble(parts[4]) || amt <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid amount.");
        }
        parts[3] = "" + (Double.parseDouble(parts[3]) + amt);
        parts[4] = "" + (Double.parseDouble(parts[4]) - amt);
        updateDocument(oldLine, (parts[0] + "|" + parts[1] + "|" + parts[2] + "|" + parts[3] + "|" + parts[4]));
    }

    private String[] getLine(String email, String password) {
        try {
            // Open the credentials file for reading
            BufferedReader br = new BufferedReader(new FileReader("creds.txt"));

            String line;
            boolean found = false;
            String encryptedPw = encrypt(password);
            String[] splitStr = null;

            // Read the file line by line and check for matching credentials
            while ((line = br.readLine()) != null) {
                splitStr = line.split("\\|");
                if (splitStr[1].equals(encryptedPw) && splitStr[2].equals(email)) {
                    found = true;
                    break;
                }
            }
            br.close();
            return splitStr;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Error opening file.");
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

    private void updateDocument(String oldLine, String newLine) {
        try {
            String file = null, master = null, line = null;
            BufferedReader br = new BufferedReader(new FileReader("creds.txt"));

            while((line = br.readLine()) != null) {
                master += line;
            }

            String processed = master.replace(oldLine, newLine);
            Files.deleteIfExists(Paths.get("creds.txt"));
            File tempFile = new File("", "creds.txt");
            FileWriter writer = new FileWriter("creds.txt");
            writer.write(master);
            writer.close();
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Error opening file.");
        }
    }
}
