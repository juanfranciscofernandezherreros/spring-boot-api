package com.example.api.application.mapper;

import com.example.api.domain.CounterpartyRiskProfile;
import com.example.api.dto.response.CounterpartyRiskProfileResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CounterpartyRiskProfileMapper {

    @Mapping(source = "counterpartyId", target = "counterpartyId")
    @Mapping(source = "legalName", target = "legalName")
    @Mapping(source = "countryCode", target = "countryCode")
    @Mapping(source = "creditRating", target = "creditRating")
    @Mapping(source = "riskScore", target = "riskScore")
    @Mapping(source = "exposureLimit", target = "exposureLimit")
    @Mapping(source = "createdAt", target = "createdAt")
    CounterpartyRiskProfileResponse toResponse(CounterpartyRiskProfile entity);
}
