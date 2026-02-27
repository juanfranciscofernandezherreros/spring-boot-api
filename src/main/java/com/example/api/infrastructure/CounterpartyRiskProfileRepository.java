package com.example.api.infrastructure;

import com.example.api.domain.CounterpartyRiskProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CounterpartyRiskProfileRepository extends JpaRepository<CounterpartyRiskProfile, UUID> {
}
