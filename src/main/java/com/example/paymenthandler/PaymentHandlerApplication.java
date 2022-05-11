package com.example.paymenthandler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class PaymentHandlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentHandlerApplication.class, args);
    }
}
