package com.example.api.application;

import com.example.api.dto.request.CreateCounterpartyRiskProfileRequest;
import com.example.api.dto.request.UpdateCounterpartyRiskProfileRequest;
import com.example.api.dto.response.CounterpartyRiskProfileResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CounterpartyRiskProfileService {

    CounterpartyRiskProfileResponse create(CreateCounterpartyRiskProfileRequest request);

    CounterpartyRiskProfileResponse getById(UUID id);

    Page<CounterpartyRiskProfileResponse> getAll(Pageable pageable);

    CounterpartyRiskProfileResponse update(UUID id, UpdateCounterpartyRiskProfileRequest request);

    void delete(UUID id);
}
