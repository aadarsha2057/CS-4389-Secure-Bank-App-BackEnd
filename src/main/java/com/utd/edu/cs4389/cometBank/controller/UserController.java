package com.utd.edu.cs4389.cometBank.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.utd.edu.cs4389.cometBank.dto.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    // Endpoint to retrieve user account information
    @GetMapping("/accountInfo")
    public User getUserAccountInfo(@RequestParam String username) {
        List<User> users = readUserDataFromFile("User.txt");
        return users.stream()
                .filter(user -> user.getName().equalsIgnoreCase(username))
                .findFirst()
                .orElse(null);
    }

    // Method to read user data from the file
    private List<User> readUserDataFromFile(String fileName) {
        List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userData = line.split("\\|");
                User user = new User();
                user.setName(userData[0].trim());
                user.setAccountNumber(userData[1].trim());
                user.setRoutingNumber(userData[2].trim());
                user.setAccountBalance(Double.parseDouble(userData[3].trim()));
                users.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
}
