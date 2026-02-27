package com.example.api.dto.response;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * DTO for transfer response data.
 */
public record TransferenciaResponse(
        Long idTransferencia,
        Long cuentaOrigenId,
        Long cuentaDestinoId,
        BigDecimal importe,
        String divisa,
        String concepto,
        String estado,
        Instant fechaCreacion,
        Instant fechaEjecucion,
        String referenciaExterna
) {
}
