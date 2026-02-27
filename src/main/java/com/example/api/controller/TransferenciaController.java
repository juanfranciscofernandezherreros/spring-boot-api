package com.example.api.controller;

import com.example.api.application.TransferenciaService;
import com.example.api.dto.request.CreateTransferenciaRequest;
import com.example.api.dto.request.UpdateTransferenciaRequest;
import com.example.api.dto.response.TransferenciaResponse;
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

/**
 * REST controller for transfer (transferencia) operations.
 */
@RestController
@RequestMapping("${api.base-path}${api.transferencias-path}")
public class TransferenciaController {

    private final TransferenciaService service;

    public TransferenciaController(TransferenciaService service) {
        this.service = service;
    }

    /**
     * Creates a new transfer.
     *
     * @param request the creation request
     * @return the created transfer with HTTP 201
     */
    @PostMapping
    public ResponseEntity<TransferenciaResponse> create(
            @Valid @RequestBody CreateTransferenciaRequest request) {
        TransferenciaResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Retrieves a transfer by its identifier.
     *
     * @param id the transfer identifier
     * @return the transfer with HTTP 200
     */
    @GetMapping("/{id}")
    public ResponseEntity<TransferenciaResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    /**
     * Retrieves all transfers with pagination.
     *
     * @param pageable pagination parameters
     * @return a page of transfers with HTTP 200
     */
    @GetMapping
    public ResponseEntity<Page<TransferenciaResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(service.getAll(pageable));
    }

    /**
     * Updates an existing transfer.
     *
     * @param id      the transfer identifier
     * @param request the update request
     * @return the updated transfer with HTTP 200
     */
    @PutMapping("/{id}")
    public ResponseEntity<TransferenciaResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTransferenciaRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    /**
     * Deletes a transfer by its identifier.
     *
     * @param id the transfer identifier
     * @return HTTP 204 No Content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
