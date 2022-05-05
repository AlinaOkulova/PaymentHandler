package com.example.paymenthandler.model;

import java.util.Random;

public enum PaymentStatus {
    NEW,
    DONE,
    FAILED;

    private static final Random RANDOM = new Random();

    public static PaymentStatus getRandomPaymentStatus()  {
        return PaymentStatus.values()[RANDOM.nextInt(3)];
    }
}
