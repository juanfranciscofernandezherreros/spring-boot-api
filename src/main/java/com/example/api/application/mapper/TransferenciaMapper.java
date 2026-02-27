package com.example.api.application.mapper;

import com.example.api.domain.Transferencia;
import com.example.api.dto.response.TransferenciaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * MapStruct mapper for converting Transferencia entities to response DTOs.
 */
@Mapper(componentModel = "spring")
public interface TransferenciaMapper {

    @Mapping(target = "estado", expression = "java(entity.getEstado().name())")
    TransferenciaResponse toResponse(Transferencia entity);
}
