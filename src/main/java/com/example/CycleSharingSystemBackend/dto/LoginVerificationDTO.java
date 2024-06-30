package com.example.CycleSharingSystemBackend.dto;

import lombok.Data;

@Data
public class LoginVerificationDTO {
    private String email;
    private String otp;

}
