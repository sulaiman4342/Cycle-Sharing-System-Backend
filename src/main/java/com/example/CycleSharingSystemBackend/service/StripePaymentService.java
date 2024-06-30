package com.example.CycleSharingSystemBackend.service;


import com.example.CycleSharingSystemBackend.dto.CreatePaymentResponse;
import com.example.CycleSharingSystemBackend.dto.PaymentRequest;
import com.example.CycleSharingSystemBackend.repository.UserPaymentRepository;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.model.PaymentMethodCollection;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentMethodListParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;

@Service
public class StripePaymentService {

    @Value("${stripe.key.secret}")
    private String stripeApiKey;


    public CreatePaymentResponse createPaymentIntent(PaymentRequest paymentRequest) {
        Stripe.apiKey = stripeApiKey;

        try {
            CustomerCreateParams customerParams = new CustomerCreateParams
                    .Builder()
                    .setEmail(paymentRequest.getEmail())
                    .build();
            Customer customer = Customer.create(customerParams);

            PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder()
                    .setCurrency("USD")
                    .setAmount((long) (paymentRequest.getEstimatedAmount())*100)
                    .setSetupFutureUsage(PaymentIntentCreateParams.SetupFutureUsage.OFF_SESSION)
                    .setCustomer(customer.getId())
                    .addPaymentMethodType("card")
                    .setReceiptEmail(paymentRequest.getEmail())
                    .build();

            PaymentIntent intent = PaymentIntent.create(createParams);
            CreatePaymentResponse response = new CreatePaymentResponse();
            response.setClientSecret(intent.getClientSecret());


            return response;
        } catch (StripeException e) {
            // Handle Stripe exceptions
            System.out.println("Error code is : " + e.getCode());
            throw new RuntimeException(e); // Re-throw the exception as a runtime exception
        }
    }

}




