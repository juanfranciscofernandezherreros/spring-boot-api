package com.example.api.application;

import com.example.api.dto.request.CreateCryptocurrencyRequest;
import com.example.api.dto.request.UpdateCryptocurrencyRequest;
import com.example.api.dto.response.CryptocurrencyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CryptocurrencyService {

    CryptocurrencyResponse create(CreateCryptocurrencyRequest request);

    CryptocurrencyResponse getById(Long id);

    Page<CryptocurrencyResponse> getAll(Pageable pageable);

    CryptocurrencyResponse update(Long id, UpdateCryptocurrencyRequest request);

    void delete(Long id);
}
