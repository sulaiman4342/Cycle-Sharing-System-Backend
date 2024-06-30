package com.example.CycleSharingSystemBackend.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class CreatePaymentResponse {
    private String clientSecret;
}
