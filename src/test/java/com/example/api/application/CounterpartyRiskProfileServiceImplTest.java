package com.example.api.application;

import com.example.api.domain.CounterpartyRiskProfile;
import com.example.api.application.mapper.CounterpartyRiskProfileMapper;
import com.example.api.dto.request.CreateCounterpartyRiskProfileRequest;
import com.example.api.dto.request.UpdateCounterpartyRiskProfileRequest;
import com.example.api.dto.response.CounterpartyRiskProfileResponse;
import com.example.api.exception.ResourceNotFoundException;
import com.example.api.infrastructure.CounterpartyRiskProfileRepository;
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
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CounterpartyRiskProfileServiceImplTest {

    @Mock
    private CounterpartyRiskProfileRepository repository;

    @Mock
    private CounterpartyRiskProfileMapper mapper;

    @InjectMocks
    private CounterpartyRiskProfileServiceImpl service;

    private static final UUID SAMPLE_ID = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private static final UUID NON_EXISTENT_ID = UUID.fromString("00000000-0000-0000-0000-000000000999");

    private CounterpartyRiskProfileResponse sampleResponse() {
        return new CounterpartyRiskProfileResponse(
                null, "Acme Corp", "USA", "AA+",
                new BigDecimal("85.50"), new BigDecimal("1000000.00"), null);
    }

    @Test
    void create_shouldSaveAndReturnResponse() {
        CreateCounterpartyRiskProfileRequest request = new CreateCounterpartyRiskProfileRequest(
                "Acme Corp", "USA", "AA+", new BigDecimal("85.50"), new BigDecimal("1000000.00"));

        CounterpartyRiskProfile entity = new CounterpartyRiskProfile(
                "Acme Corp", "USA", "AA+", new BigDecimal("85.50"), new BigDecimal("1000000.00"));

        when(repository.save(any(CounterpartyRiskProfile.class))).thenReturn(entity);
        when(mapper.toResponse(any(CounterpartyRiskProfile.class))).thenReturn(sampleResponse());

        CounterpartyRiskProfileResponse response = service.create(request);

        assertThat(response.legalName()).isEqualTo("Acme Corp");
        assertThat(response.countryCode()).isEqualTo("USA");
        assertThat(response.creditRating()).isEqualTo("AA+");
        assertThat(response.riskScore()).isEqualByComparingTo(new BigDecimal("85.50"));
        assertThat(response.exposureLimit()).isEqualByComparingTo(new BigDecimal("1000000.00"));
        verify(repository).save(any(CounterpartyRiskProfile.class));
        verify(mapper).toResponse(any(CounterpartyRiskProfile.class));
    }

    @Test
    void getById_shouldReturnResponse_whenFound() {
        CounterpartyRiskProfile entity = new CounterpartyRiskProfile(
                "Acme Corp", "USA", "AA+", new BigDecimal("85.50"), new BigDecimal("1000000.00"));

        when(repository.findById(SAMPLE_ID)).thenReturn(Optional.of(entity));
        when(mapper.toResponse(any(CounterpartyRiskProfile.class))).thenReturn(sampleResponse());

        CounterpartyRiskProfileResponse response = service.getById(SAMPLE_ID);

        assertThat(response.legalName()).isEqualTo("Acme Corp");
        verify(repository).findById(SAMPLE_ID);
        verify(mapper).toResponse(any(CounterpartyRiskProfile.class));
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
        CounterpartyRiskProfile entity = new CounterpartyRiskProfile(
                "Acme Corp", "USA", "AA+", new BigDecimal("85.50"), new BigDecimal("1000000.00"));
        Page<CounterpartyRiskProfile> page = new PageImpl<>(List.of(entity));

        when(repository.findAll(PageRequest.of(0, 20))).thenReturn(page);
        when(mapper.toResponse(any(CounterpartyRiskProfile.class))).thenReturn(sampleResponse());

        Page<CounterpartyRiskProfileResponse> result = service.getAll(PageRequest.of(0, 20));

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).legalName()).isEqualTo("Acme Corp");
    }

    @Test
    void update_shouldUpdateAndReturnResponse_whenFound() {
        CounterpartyRiskProfile entity = new CounterpartyRiskProfile(
                "Acme Corp", "USA", "AA+", new BigDecimal("85.50"), new BigDecimal("1000000.00"));
        UpdateCounterpartyRiskProfileRequest request = new UpdateCounterpartyRiskProfileRequest(
                "Acme Updated", "GBR", "A-", new BigDecimal("70.00"), new BigDecimal("500000.00"));

        when(repository.findById(SAMPLE_ID)).thenReturn(Optional.of(entity));
        when(repository.save(any(CounterpartyRiskProfile.class))).thenReturn(entity);
        when(mapper.toResponse(any(CounterpartyRiskProfile.class))).thenReturn(sampleResponse());

        CounterpartyRiskProfileResponse response = service.update(SAMPLE_ID, request);

        assertThat(response).isNotNull();
        verify(repository).findById(SAMPLE_ID);
        verify(repository).save(any(CounterpartyRiskProfile.class));
        verify(mapper).toResponse(any(CounterpartyRiskProfile.class));
    }

    @Test
    void update_shouldThrowResourceNotFoundException_whenNotFound() {
        UpdateCounterpartyRiskProfileRequest request = new UpdateCounterpartyRiskProfileRequest(
                "Acme Updated", "GBR", "A-", new BigDecimal("70.00"), new BigDecimal("500000.00"));

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
