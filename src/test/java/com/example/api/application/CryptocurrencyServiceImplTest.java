package com.example.api.application;

import com.example.api.application.mapper.CryptocurrencyMapper;
import com.example.api.domain.Cryptocurrency;
import com.example.api.dto.request.CreateCryptocurrencyRequest;
import com.example.api.dto.request.UpdateCryptocurrencyRequest;
import com.example.api.dto.response.CryptocurrencyResponse;
import com.example.api.exception.ResourceNotFoundException;
import com.example.api.infrastructure.CryptocurrencyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CryptocurrencyServiceImplTest {

    @Mock
    private CryptocurrencyRepository repository;

    @Mock
    private CryptocurrencyMapper mapper;

    @InjectMocks
    private CryptocurrencyServiceImpl service;

    private static final Long SAMPLE_ID = 1L;
    private static final Long NON_EXISTENT_ID = 999L;

    private CryptocurrencyResponse sampleResponse() {
        return new CryptocurrencyResponse(
                SAMPLE_ID, "Bitcoin", "BTC", "bitcoin",
                new BigDecimal("500000000000.00"), new BigDecimal("30000.00000000"),
                new BigDecimal("20000000000.00"),
                new BigDecimal("19000000.00000000"), new BigDecimal("21000000.00000000"),
                new BigDecimal("0.50"), new BigDecimal("1.20"), new BigDecimal("-3.40"),
                1, true, null, null);
    }

    @Test
    void create_shouldSaveAndReturnResponse() {
        CreateCryptocurrencyRequest request = new CreateCryptocurrencyRequest(
                "Bitcoin", "BTC", "bitcoin",
                new BigDecimal("500000000000.00"), new BigDecimal("30000.00000000"),
                new BigDecimal("20000000000.00"),
                new BigDecimal("19000000.00000000"), new BigDecimal("21000000.00000000"),
                new BigDecimal("0.50"), new BigDecimal("1.20"), new BigDecimal("-3.40"),
                1, true);

        Cryptocurrency entity = new Cryptocurrency(
                "Bitcoin", "BTC", "bitcoin",
                new BigDecimal("500000000000.00"), new BigDecimal("30000.00000000"),
                new BigDecimal("20000000000.00"),
                new BigDecimal("19000000.00000000"), new BigDecimal("21000000.00000000"),
                new BigDecimal("0.50"), new BigDecimal("1.20"), new BigDecimal("-3.40"),
                1, true);

        when(repository.save(any(Cryptocurrency.class))).thenReturn(entity);
        when(mapper.toResponse(any(Cryptocurrency.class))).thenReturn(sampleResponse());

        CryptocurrencyResponse response = service.create(request);

        assertThat(response.name()).isEqualTo("Bitcoin");
        assertThat(response.symbol()).isEqualTo("BTC");
        assertThat(response.slug()).isEqualTo("bitcoin");
        assertThat(response.priceUsd()).isEqualByComparingTo(new BigDecimal("30000.00000000"));
        verify(repository).save(any(Cryptocurrency.class));
        verify(mapper).toResponse(any(Cryptocurrency.class));
    }

    @Test
    void getById_shouldReturnResponse_whenFound() {
        Cryptocurrency entity = new Cryptocurrency(
                "Bitcoin", "BTC", "bitcoin",
                null, new BigDecimal("30000.00000000"), null,
                null, null, null, null, null, 1, true);

        when(repository.findById(SAMPLE_ID)).thenReturn(Optional.of(entity));
        when(mapper.toResponse(any(Cryptocurrency.class))).thenReturn(sampleResponse());

        CryptocurrencyResponse response = service.getById(SAMPLE_ID);

        assertThat(response.name()).isEqualTo("Bitcoin");
        verify(repository).findById(SAMPLE_ID);
        verify(mapper).toResponse(any(Cryptocurrency.class));
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
        Cryptocurrency entity = new Cryptocurrency(
                "Bitcoin", "BTC", "bitcoin",
                null, new BigDecimal("30000.00000000"), null,
                null, null, null, null, null, 1, true);
        Page<Cryptocurrency> page = new PageImpl<>(List.of(entity));

        when(repository.findAll(PageRequest.of(0, 20))).thenReturn(page);
        when(mapper.toResponse(any(Cryptocurrency.class))).thenReturn(sampleResponse());

        Page<CryptocurrencyResponse> result = service.getAll(PageRequest.of(0, 20));

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).name()).isEqualTo("Bitcoin");
    }

    @Test
    void update_shouldUpdateAndReturnResponse_whenFound() {
        Cryptocurrency entity = new Cryptocurrency(
                "Bitcoin", "BTC", "bitcoin",
                null, new BigDecimal("30000.00000000"), null,
                null, null, null, null, null, 1, true);
        UpdateCryptocurrencyRequest request = new UpdateCryptocurrencyRequest(
                "Bitcoin Updated", "BTC", "bitcoin",
                new BigDecimal("600000000000.00"), new BigDecimal("35000.00000000"),
                null, null, null, null, null, null, 1, true);

        when(repository.findById(SAMPLE_ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(Cryptocurrency.class))).thenReturn(entity);
        when(mapper.toResponse(any(Cryptocurrency.class))).thenReturn(sampleResponse());

        CryptocurrencyResponse response = service.update(SAMPLE_ID, request);

        assertThat(response).isNotNull();
        verify(repository).findById(SAMPLE_ID);
        verify(repository).save(any(Cryptocurrency.class));
        verify(mapper).toResponse(any(Cryptocurrency.class));
    }

    @Test
    void update_shouldThrowResourceNotFoundException_whenNotFound() {
        UpdateCryptocurrencyRequest request = new UpdateCryptocurrencyRequest(
                "Bitcoin Updated", "BTC", "bitcoin",
                null, null, null, null, null, null, null, null, 1, true);

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
}
