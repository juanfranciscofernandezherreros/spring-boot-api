package com.example.api.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record CounterpartyRiskProfileResponse(
        UUID counterpartyId,
        String legalName,
        String countryCode,
        String creditRating,
        BigDecimal riskScore,
        BigDecimal exposureLimit,
        Instant createdAt
) {
}
