package com.example.mspayment.client;

import com.example.mspayment.model.dto.BookingDTO;
import com.example.mspayment.model.dto.FlightDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-booking", url = "http://localhost:8082/api")
public interface BookingClient {

    @GetMapping("/bookings/{id}")
    BookingDTO getBookingById(@PathVariable("id") Long id);

    @GetMapping("/flights/{id}")
    FlightDTO getFlightById(@PathVariable("id") Long id);
}

