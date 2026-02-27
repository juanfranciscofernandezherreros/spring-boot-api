package com.example.api.dto;

import com.example.api.domain.CounterpartyRiskProfile;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CounterpartyRiskProfileResponse(
        Long counterpartyId,
        String legalName,
        String countryCode,
        String creditRating,
        BigDecimal riskScore,
        BigDecimal exposureLimit,
        LocalDateTime createdAt
) {
    public static CounterpartyRiskProfileResponse fromEntity(CounterpartyRiskProfile entity) {
        return new CounterpartyRiskProfileResponse(
                entity.getCounterpartyId(),
                entity.getLegalName(),
                entity.getCountryCode(),
                entity.getCreditRating(),
                entity.getRiskScore(),
                entity.getExposureLimit(),
                entity.getCreatedAt()
        );
    }
}
