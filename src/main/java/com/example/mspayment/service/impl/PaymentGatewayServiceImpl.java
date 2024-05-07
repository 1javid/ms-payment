package com.example.mspayment.service.impl;

import com.example.mspayment.client.BookingClient;
import com.example.mspayment.client.CustomerClient;
import com.example.mspayment.model.dto.*;
import com.example.mspayment.model.entities.PaymentGateway;
import com.example.mspayment.repository.PaymentGatewayRepository;
import com.example.mspayment.service.PaymentGatewayService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentGatewayServiceImpl implements PaymentGatewayService {

    private final PaymentGatewayRepository paymentGatewayRepository;
    private final BookingClient bookingClient;
    private final CustomerClient customerClient;

    PaymentGatewayServiceImpl(PaymentGatewayRepository paymentGatewayRepository,
                              BookingClient bookingClient, CustomerClient customerClient) {
        this.paymentGatewayRepository = paymentGatewayRepository;
        this.bookingClient = bookingClient;
        this.customerClient = customerClient;
    }

    @Override
    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
        var payment = PaymentGateway.builder()
                        .bookingId(paymentDTO.getBookingId())
                        .paymentStatus(false)
                        .build();
        paymentGatewayRepository.save(payment);
        return paymentDTO;
    }

    @Override
    public List<PaymentGateway> initiatePayment(List<Long> bookingIds) {
        List<PaymentGateway> payments = new ArrayList<>();
        for(Long bookingId: bookingIds) {
            BookingDTO booking = bookingClient.getBookingById(bookingId);
            PaymentGateway payment = paymentGatewayRepository.findByBookingId(booking.getId());
            FlightDTO flight = bookingClient.getFlightById(booking.getFlightId());
            RegisteredCustomerDTO customer = customerClient.getCustomerById(booking.getCustomerId());

            if (flight.getAmount() == null) {
                throw new IllegalStateException("Flight cost is missing.");
            }

            float newBalance = customer.getAccountBalance() - flight.getAmount();
            if (newBalance < 0) {
                throw new IllegalStateException("Insufficient balance for the transaction.");
            }

            updateCustomerBalance(customer.getId(), flight.getAmount(), "-");

            payment.setPaymentStatus(true);

            payments.add(payment);
        }
        return paymentGatewayRepository.saveAll(payments);
    }

    @Override
    public PaymentGateway refundPayment(Long paymentId) {
        PaymentGateway payment = paymentGatewayRepository.findById(paymentId).orElseThrow(() -> new RuntimeException("Payment not found"));
        BookingDTO booking = bookingClient.getBookingById(payment.getBookingId());
        FlightDTO flight = bookingClient.getFlightById(booking.getFlightId());
        RegisteredCustomerDTO customer = customerClient.getCustomerById(booking.getCustomerId());
        payment.setPaymentStatus(false);

        updateCustomerBalance(customer.getId(),flight.getAmount() * 0.25f, "+");

        return paymentGatewayRepository.save(payment);
    }

    private void updateCustomerBalance(Long customerId, Float newBalance, String operation) {
        UpdateBalanceDTO updateBalanceDTO = new UpdateBalanceDTO(newBalance, operation);
        customerClient.flightPaymentBalance(customerId, updateBalanceDTO);
    }
}
