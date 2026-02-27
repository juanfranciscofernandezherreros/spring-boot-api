package com.example.api.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Domain entity representing a bank transfer (transferencia).
 */
@Entity
@Table(name = "transferencias", indexes = {
        @Index(name = "idx_cuenta_origen", columnList = "cuenta_origen_id"),
        @Index(name = "idx_cuenta_destino", columnList = "cuenta_destino_id")
})
public class Transferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transferencia")
    private Long idTransferencia;

    @Column(name = "cuenta_origen_id", nullable = false)
    private Long cuentaOrigenId;

    @Column(name = "cuenta_destino_id", nullable = false)
    private Long cuentaDestinoId;

    @Column(name = "importe", nullable = false, precision = 15, scale = 2)
    private BigDecimal importe;

    @Column(name = "divisa", nullable = false, length = 3)
    private String divisa;

    @Column(name = "concepto", length = 255)
    private String concepto;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private EstadoTransferencia estado;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private Instant fechaCreacion;

    @Column(name = "fecha_ejecucion")
    private Instant fechaEjecucion;

    @Column(name = "referencia_externa", length = 100)
    private String referenciaExterna;

    protected Transferencia() {
    }

    /**
     * Creates a new transfer with domain invariant validation.
     *
     * @param cuentaOrigenId    source account identifier
     * @param cuentaDestinoId   destination account identifier
     * @param importe           transfer amount (must be positive)
     * @param divisa            currency code (3 characters)
     * @param concepto          optional description
     * @param referenciaExterna optional external reference
     */
    public Transferencia(Long cuentaOrigenId, Long cuentaDestinoId,
                         BigDecimal importe, String divisa,
                         String concepto, String referenciaExterna) {
        validateCuentaOrigenId(cuentaOrigenId);
        validateCuentaDestinoId(cuentaDestinoId);
        validateImporte(importe);
        validateDivisa(divisa);
        this.cuentaOrigenId = cuentaOrigenId;
        this.cuentaDestinoId = cuentaDestinoId;
        this.importe = importe;
        this.divisa = divisa;
        this.concepto = concepto;
        this.estado = EstadoTransferencia.PENDIENTE;
        this.fechaCreacion = Instant.now();
        this.referenciaExterna = referenciaExterna;
    }

    /**
     * Updates the mutable details of this transfer.
     *
     * @param cuentaOrigenId    source account identifier
     * @param cuentaDestinoId   destination account identifier
     * @param importe           transfer amount (must be positive)
     * @param divisa            currency code (3 characters)
     * @param concepto          optional description
     * @param estado            transfer state
     * @param fechaEjecucion    optional execution timestamp
     * @param referenciaExterna optional external reference
     */
    public void updateDetails(Long cuentaOrigenId, Long cuentaDestinoId,
                              BigDecimal importe, String divisa,
                              String concepto, EstadoTransferencia estado,
                              Instant fechaEjecucion, String referenciaExterna) {
        validateCuentaOrigenId(cuentaOrigenId);
        validateCuentaDestinoId(cuentaDestinoId);
        validateImporte(importe);
        validateDivisa(divisa);
        validateEstado(estado);
        this.cuentaOrigenId = cuentaOrigenId;
        this.cuentaDestinoId = cuentaDestinoId;
        this.importe = importe;
        this.divisa = divisa;
        this.concepto = concepto;
        this.estado = estado;
        this.fechaEjecucion = fechaEjecucion;
        this.referenciaExterna = referenciaExterna;
    }

    private void validateCuentaOrigenId(Long value) {
        if (value == null) {
            throw new IllegalArgumentException("Source account id must not be null");
        }
    }

    private void validateCuentaDestinoId(Long value) {
        if (value == null) {
            throw new IllegalArgumentException("Destination account id must not be null");
        }
    }

    private void validateImporte(BigDecimal value) {
        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }

    private void validateDivisa(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Currency must not be blank");
        }
    }

    private void validateEstado(EstadoTransferencia value) {
        if (value == null) {
            throw new IllegalArgumentException("State must not be null");
        }
    }

    public Long getIdTransferencia() {
        return idTransferencia;
    }

    public Long getCuentaOrigenId() {
        return cuentaOrigenId;
    }

    public Long getCuentaDestinoId() {
        return cuentaDestinoId;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public String getDivisa() {
        return divisa;
    }

    public String getConcepto() {
        return concepto;
    }

    public EstadoTransferencia getEstado() {
        return estado;
    }

    public Instant getFechaCreacion() {
        return fechaCreacion;
    }

    public Instant getFechaEjecucion() {
        return fechaEjecucion;
    }

    public String getReferenciaExterna() {
        return referenciaExterna;
    }
}
