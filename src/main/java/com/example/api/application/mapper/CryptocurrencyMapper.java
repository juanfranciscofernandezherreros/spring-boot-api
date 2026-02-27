package com.example.api.application.mapper;

import com.example.api.domain.Cryptocurrency;
import com.example.api.dto.response.CryptocurrencyResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CryptocurrencyMapper {

    CryptocurrencyResponse toResponse(Cryptocurrency entity);
}
