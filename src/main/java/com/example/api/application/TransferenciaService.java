package com.example.api.application;

import com.example.api.dto.request.CreateTransferenciaRequest;
import com.example.api.dto.request.UpdateTransferenciaRequest;
import com.example.api.dto.response.TransferenciaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for transfer operations.
 */
public interface TransferenciaService {

    /**
     * Creates a new transfer.
     *
     * @param request the creation request
     * @return the created transfer response
     */
    TransferenciaResponse create(CreateTransferenciaRequest request);

    /**
     * Retrieves a transfer by its identifier.
     *
     * @param id the transfer identifier
     * @return the transfer response
     */
    TransferenciaResponse getById(Long id);

    /**
     * Retrieves all transfers with pagination.
     *
     * @param pageable pagination parameters
     * @return a page of transfer responses
     */
    Page<TransferenciaResponse> getAll(Pageable pageable);

    /**
     * Updates an existing transfer.
     *
     * @param id      the transfer identifier
     * @param request the update request
     * @return the updated transfer response
     */
    TransferenciaResponse update(Long id, UpdateTransferenciaRequest request);

    /**
     * Deletes a transfer by its identifier.
     *
     * @param id the transfer identifier
     */
    void delete(Long id);
}
