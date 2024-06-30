package com.example.CycleSharingSystemBackend.repository;


import com.example.CycleSharingSystemBackend.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Loginrepository extends JpaRepository <Login, Long > {
        Login findByUsernameAndPassword(String username, String password);
}
