package com.example.CycleSharingSystemBackend.dto;

import lombok.*;

@Getter
//@Data
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
public class PaymentRequest {
    private String id;
    private double estimatedAmount;
    private String email;
//    private Long userId;
}
