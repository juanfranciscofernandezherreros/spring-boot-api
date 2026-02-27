package com.example.api.application;

import com.example.api.domain.CounterpartyRiskProfile;
import com.example.api.dto.CounterpartyRiskProfileResponse;
import com.example.api.dto.CreateCounterpartyRiskProfileRequest;
import com.example.api.dto.UpdateCounterpartyRiskProfileRequest;
import com.example.api.exception.ResourceNotFoundException;
import com.example.api.infrastructure.CounterpartyRiskProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CounterpartyRiskProfileServiceImpl implements CounterpartyRiskProfileService {

    private static final Logger log = LoggerFactory.getLogger(CounterpartyRiskProfileServiceImpl.class);

    private final CounterpartyRiskProfileRepository repository;

    public CounterpartyRiskProfileServiceImpl(CounterpartyRiskProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public CounterpartyRiskProfileResponse create(CreateCounterpartyRiskProfileRequest request) {
        log.info("Creating counterparty risk profile for: {}", request.legalName());
        CounterpartyRiskProfile entity = new CounterpartyRiskProfile(
                request.legalName(),
                request.countryCode(),
                request.creditRating(),
                request.riskScore(),
                request.exposureLimit()
        );
        CounterpartyRiskProfile saved = repository.save(entity);
        log.info("Created counterparty risk profile with id: {}", saved.getCounterpartyId());
        return CounterpartyRiskProfileResponse.fromEntity(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CounterpartyRiskProfileResponse getById(Long id) {
        log.info("Fetching counterparty risk profile with id: {}", id);
        CounterpartyRiskProfile entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Counterparty risk profile not found with id: " + id));
        return CounterpartyRiskProfileResponse.fromEntity(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CounterpartyRiskProfileResponse> getAll(Pageable pageable) {
        log.info("Fetching counterparty risk profiles, page: {}, size: {}",
                pageable.getPageNumber(), pageable.getPageSize());
        return repository.findAll(pageable)
                .map(CounterpartyRiskProfileResponse::fromEntity);
    }

    @Override
    @Transactional
    public CounterpartyRiskProfileResponse update(Long id, UpdateCounterpartyRiskProfileRequest request) {
        log.info("Updating counterparty risk profile with id: {}", id);
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
        log.info("Updated counterparty risk profile with id: {}", updated.getCounterpartyId());
        return CounterpartyRiskProfileResponse.fromEntity(updated);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Deleting counterparty risk profile with id: {}", id);
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Counterparty risk profile not found with id: " + id);
        }
        repository.deleteById(id);
        log.info("Deleted counterparty risk profile with id: {}", id);
    }
}
