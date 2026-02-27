package com.example.api.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/**
 * DTO for creating a new transfer.
 */
public record CreateTransferenciaRequest(
        @NotNull(message = "Source account id is required")
        Long cuentaOrigenId,

        @NotNull(message = "Destination account id is required")
        Long cuentaDestinoId,

        @NotNull(message = "Amount is required")
        @DecimalMin(value = "0.01", message = "Amount must be positive")
        @Digits(integer = 13, fraction = 2, message = "Amount must have at most 13 integer and 2 fractional digits")
        BigDecimal importe,

        @NotBlank(message = "Currency is required")
        @Size(max = 3, message = "Currency must not exceed 3 characters")
        String divisa,

        @Size(max = 255, message = "Description must not exceed 255 characters")
        String concepto,

        @Size(max = 100, message = "External reference must not exceed 100 characters")
        String referenciaExterna
) {
}
