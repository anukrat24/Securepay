package com.example.securepay.Repository;

import com.example.securepay.Entity.RoutingConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoutingConfigRepository extends JpaRepository<RoutingConfig, Long> {

    List<RoutingConfig> findByPayModeId(Long payModeId);
}
