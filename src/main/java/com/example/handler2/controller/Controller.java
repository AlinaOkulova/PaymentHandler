package com.example.handler2.controller;

import com.example.handler2.model.Payment;
import com.example.handler2.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class Controller {

    private final PaymentService paymentService;

    @Autowired
    public Controller(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/payment-handler")
    public ResponseEntity<?> handlePayment(@RequestBody List<Payment> payments) {
        paymentService.changePaymentStatus(payments);

        return ResponseEntity.ok(HttpStatus.OK);
    }
}
