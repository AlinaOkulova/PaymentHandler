package com.example.paymenthandler.service;


import com.example.paymenthandler.dto.PaymentDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class PaymentService {

    private final RestTemplate restTemplate;
    private final String url;

    public PaymentService(@Value("${rest.service.COMMUNAL_PAYMENTS_API.url}") String url,
                          RestTemplateBuilder builder) {
        this.url = url;
        this.restTemplate = builder.build();
    }

    @Async
    public void changePaymentStatus(PaymentDto payment) {
        PaymentStatus status = PaymentStatus.NEW;
        long id = payment.getId();
        log.info("Взял в работу оплату id: " + id);
        try {
            TimeUnit.SECONDS.sleep(2);
            status = PaymentStatus.getRandomPaymentStatus();
            log.info("Оплата id: " + id + ", статус оплаты: " + status);
        } catch (InterruptedException e) {
            log.error("Ошибка во время обработки оплаты id: " + payment.getId() + " " + e.getMessage());
            log.error("Более точное описание ошибки: ", e);
        }
        sendHandledPayments(new PaymentDto(id, status));
    }

    private void sendHandledPayments(PaymentDto payment) {
        log.info("Отправка обработанной оплаты id: " + payment.getId() + " в сервис Communal payments");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PaymentDto> httpEntity = new HttpEntity<>(payment, httpHeaders);
        restTemplate.exchange(url, HttpMethod.POST, httpEntity, HttpStatus.class);
    }
}
