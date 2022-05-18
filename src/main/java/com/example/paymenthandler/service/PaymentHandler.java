package com.example.paymenthandler.service;

import com.example.paymenthandler.dto.PaymentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentHandler {

    private final PaymentService paymentService;

    @Autowired
    public PaymentHandler(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Async
    public void handlePayments(List<PaymentDto> payments) {
        payments.forEach(paymentService::changePaymentStatus);
    }
}
