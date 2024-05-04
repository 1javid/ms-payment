package com.example.mspayment.service;

import com.example.mspayment.model.dto.PaymentDTO;
import com.example.mspayment.model.entities.PaymentGateway;

public interface PaymentGatewayService {
    PaymentDTO createPayment(PaymentDTO paymentDTO);

    PaymentGateway initiatePayment(Long paymentId);

    PaymentGateway refundPayment(Long paymentId);
}
