package com.example.api.infrastructure;

import com.example.api.domain.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Transferencia entity.
 */
public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {
}
