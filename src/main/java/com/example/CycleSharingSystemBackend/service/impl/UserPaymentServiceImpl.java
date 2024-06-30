package com.example.CycleSharingSystemBackend.service.impl;

import com.example.CycleSharingSystemBackend.dto.CreatePaymentResponse;
import com.example.CycleSharingSystemBackend.dto.CreateUserPaymentDTO;
import com.example.CycleSharingSystemBackend.dto.PaymentRequest;
import com.example.CycleSharingSystemBackend.model.Ride;
import com.example.CycleSharingSystemBackend.model.UserPayment;
import com.example.CycleSharingSystemBackend.repository.UserPaymentRepository;
import com.example.CycleSharingSystemBackend.service.UserPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

@Service
public class UserPaymentServiceImpl implements UserPaymentService {

    private static final Logger logger = LoggerFactory.getLogger(UserPaymentServiceImpl.class);

    private final UserPaymentRepository userPaymentRepository;

    @Autowired
    public UserPaymentServiceImpl(UserPaymentRepository userPaymentRepository) {
        this.userPaymentRepository = userPaymentRepository;
    }

    @Override
    public Double getTotalEstimatedAmountForDate(LocalDate paymentDate) {
        logger.info("Fetching total estimated amount for date: {}", paymentDate);
        Double totalAmount = userPaymentRepository.getTotalEstimatedAmountForDate(paymentDate);
        logger.info("Total estimated amount for date {}: {}", paymentDate, totalAmount);
        return totalAmount;
    }

    @Override
    public CreatePaymentResponse createPaymentIntent(PaymentRequest paymentRequest) {
        return null;
    }

    @Override
    public double getestimatedAmount(Long id) {
        UserPayment userPayment= userPaymentRepository.findById(id).get();
        return userPayment.getEstimatedAmount();
    }
    @Override
    public Double getDailySum(LocalDate date) {
        return userPaymentRepository.findDailySum(date);
    }

    @Override
    public Double getMonthlySum(int year, int month) {
        return userPaymentRepository.findMonthlySum(year, month);
    }
    @Override
    public UserPayment createUserPayment(CreateUserPaymentDTO createUserPaymentDTO) {
        UserPayment userPayment = new UserPayment();
        userPayment.setPaymentDate(createUserPaymentDTO.getPaymentDate());
        userPayment.setEstimatedAmount(createUserPaymentDTO.getEstimatedAmount());
        return userPaymentRepository.save(userPayment);
    }
}
