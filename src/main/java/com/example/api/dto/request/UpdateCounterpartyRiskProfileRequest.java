package com.example.api.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record UpdateCounterpartyRiskProfileRequest(
        @NotBlank(message = "Legal name is required")
        @Size(max = 200, message = "Legal name must not exceed 200 characters")
        String legalName,

        @NotBlank(message = "Country code is required")
        @Size(max = 3, message = "Country code must not exceed 3 characters")
        String countryCode,

        @Size(max = 10, message = "Credit rating must not exceed 10 characters")
        String creditRating,

        @DecimalMin(value = "0.00", message = "Risk score must not be negative")
        @Digits(integer = 3, fraction = 2, message = "Risk score must have at most 3 integer and 2 fractional digits")
        BigDecimal riskScore,

        @DecimalMin(value = "0.00", message = "Exposure limit must not be negative")
        @Digits(integer = 16, fraction = 2,
                message = "Exposure limit must have at most 16 integer and 2 fractional digits")
        BigDecimal exposureLimit
) {
}
