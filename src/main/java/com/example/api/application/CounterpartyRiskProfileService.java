package com.example.api.application;

import com.example.api.dto.request.CreateCounterpartyRiskProfileRequest;
import com.example.api.dto.request.UpdateCounterpartyRiskProfileRequest;
import com.example.api.dto.response.CounterpartyRiskProfileResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CounterpartyRiskProfileService {

    CounterpartyRiskProfileResponse create(CreateCounterpartyRiskProfileRequest request);

    CounterpartyRiskProfileResponse getById(Long id);

    Page<CounterpartyRiskProfileResponse> getAll(Pageable pageable);

    CounterpartyRiskProfileResponse update(Long id, UpdateCounterpartyRiskProfileRequest request);

    void delete(Long id);
}
