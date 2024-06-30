package com.example.CycleSharingSystemBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserPaymentDTO {
    private LocalDate paymentDate;
    private Double estimatedAmount;
    private Long userId;
}