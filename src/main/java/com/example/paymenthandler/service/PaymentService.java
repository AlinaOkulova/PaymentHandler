package com.example.paymenthandler.service;


import com.example.paymenthandler.dto.PaymentDto;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class PaymentService {

    private final RestTemplate restTemplate;

    public PaymentService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Async
    public void changePaymentStatus(PaymentDto payment) {
        PaymentStatus status = PaymentStatus.NEW;
        long id = payment.getId();
            try {
                TimeUnit.SECONDS.sleep(2);
                status = PaymentStatus.getRandomPaymentStatus();
            } catch (InterruptedException e) {
                log.error("Error in processing payment with id " + payment.getId() + " " + e.getMessage());
                log.error("More precise description of error : ", e);
        }
         sendHandledPayments(new PaymentDto(id, status));
    }

    private void sendHandledPayments(PaymentDto payment) {
        try {
                URI url = new URI("http://localhost:8080/api/handled-payments/save");
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<PaymentDto> httpEntity = new HttpEntity<>(payment, httpHeaders);
                restTemplate.exchange(url, HttpMethod.POST, httpEntity, HttpStatus.class);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
