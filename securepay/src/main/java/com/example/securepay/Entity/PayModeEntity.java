package com.example.securepay.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pay_modes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PayModeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mode_name", unique = true, nullable = false)
    private String modeName;
}
