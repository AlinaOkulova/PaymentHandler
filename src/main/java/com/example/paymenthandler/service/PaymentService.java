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
        log.info("Взял в работу оплату с id " + id);
        try {
            TimeUnit.SECONDS.sleep(2);
            status = PaymentStatus.getRandomPaymentStatus();
            log.info("Оплата с id " + id + ", статус оплаты: " + status);
        } catch (InterruptedException e) {
            log.error("Ошибка во время обработки оплаты с id " + payment.getId() + " " + e.getMessage());
            log.error("Более точное описание ошибки: ", e);
        }
        sendHandledPayments(new PaymentDto(id, status));
    }

    private void sendHandledPayments(PaymentDto payment) {
        try {
            log.info("Отправка обработанной оплаты с id " + payment.getId() + " в сервис Communal payments");
            URI uri = new URI("http://localhost:8080/api/handled-payments/saving");
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<PaymentDto> httpEntity = new HttpEntity<>(payment, httpHeaders);
            restTemplate.exchange(uri, HttpMethod.POST, httpEntity, HttpStatus.class);
        } catch (URISyntaxException e) {
            log.error("Ошибка в URI сервиса");
            log.error(e.getMessage(), e);
        }
    }
}
