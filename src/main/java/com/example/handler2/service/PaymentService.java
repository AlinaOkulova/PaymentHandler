package com.example.handler2.service;


import com.example.handler2.model.Payment;
import com.example.handler2.model.PaymentStatus;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
public class PaymentService {

    private final RestTemplate restTemplate;

    public PaymentService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Async
    public void changePaymentStatus(List<Payment> payments) {
        List<Payment> handlePayments = new ArrayList<>();
        for (Payment payment : payments) {
            try {
                TimeUnit.SECONDS.sleep(2);
                payment.setStatus(PaymentStatus.getRandomPaymentStatus());
                payment.setTimeStatusChange(LocalDateTime.now());
                handlePayments.add(payment);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            URI url = new URI("http://localhost:8080/api/payments/save");
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<List<Payment>> httpEntity = new HttpEntity<>(handlePayments, httpHeaders);
            restTemplate.exchange(url, HttpMethod.POST, httpEntity, HttpStatus.class);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
