package com.example.securepay.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String email;
    @Column(name = "password_hash")
    private String passwordHash;
    private String role;
    @Column(name = "otp_code")
    private String otpCode;
    @Column(name = "otp_expiry")
    private LocalDateTime otpExpiry;
}
