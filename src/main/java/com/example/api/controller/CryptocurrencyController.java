package com.example.api.controller;

import com.example.api.application.CryptocurrencyService;
import com.example.api.dto.request.CreateCryptocurrencyRequest;
import com.example.api.dto.request.UpdateCryptocurrencyRequest;
import com.example.api.dto.response.CryptocurrencyResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.base-path}${api.cryptocurrencies-path}")
public class CryptocurrencyController {

    private final CryptocurrencyService service;

    public CryptocurrencyController(CryptocurrencyService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CryptocurrencyResponse> create(
            @Valid @RequestBody CreateCryptocurrencyRequest request) {
        CryptocurrencyResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CryptocurrencyResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<CryptocurrencyResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(service.getAll(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CryptocurrencyResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCryptocurrencyRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
