package com.example.api.application;

import com.example.api.domain.CounterpartyRiskProfile;
import com.example.api.application.mapper.CounterpartyRiskProfileMapper;
import com.example.api.dto.request.CreateCounterpartyRiskProfileRequest;
import com.example.api.dto.request.UpdateCounterpartyRiskProfileRequest;
import com.example.api.dto.response.CounterpartyRiskProfileResponse;
import com.example.api.exception.ResourceNotFoundException;
import com.example.api.infrastructure.CounterpartyRiskProfileRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CounterpartyRiskProfileServiceImpl implements CounterpartyRiskProfileService {

    private final CounterpartyRiskProfileRepository repository;
    private final CounterpartyRiskProfileMapper mapper;

    public CounterpartyRiskProfileServiceImpl(CounterpartyRiskProfileRepository repository,
                                              CounterpartyRiskProfileMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public CounterpartyRiskProfileResponse create(CreateCounterpartyRiskProfileRequest request) {
        CounterpartyRiskProfile entity = new CounterpartyRiskProfile(
                request.legalName(),
                request.countryCode(),
                request.creditRating(),
                request.riskScore(),
                request.exposureLimit()
        );
        CounterpartyRiskProfile saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CounterpartyRiskProfileResponse getById(UUID id) {
        CounterpartyRiskProfile entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Counterparty risk profile not found with id: " + id));
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CounterpartyRiskProfileResponse> getAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }

    @Override
    @Transactional
    public CounterpartyRiskProfileResponse update(UUID id, UpdateCounterpartyRiskProfileRequest request) {
        CounterpartyRiskProfile entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Counterparty risk profile not found with id: " + id));
        entity.updateDetails(
                request.legalName(),
                request.countryCode(),
                request.creditRating(),
                request.riskScore(),
                request.exposureLimit()
        );
        CounterpartyRiskProfile updated = repository.save(entity);
        return mapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Counterparty risk profile not found with id: " + id);
        }
        repository.deleteById(id);
    }
}
