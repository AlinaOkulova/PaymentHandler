package com.example.paymenthandler.service;

import java.util.Random;

public enum PaymentStatus {
    NEW,
    DONE,
    FAILED,
    IN_PROCESS;

    private static final Random RANDOM = new Random();

    public static PaymentStatus getRandomPaymentStatus() {
        return PaymentStatus.values()[RANDOM.nextInt(3)];
    }
}
