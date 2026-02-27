package com.example.api.application;

import com.example.api.application.mapper.TransferenciaMapper;
import com.example.api.domain.EstadoTransferencia;
import com.example.api.domain.Transferencia;
import com.example.api.dto.request.CreateTransferenciaRequest;
import com.example.api.dto.request.UpdateTransferenciaRequest;
import com.example.api.dto.response.TransferenciaResponse;
import com.example.api.exception.BusinessException;
import com.example.api.exception.ResourceNotFoundException;
import com.example.api.infrastructure.TransferenciaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransferenciaServiceImplTest {

    @Mock
    private TransferenciaRepository repository;

    @Mock
    private TransferenciaMapper mapper;

    @InjectMocks
    private TransferenciaServiceImpl service;

    private static final Long SAMPLE_ID = 1L;
    private static final Long NON_EXISTENT_ID = 999L;

    private TransferenciaResponse sampleResponse() {
        return new TransferenciaResponse(
                SAMPLE_ID, 100L, 200L, new BigDecimal("500.00"), "EUR",
                "Test payment", "PENDIENTE",
                Instant.parse("2024-01-01T12:00:00Z"), null, "REF-001");
    }

    @Test
    void create_shouldSaveAndReturnResponse() {
        CreateTransferenciaRequest request = new CreateTransferenciaRequest(
                100L, 200L, new BigDecimal("500.00"), "EUR", "Test payment", "REF-001");

        Transferencia entity = new Transferencia(
                100L, 200L, new BigDecimal("500.00"), "EUR", "Test payment", "REF-001");

        when(repository.save(any(Transferencia.class))).thenReturn(entity);
        when(mapper.toResponse(any(Transferencia.class))).thenReturn(sampleResponse());

        TransferenciaResponse response = service.create(request);

        assertThat(response.cuentaOrigenId()).isEqualTo(100L);
        assertThat(response.cuentaDestinoId()).isEqualTo(200L);
        assertThat(response.importe()).isEqualByComparingTo(new BigDecimal("500.00"));
        assertThat(response.divisa()).isEqualTo("EUR");
        assertThat(response.concepto()).isEqualTo("Test payment");
        verify(repository).save(any(Transferencia.class));
        verify(mapper).toResponse(any(Transferencia.class));
    }

    @Test
    void getById_shouldReturnResponse_whenFound() {
        Transferencia entity = new Transferencia(
                100L, 200L, new BigDecimal("500.00"), "EUR", "Test payment", "REF-001");

        when(repository.findById(SAMPLE_ID)).thenReturn(Optional.of(entity));
        when(mapper.toResponse(any(Transferencia.class))).thenReturn(sampleResponse());

        TransferenciaResponse response = service.getById(SAMPLE_ID);

        assertThat(response.cuentaOrigenId()).isEqualTo(100L);
        verify(repository).findById(SAMPLE_ID);
        verify(mapper).toResponse(any(Transferencia.class));
    }

    @Test
    void getById_shouldThrowResourceNotFoundException_whenNotFound() {
        when(repository.findById(NON_EXISTENT_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(NON_EXISTENT_ID))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(NON_EXISTENT_ID.toString());
    }

    @Test
    void getAll_shouldReturnPagedResults() {
        Transferencia entity = new Transferencia(
                100L, 200L, new BigDecimal("500.00"), "EUR", "Test payment", "REF-001");
        Page<Transferencia> page = new PageImpl<>(List.of(entity));

        when(repository.findAll(PageRequest.of(0, 20))).thenReturn(page);
        when(mapper.toResponse(any(Transferencia.class))).thenReturn(sampleResponse());

        Page<TransferenciaResponse> result = service.getAll(PageRequest.of(0, 20));

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).cuentaOrigenId()).isEqualTo(100L);
    }

    @Test
    void update_shouldUpdateAndReturnResponse_whenFound() {
        Transferencia entity = new Transferencia(
                100L, 200L, new BigDecimal("500.00"), "EUR", "Test payment", "REF-001");
        UpdateTransferenciaRequest request = new UpdateTransferenciaRequest(
                300L, 400L, new BigDecimal("750.00"), "USD",
                "Updated payment", "COMPLETADA", Instant.now(), "REF-002");

        when(repository.findById(SAMPLE_ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(Transferencia.class))).thenReturn(entity);
        when(mapper.toResponse(any(Transferencia.class))).thenReturn(sampleResponse());

        TransferenciaResponse response = service.update(SAMPLE_ID, request);

        assertThat(response).isNotNull();
        verify(repository).findById(SAMPLE_ID);
        verify(repository).save(any(Transferencia.class));
        verify(mapper).toResponse(any(Transferencia.class));
    }

    @Test
    void update_shouldThrowResourceNotFoundException_whenNotFound() {
        UpdateTransferenciaRequest request = new UpdateTransferenciaRequest(
                300L, 400L, new BigDecimal("750.00"), "USD",
                "Updated payment", "COMPLETADA", null, "REF-002");

        when(repository.findById(NON_EXISTENT_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(NON_EXISTENT_ID, request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(NON_EXISTENT_ID.toString());
    }

    @Test
    void delete_shouldDelete_whenFound() {
        when(repository.existsById(SAMPLE_ID)).thenReturn(true);

        service.delete(SAMPLE_ID);

        verify(repository).deleteById(SAMPLE_ID);
    }

    @Test
    void delete_shouldThrowResourceNotFoundException_whenNotFound() {
        when(repository.existsById(NON_EXISTENT_ID)).thenReturn(false);

        assertThatThrownBy(() -> service.delete(NON_EXISTENT_ID))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(NON_EXISTENT_ID.toString());

        verify(repository, never()).deleteById(NON_EXISTENT_ID);
    }

    @Test
    void update_shouldThrowBusinessException_whenEstadoIsInvalid() {
        Transferencia entity = new Transferencia(
                100L, 200L, new BigDecimal("500.00"), "EUR", "Test payment", "REF-001");
        UpdateTransferenciaRequest request = new UpdateTransferenciaRequest(
                100L, 200L, new BigDecimal("500.00"), "EUR",
                "Test payment", "INVALID_STATE", null, "REF-001");

        when(repository.findById(SAMPLE_ID)).thenReturn(Optional.of(entity));

        assertThatThrownBy(() -> service.update(SAMPLE_ID, request))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Invalid transfer state: INVALID_STATE");
    }
}
