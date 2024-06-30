package com.example.CycleSharingSystemBackend.dto;

import lombok.Data;

@Data
public class VerificationRequestDTO {
    private String email;
    private String otp;
}
