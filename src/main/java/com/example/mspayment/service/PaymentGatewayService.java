package com.example.mspayment.service;

import com.example.mspayment.model.dto.PaymentDTO;
import com.example.mspayment.model.entities.PaymentGateway;

import java.util.List;

public interface PaymentGatewayService {
    PaymentDTO createPayment(PaymentDTO paymentDTO);

    List<PaymentGateway> initiatePayment(List<Long> paymentId);

    PaymentGateway refundPayment(Long paymentId);
}
