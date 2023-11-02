package com.utd.edu.cs4389.cometBank.service;

import com.utd.edu.cs4389.cometBank.dto.LoginDTO;
import com.utd.edu.cs4389.cometBank.dto.SigninDTO;
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
public class LoginServiceImpl implements LoginService{
    private Long temp = 1L;
    public LoginServiceImpl() {

    }

    @Override
    public boolean checkUserCredentials(LoginDTO loginDTO) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("creds.txt"));

            String line;
            boolean found = false;
            while((line = br.readLine()) != null) {
                String[] splitStr = line.split("\\|");
                if(splitStr[1].equals(loginDTO.getPassword()) && splitStr[2].equals(loginDTO.getEmail())) {
                    found = true;
                    break;
                }
            }
            if(found){return true;}
            else {return false;}

        } catch(Exception e) {
            System.out.println(e.getStackTrace());
        }
        return false;
    }

    @Override
    public void createUser(SigninDTO SigninDTO) {
        String textToAppend = SigninDTO.getFullName() + "|" + SigninDTO.getPassword() + "|" + SigninDTO.getEmail() + "|0" + "\n";
        Path path = Paths.get("creds.txt");
        System.out.println( "Hello world");
        try {
            Files.write(path, textToAppend.getBytes(), StandardOpenOption.APPEND);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}