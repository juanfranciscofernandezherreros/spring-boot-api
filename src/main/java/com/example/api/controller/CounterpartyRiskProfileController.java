package com.example.api.controller;

import com.example.api.application.CounterpartyRiskProfileService;
import com.example.api.dto.request.CreateCounterpartyRiskProfileRequest;
import com.example.api.dto.request.UpdateCounterpartyRiskProfileRequest;
import com.example.api.dto.response.CounterpartyRiskProfileResponse;
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

import java.util.UUID;

@RestController
@RequestMapping("${api.base-path}/counterparty-risk-profiles")
public class CounterpartyRiskProfileController {

    private final CounterpartyRiskProfileService service;

    public CounterpartyRiskProfileController(CounterpartyRiskProfileService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CounterpartyRiskProfileResponse> create(
            @Valid @RequestBody CreateCounterpartyRiskProfileRequest request) {
        CounterpartyRiskProfileResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CounterpartyRiskProfileResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<CounterpartyRiskProfileResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(service.getAll(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CounterpartyRiskProfileResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateCounterpartyRiskProfileRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
