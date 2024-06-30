package com.example.CycleSharingSystemBackend.service;

import com.example.CycleSharingSystemBackend.dto.CreatePaymentResponse;
import com.example.CycleSharingSystemBackend.dto.CreateUserPaymentDTO;
import com.example.CycleSharingSystemBackend.dto.PaymentRequest;
import com.example.CycleSharingSystemBackend.model.UserPayment;

import java.time.LocalDate;

public interface UserPaymentService {
    Double getTotalEstimatedAmountForDate(LocalDate paymentDate);
    CreatePaymentResponse createPaymentIntent(PaymentRequest paymentRequest);
    public double getestimatedAmount(Long id );
    Double getDailySum(LocalDate date);
    Double getMonthlySum(int year, int month);
    UserPayment createUserPayment(CreateUserPaymentDTO createUserPaymentDTO);

}