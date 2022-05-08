package com.example.paymenthandler.repository;

import com.example.paymenthandler.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
