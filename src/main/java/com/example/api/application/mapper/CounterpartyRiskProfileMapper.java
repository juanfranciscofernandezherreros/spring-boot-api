package com.example.api.application.mapper;

import com.example.api.domain.CounterpartyRiskProfile;
import com.example.api.dto.response.CounterpartyRiskProfileResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CounterpartyRiskProfileMapper {

    CounterpartyRiskProfileResponse toResponse(CounterpartyRiskProfile entity);
}
