package com.example.securepay.Repository;

import com.example.securepay.Entity.PayModeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PayModeRepository extends JpaRepository<PayModeEntity, Long> {

    boolean existsByModeName(String modeName);

    Optional<PayModeEntity> findByModeName(String modeName);
}
