package com.example.mspayment.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisteredCustomerDTO {
    private Long id;
    private String username;
    private String password;
    private Float accountBalance;
    private Long contactDetailsId;
}
