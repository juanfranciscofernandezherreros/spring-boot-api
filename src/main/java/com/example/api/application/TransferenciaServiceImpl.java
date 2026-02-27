package com.example.api.application;

import com.example.api.application.mapper.TransferenciaMapper;
import com.example.api.domain.EstadoTransferencia;
import com.example.api.domain.Transferencia;
import com.example.api.dto.request.CreateTransferenciaRequest;
import com.example.api.dto.request.UpdateTransferenciaRequest;
import com.example.api.dto.response.TransferenciaResponse;
import com.example.api.exception.ResourceNotFoundException;
import com.example.api.infrastructure.TransferenciaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation for transfer operations.
 */
@Service
public class TransferenciaServiceImpl implements TransferenciaService {

    private final TransferenciaRepository repository;
    private final TransferenciaMapper mapper;

    public TransferenciaServiceImpl(TransferenciaRepository repository,
                                    TransferenciaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public TransferenciaResponse create(CreateTransferenciaRequest request) {
        Transferencia entity = new Transferencia(
                request.cuentaOrigenId(),
                request.cuentaDestinoId(),
                request.importe(),
                request.divisa(),
                request.concepto(),
                request.referenciaExterna()
        );
        Transferencia saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public TransferenciaResponse getById(Long id) {
        Transferencia entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Transfer not found with id: " + id));
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransferenciaResponse> getAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }

    @Override
    @Transactional
    public TransferenciaResponse update(Long id, UpdateTransferenciaRequest request) {
        Transferencia entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Transfer not found with id: " + id));
        entity.updateDetails(
                request.cuentaOrigenId(),
                request.cuentaDestinoId(),
                request.importe(),
                request.divisa(),
                request.concepto(),
                EstadoTransferencia.valueOf(request.estado()),
                request.fechaEjecucion(),
                request.referenciaExterna()
        );
        Transferencia updated = repository.save(entity);
        return mapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Transfer not found with id: " + id);
        }
        repository.deleteById(id);
    }
}
