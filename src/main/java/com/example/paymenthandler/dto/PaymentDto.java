package com.example.paymenthandler.dto;

import com.example.paymenthandler.service.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component
public class PaymentDto {

    @JsonProperty
    private long id;

    @JsonProperty
    private String cardNumber;

    @JsonProperty
    private double amount;

    @JsonProperty
    private PaymentStatus status;

    public PaymentDto(long id, PaymentStatus status) {
        this.id = id;
        this.status = status;
    }
}
