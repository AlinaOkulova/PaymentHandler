package com.example.paymenthandler.controller;

import com.example.paymenthandler.dto.PaymentDto;
import com.example.paymenthandler.service.PaymentHandler;
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

    private final PaymentHandler paymentHandler;

    public Controller(PaymentHandler paymentHandler) {
        this.paymentHandler = paymentHandler;
    }

    @PostMapping("/payment-handler")
    public ResponseEntity<HttpStatus> handlePayment(@RequestBody List<PaymentDto> payments) {
        paymentHandler.handlePayments(payments);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
