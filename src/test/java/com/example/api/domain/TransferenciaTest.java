package com.example.api.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TransferenciaTest {

    @Test
    void constructor_shouldCreateEntity_withValidData() {
        Transferencia entity = new Transferencia(
                1L, 2L, new BigDecimal("100.50"), "EUR", "Payment", "REF-001");

        assertThat(entity.getCuentaOrigenId()).isEqualTo(1L);
        assertThat(entity.getCuentaDestinoId()).isEqualTo(2L);
        assertThat(entity.getImporte()).isEqualByComparingTo(new BigDecimal("100.50"));
        assertThat(entity.getDivisa()).isEqualTo("EUR");
        assertThat(entity.getConcepto()).isEqualTo("Payment");
        assertThat(entity.getEstado()).isEqualTo(EstadoTransferencia.PENDIENTE);
        assertThat(entity.getFechaCreacion()).isNotNull();
        assertThat(entity.getFechaEjecucion()).isNull();
        assertThat(entity.getReferenciaExterna()).isEqualTo("REF-001");
        assertThat(entity.getIdTransferencia()).isNull();
    }

    @Test
    void constructor_shouldAllowNullConceptoAndReferenciaExterna() {
        Transferencia entity = new Transferencia(
                1L, 2L, new BigDecimal("50.00"), "USD", null, null);

        assertThat(entity.getConcepto()).isNull();
        assertThat(entity.getReferenciaExterna()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenCuentaOrigenIdIsNull() {
        assertThatThrownBy(() -> new Transferencia(
                null, 2L, new BigDecimal("100.00"), "EUR", "Payment", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Source account id must not be null");
    }

    @Test
    void constructor_shouldThrow_whenCuentaDestinoIdIsNull() {
        assertThatThrownBy(() -> new Transferencia(
                1L, null, new BigDecimal("100.00"), "EUR", "Payment", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Destination account id must not be null");
    }

    @Test
    void constructor_shouldThrow_whenImporteIsNull() {
        assertThatThrownBy(() -> new Transferencia(
                1L, 2L, null, "EUR", "Payment", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Amount must be positive");
    }

    @Test
    void constructor_shouldThrow_whenImporteIsZero() {
        assertThatThrownBy(() -> new Transferencia(
                1L, 2L, BigDecimal.ZERO, "EUR", "Payment", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Amount must be positive");
    }

    @Test
    void constructor_shouldThrow_whenImporteIsNegative() {
        assertThatThrownBy(() -> new Transferencia(
                1L, 2L, new BigDecimal("-10.00"), "EUR", "Payment", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Amount must be positive");
    }

    @Test
    void constructor_shouldThrow_whenDivisaIsNull() {
        assertThatThrownBy(() -> new Transferencia(
                1L, 2L, new BigDecimal("100.00"), null, "Payment", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Currency must not be blank");
    }

    @Test
    void constructor_shouldThrow_whenDivisaIsBlank() {
        assertThatThrownBy(() -> new Transferencia(
                1L, 2L, new BigDecimal("100.00"), "  ", "Payment", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Currency must not be blank");
    }

    @Test
    void updateDetails_shouldUpdateAllFields() {
        Transferencia entity = new Transferencia(
                1L, 2L, new BigDecimal("100.00"), "EUR", "Payment", "REF-001");

        entity.updateDetails(3L, 4L, new BigDecimal("200.00"), "USD",
                "Updated", EstadoTransferencia.COMPLETADA, null, "REF-002");

        assertThat(entity.getCuentaOrigenId()).isEqualTo(3L);
        assertThat(entity.getCuentaDestinoId()).isEqualTo(4L);
        assertThat(entity.getImporte()).isEqualByComparingTo(new BigDecimal("200.00"));
        assertThat(entity.getDivisa()).isEqualTo("USD");
        assertThat(entity.getConcepto()).isEqualTo("Updated");
        assertThat(entity.getEstado()).isEqualTo(EstadoTransferencia.COMPLETADA);
        assertThat(entity.getReferenciaExterna()).isEqualTo("REF-002");
    }

    @Test
    void updateDetails_shouldThrow_whenCuentaOrigenIdIsNull() {
        Transferencia entity = new Transferencia(
                1L, 2L, new BigDecimal("100.00"), "EUR", "Payment", null);

        assertThatThrownBy(() -> entity.updateDetails(
                null, 2L, new BigDecimal("100.00"), "EUR",
                "Payment", EstadoTransferencia.PENDIENTE, null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Source account id must not be null");
    }

    @Test
    void updateDetails_shouldThrow_whenCuentaDestinoIdIsNull() {
        Transferencia entity = new Transferencia(
                1L, 2L, new BigDecimal("100.00"), "EUR", "Payment", null);

        assertThatThrownBy(() -> entity.updateDetails(
                1L, null, new BigDecimal("100.00"), "EUR",
                "Payment", EstadoTransferencia.PENDIENTE, null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Destination account id must not be null");
    }

    @Test
    void updateDetails_shouldThrow_whenImporteIsZero() {
        Transferencia entity = new Transferencia(
                1L, 2L, new BigDecimal("100.00"), "EUR", "Payment", null);

        assertThatThrownBy(() -> entity.updateDetails(
                1L, 2L, BigDecimal.ZERO, "EUR",
                "Payment", EstadoTransferencia.PENDIENTE, null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Amount must be positive");
    }

    @Test
    void updateDetails_shouldThrow_whenDivisaIsBlank() {
        Transferencia entity = new Transferencia(
                1L, 2L, new BigDecimal("100.00"), "EUR", "Payment", null);

        assertThatThrownBy(() -> entity.updateDetails(
                1L, 2L, new BigDecimal("100.00"), " ",
                "Payment", EstadoTransferencia.PENDIENTE, null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Currency must not be blank");
    }

    @Test
    void updateDetails_shouldThrow_whenEstadoIsNull() {
        Transferencia entity = new Transferencia(
                1L, 2L, new BigDecimal("100.00"), "EUR", "Payment", null);

        assertThatThrownBy(() -> entity.updateDetails(
                1L, 2L, new BigDecimal("100.00"), "EUR",
                "Payment", null, null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("State must not be null");
    }
}
