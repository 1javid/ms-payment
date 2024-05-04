package com.example.mspayment.repository;

import com.example.mspayment.model.entities.PaymentGateway;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentGatewayRepository extends JpaRepository<PaymentGateway, Long> {
}