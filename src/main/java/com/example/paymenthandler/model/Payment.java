package com.example.paymenthandler.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(schema = "communal_payments", name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "template_id")
    private Template template;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "payment_amount")
    private double amount;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(name = "time_of_creation")
    private LocalDateTime timeOfCreation;

    @Column(name = "status_change_time")
    private LocalDateTime timeStatusChange;

}
