package com.example.mspayment.client;

import com.example.mspayment.model.dto.RegisteredCustomerDTO;
import com.example.mspayment.model.dto.UpdateBalanceDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ms-customer", url = "http://localhost:8081/api")
public interface CustomerClient {

    @GetMapping("/customers/{id}")
    RegisteredCustomerDTO getCustomerById(@PathVariable("id") Long id);
    @PostMapping("/customers/{customerId}/flightPayment")
    void flightPaymentBalance(@PathVariable("customerId") Long customerId, @RequestBody UpdateBalanceDTO updateBalanceDTO);
}