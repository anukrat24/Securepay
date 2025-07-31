package com.example.securepay.Dto;

import lombok.Data;

@Data
public class TransactionRequestDTO {
    private Double amount;
    private String description;
}
