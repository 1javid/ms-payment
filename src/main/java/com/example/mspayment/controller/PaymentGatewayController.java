package com.example.mspayment.controller;

import com.example.mspayment.model.dto.PaymentDTO;
import com.example.mspayment.model.entities.PaymentGateway;
import com.example.mspayment.service.PaymentGatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PaymentGatewayController {

    @Autowired
    private PaymentGatewayService paymentGatewayService;

    @PostMapping("/create")
    public PaymentDTO createPayment(@RequestBody PaymentDTO paymentDTO) {
        // Logic to create and save the Payment
        return paymentGatewayService.createPayment(paymentDTO);
    }

    @PostMapping("/initiate/{paymentId}")
    public ResponseEntity<PaymentGateway> initiatePayment(@PathVariable Long paymentId) {
        try {
            PaymentGateway payment = paymentGatewayService.initiatePayment(paymentId);
            return ResponseEntity.ok(payment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/refund/{paymentId}")
    public ResponseEntity<PaymentGateway> refundPayment(@PathVariable Long paymentId) {
        try {
            PaymentGateway payment = paymentGatewayService.refundPayment(paymentId);
            return ResponseEntity.ok(payment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}