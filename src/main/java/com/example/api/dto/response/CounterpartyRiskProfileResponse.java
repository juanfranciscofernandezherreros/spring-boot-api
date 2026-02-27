package com.example.api.dto.response;

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
}
