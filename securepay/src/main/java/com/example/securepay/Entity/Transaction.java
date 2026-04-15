package com.example.securepay.Entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userEmail;
    private Double amount;
    private String description;
    private LocalDateTime timestamp;

    private boolean flagged;
    private String fraudReason;
    private String payMode;

    private String bankName;
}