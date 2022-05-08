package com.example.paymenthandler.service;


import com.example.paymenthandler.model.Payment;
import com.example.paymenthandler.model.PaymentStatus;
import com.example.paymenthandler.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class PaymentService {

    private final PaymentRepository repository;

    @Autowired
    public PaymentService(PaymentRepository repository) {
        this.repository = repository;
    }

    @Async
    public void changePaymentStatus(List<Payment> payments) {
        List<Payment> handledPayments = new ArrayList<>();
        for (Payment payment : payments) {
            try {
                TimeUnit.SECONDS.sleep(2);
                payment.setStatus(PaymentStatus.getRandomPaymentStatus());
                payment.setTimeStatusChange(LocalDateTime.now());
                handledPayments.add(payment);

            } catch (InterruptedException e) {
                log.error("Error in processing payment with id " + payment.getId() + " " + e.getMessage());
                log.error("More precise description of error : ", e);
            }
        }
        repository.saveAll(handledPayments);
    }

//    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.SECONDS)
//    public void sendHandledPayments() {
//        List<Payment> payments = new ArrayList<>();
//        try {
//            if(!handlePayments.isEmpty()) {
//                while (!handlePayments.isEmpty()) {
//                    payments.add(handlePayments.poll());
//                }
//                URI url = new URI("http://localhost:8080/api/handled-payments/save");
//                HttpHeaders httpHeaders = new HttpHeaders();
//                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//                HttpEntity<List<Payment>> httpEntity = new HttpEntity<>(payments, httpHeaders);
//                restTemplate.exchange(url, HttpMethod.POST, httpEntity, HttpStatus.class);
//            }
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//    }
}
