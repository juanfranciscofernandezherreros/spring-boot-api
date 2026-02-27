package com.example.api.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateCryptocurrencyRequest(
        @NotBlank(message = "Name is required")
        @Size(max = 100, message = "Name must not exceed 100 characters")
        String name,

        @NotBlank(message = "Symbol is required")
        @Size(max = 20, message = "Symbol must not exceed 20 characters")
        String symbol,

        @NotBlank(message = "Slug is required")
        @Size(max = 120, message = "Slug must not exceed 120 characters")
        String slug,

        @Digits(integer = 18, fraction = 2,
                message = "Market cap must have at most 18 integer and 2 fractional digits")
        BigDecimal marketCap,

        @DecimalMin(value = "0.00", message = "Price must not be negative")
        @Digits(integer = 10, fraction = 8,
                message = "Price must have at most 10 integer and 8 fractional digits")
        BigDecimal priceUsd,

        @Digits(integer = 18, fraction = 2,
                message = "Volume 24h must have at most 18 integer and 2 fractional digits")
        BigDecimal volume24h,

        @Digits(integer = 22, fraction = 8,
                message = "Circulating supply must have at most 22 integer and 8 fractional digits")
        BigDecimal circulatingSupply,

        @Digits(integer = 22, fraction = 8,
                message = "Max supply must have at most 22 integer and 8 fractional digits")
        BigDecimal maxSupply,

        @Digits(integer = 4, fraction = 2,
                message = "Percent change 1h must have at most 4 integer and 2 fractional digits")
        BigDecimal percentChange1h,

        @Digits(integer = 4, fraction = 2,
                message = "Percent change 24h must have at most 4 integer and 2 fractional digits")
        BigDecimal percentChange24h,

        @Digits(integer = 4, fraction = 2,
                message = "Percent change 7d must have at most 4 integer and 2 fractional digits")
        BigDecimal percentChange7d,

        Integer rankPosition,

        Boolean isActive
) {
}
