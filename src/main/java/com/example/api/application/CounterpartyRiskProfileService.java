package com.example.api.application;

import com.example.api.dto.CounterpartyRiskProfileResponse;
import com.example.api.dto.CreateCounterpartyRiskProfileRequest;
import com.example.api.dto.UpdateCounterpartyRiskProfileRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CounterpartyRiskProfileService {

    CounterpartyRiskProfileResponse create(CreateCounterpartyRiskProfileRequest request);

    CounterpartyRiskProfileResponse getById(Long id);

    Page<CounterpartyRiskProfileResponse> getAll(Pageable pageable);

    CounterpartyRiskProfileResponse update(Long id, UpdateCounterpartyRiskProfileRequest request);

    void delete(Long id);
}
