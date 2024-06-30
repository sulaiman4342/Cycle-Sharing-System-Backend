package com.example.CycleSharingSystemBackend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
public class RegisterDto {
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;

}
