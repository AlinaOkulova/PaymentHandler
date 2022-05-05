package com.example.paymenthandler.service;


import com.example.paymenthandler.model.Payment;
import com.example.paymenthandler.model.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;

import org.springframework.core.task.TaskExecutor;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;


@Service
public class PaymentService {

    private final RestTemplate restTemplate;
    private final TaskExecutor taskExecutor;
    private final ConcurrentLinkedQueue<Payment> handlePayments;

    @Autowired
    public PaymentService(RestTemplateBuilder restTemplateBuilder,
                          @Qualifier("taskExecutor") TaskExecutor taskExecutor) {
        this.restTemplate = restTemplateBuilder.build();
        this.taskExecutor = taskExecutor;
        this.handlePayments = new ConcurrentLinkedQueue<>();
    }

    @Async
    public void changePaymentStatus(List<Payment> payments) {
        for (Payment payment : payments) {
            taskExecutor.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    payment.setStatus(PaymentStatus.getRandomPaymentStatus());
                    payment.setTimeStatusChange(LocalDateTime.now());
                    handlePayments.add(payment);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.SECONDS)
    public void sendHandledPayments() {
        List<Payment> payments = new ArrayList<>();
        try {
            if(!handlePayments.isEmpty()) {
                while (!handlePayments.isEmpty()) {
                    payments.add(handlePayments.poll());
                }
                URI url = new URI("http://localhost:8080/api/handled-payments/save");
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<List<Payment>> httpEntity = new HttpEntity<>(payments, httpHeaders);
                restTemplate.exchange(url, HttpMethod.POST, httpEntity, HttpStatus.class);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
