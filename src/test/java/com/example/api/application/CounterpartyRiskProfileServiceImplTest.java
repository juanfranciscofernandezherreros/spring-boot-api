package com.example.api.application;

import com.example.api.domain.CounterpartyRiskProfile;
import com.example.api.dto.CounterpartyRiskProfileResponse;
import com.example.api.dto.CreateCounterpartyRiskProfileRequest;
import com.example.api.dto.UpdateCounterpartyRiskProfileRequest;
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

    @InjectMocks
    private CounterpartyRiskProfileServiceImpl service;

    @Test
    void create_shouldSaveAndReturnResponse() {
        CreateCounterpartyRiskProfileRequest request = new CreateCounterpartyRiskProfileRequest(
                "Acme Corp", "USA", "AA+", new BigDecimal("85.50"), new BigDecimal("1000000.00"));

        CounterpartyRiskProfile entity = new CounterpartyRiskProfile(
                "Acme Corp", "USA", "AA+", new BigDecimal("85.50"), new BigDecimal("1000000.00"));

        when(repository.save(any(CounterpartyRiskProfile.class))).thenReturn(entity);

        CounterpartyRiskProfileResponse response = service.create(request);

        assertThat(response.legalName()).isEqualTo("Acme Corp");
        assertThat(response.countryCode()).isEqualTo("USA");
        assertThat(response.creditRating()).isEqualTo("AA+");
        assertThat(response.riskScore()).isEqualByComparingTo(new BigDecimal("85.50"));
        assertThat(response.exposureLimit()).isEqualByComparingTo(new BigDecimal("1000000.00"));
        verify(repository).save(any(CounterpartyRiskProfile.class));
    }

    @Test
    void getById_shouldReturnResponse_whenFound() {
        CounterpartyRiskProfile entity = new CounterpartyRiskProfile(
                "Acme Corp", "USA", "AA+", new BigDecimal("85.50"), new BigDecimal("1000000.00"));

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        CounterpartyRiskProfileResponse response = service.getById(1L);

        assertThat(response.legalName()).isEqualTo("Acme Corp");
        verify(repository).findById(1L);
    }

    @Test
    void getById_shouldThrowResourceNotFoundException_whenNotFound() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("999");
    }

    @Test
    void getAll_shouldReturnPagedResults() {
        CounterpartyRiskProfile entity = new CounterpartyRiskProfile(
                "Acme Corp", "USA", "AA+", new BigDecimal("85.50"), new BigDecimal("1000000.00"));
        Page<CounterpartyRiskProfile> page = new PageImpl<>(List.of(entity));

        when(repository.findAll(PageRequest.of(0, 20))).thenReturn(page);

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

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(any(CounterpartyRiskProfile.class))).thenReturn(entity);

        CounterpartyRiskProfileResponse response = service.update(1L, request);

        assertThat(response).isNotNull();
        verify(repository).findById(1L);
        verify(repository).save(any(CounterpartyRiskProfile.class));
    }

    @Test
    void update_shouldThrowResourceNotFoundException_whenNotFound() {
        UpdateCounterpartyRiskProfileRequest request = new UpdateCounterpartyRiskProfileRequest(
                "Acme Updated", "GBR", "A-", new BigDecimal("70.00"), new BigDecimal("500000.00"));

        when(repository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(999L, request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("999");
    }

    @Test
    void delete_shouldDelete_whenFound() {
        when(repository.existsById(1L)).thenReturn(true);

        service.delete(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void delete_shouldThrowResourceNotFoundException_whenNotFound() {
        when(repository.existsById(999L)).thenReturn(false);

        assertThatThrownBy(() -> service.delete(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("999");

        verify(repository, never()).deleteById(999L);
    }
}
