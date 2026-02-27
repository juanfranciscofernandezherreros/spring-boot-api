package com.example.api.application;

import com.example.api.application.mapper.CryptocurrencyMapper;
import com.example.api.domain.Cryptocurrency;
import com.example.api.dto.request.CreateCryptocurrencyRequest;
import com.example.api.dto.request.UpdateCryptocurrencyRequest;
import com.example.api.dto.response.CryptocurrencyResponse;
import com.example.api.exception.ResourceNotFoundException;
import com.example.api.infrastructure.CryptocurrencyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CryptocurrencyServiceImpl implements CryptocurrencyService {

    private final CryptocurrencyRepository repository;
    private final CryptocurrencyMapper mapper;

    public CryptocurrencyServiceImpl(CryptocurrencyRepository repository,
                                     CryptocurrencyMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public CryptocurrencyResponse create(CreateCryptocurrencyRequest request) {
        Cryptocurrency entity = new Cryptocurrency(
                request.name(),
                request.symbol(),
                request.slug(),
                request.marketCap(),
                request.priceUsd(),
                request.volume24h(),
                request.circulatingSupply(),
                request.maxSupply(),
                request.percentChange1h(),
                request.percentChange24h(),
                request.percentChange7d(),
                request.rankPosition(),
                request.isActive()
        );
        Cryptocurrency saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CryptocurrencyResponse getById(Long id) {
        Cryptocurrency entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cryptocurrency not found with id: " + id));
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CryptocurrencyResponse> getAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }

    @Override
    @Transactional
    public CryptocurrencyResponse update(Long id, UpdateCryptocurrencyRequest request) {
        Cryptocurrency entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cryptocurrency not found with id: " + id));
        entity.updateDetails(
                request.name(),
                request.symbol(),
                request.slug(),
                request.marketCap(),
                request.priceUsd(),
                request.volume24h(),
                request.circulatingSupply(),
                request.maxSupply(),
                request.percentChange1h(),
                request.percentChange24h(),
                request.percentChange7d(),
                request.rankPosition(),
                request.isActive()
        );
        Cryptocurrency updated = repository.save(entity);
        return mapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Cryptocurrency not found with id: " + id);
        }
        repository.deleteById(id);
    }
}
