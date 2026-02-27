package com.example.api.dto.response;

import java.math.BigDecimal;
import java.time.Instant;

public record CryptocurrencyResponse(
        Long id,
        String name,
        String symbol,
        String slug,
        BigDecimal marketCap,
        BigDecimal priceUsd,
        BigDecimal volume24h,
        BigDecimal circulatingSupply,
        BigDecimal maxSupply,
        BigDecimal percentChange1h,
        BigDecimal percentChange24h,
        BigDecimal percentChange7d,
        Integer rankPosition,
        Boolean isActive,
        Instant createdAt,
        Instant updatedAt
) {
}
