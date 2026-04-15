package com.example.securepay.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "routing_config")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoutingConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pay_mode_id", nullable = false)
    private Long payModeId;

    @Column(name = "bank_name", nullable = false)
    private String bankName;

    @Column(name = "traffic_percentage", nullable = false)
    private Integer trafficPercentage;
}
