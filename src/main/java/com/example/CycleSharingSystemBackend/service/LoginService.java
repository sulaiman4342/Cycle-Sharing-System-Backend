package com.example.CycleSharingSystemBackend.service;

import com.example.CycleSharingSystemBackend.model.Login;
import com.example.CycleSharingSystemBackend.repository.Loginrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private Loginrepository repo;

    public Login login(String username, String password){
        Login user = repo.findByUsernameAndPassword(username,password);
        System.out.println(username+password);

        return user;
    }
}
