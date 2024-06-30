package com.example.CycleSharingSystemBackend.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class OtpUtil {

    public String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(899999);
        return String.valueOf(otp);
    }
}
