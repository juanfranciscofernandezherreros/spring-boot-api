package com.example.api.controller;

import com.example.api.application.CounterpartyRiskProfileService;
import com.example.api.dto.response.CounterpartyRiskProfileResponse;
import com.example.api.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CounterpartyRiskProfileController.class)
class CounterpartyRiskProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CounterpartyRiskProfileService service;

    private static final String BASE_URL = "/api/v1/counterparty-risk-profiles";
    private static final UUID SAMPLE_ID = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private static final UUID NON_EXISTENT_ID = UUID.fromString("00000000-0000-0000-0000-000000000999");

    private CounterpartyRiskProfileResponse sampleResponse() {
        return new CounterpartyRiskProfileResponse(
                SAMPLE_ID, "Acme Corp", "USA", "AA+",
                new BigDecimal("85.50"), new BigDecimal("1000000.00"),
                Instant.parse("2024-01-01T12:00:00Z")
        );
    }

    @Test
    void create_shouldReturn201() throws Exception {
        when(service.create(any())).thenReturn(sampleResponse());

        Map<String, Object> request = Map.of(
                "legalName", "Acme Corp",
                "countryCode", "USA",
                "creditRating", "AA+",
                "riskScore", "85.50",
                "exposureLimit", "1000000.00"
        );

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.counterpartyId").value(SAMPLE_ID.toString()))
                .andExpect(jsonPath("$.legalName").value("Acme Corp"))
                .andExpect(jsonPath("$.countryCode").value("USA"));
    }

    @Test
    void create_shouldReturn400_whenValidationFails() throws Exception {
        Map<String, Object> request = Map.of(
                "countryCode", "USA"
        );

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void getById_shouldReturn200() throws Exception {
        when(service.getById(SAMPLE_ID)).thenReturn(sampleResponse());

        mockMvc.perform(get(BASE_URL + "/" + SAMPLE_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.counterpartyId").value(SAMPLE_ID.toString()))
                .andExpect(jsonPath("$.legalName").value("Acme Corp"));
    }

    @Test
    void getById_shouldReturn404_whenNotFound() throws Exception {
        when(service.getById(NON_EXISTENT_ID)).thenThrow(
                new ResourceNotFoundException("Counterparty risk profile not found with id: " + NON_EXISTENT_ID));

        mockMvc.perform(get(BASE_URL + "/" + NON_EXISTENT_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void getAll_shouldReturn200() throws Exception {
        Page<CounterpartyRiskProfileResponse> page = new PageImpl<>(List.of(sampleResponse()));
        when(service.getAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].legalName").value("Acme Corp"));
    }

    @Test
    void update_shouldReturn200() throws Exception {
        when(service.update(eq(SAMPLE_ID), any())).thenReturn(sampleResponse());

        Map<String, Object> request = Map.of(
                "legalName", "Acme Corp",
                "countryCode", "USA",
                "creditRating", "AA+",
                "riskScore", "85.50",
                "exposureLimit", "1000000.00"
        );

        mockMvc.perform(put(BASE_URL + "/" + SAMPLE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.counterpartyId").value(SAMPLE_ID.toString()));
    }

    @Test
    void update_shouldReturn404_whenNotFound() throws Exception {
        when(service.update(eq(NON_EXISTENT_ID), any())).thenThrow(
                new ResourceNotFoundException("Counterparty risk profile not found with id: " + NON_EXISTENT_ID));

        Map<String, Object> request = Map.of(
                "legalName", "Acme Corp",
                "countryCode", "USA"
        );

        mockMvc.perform(put(BASE_URL + "/" + NON_EXISTENT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void delete_shouldReturn204() throws Exception {
        doNothing().when(service).delete(SAMPLE_ID);

        mockMvc.perform(delete(BASE_URL + "/" + SAMPLE_ID))
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_shouldReturn404_whenNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Counterparty risk profile not found with id: " + NON_EXISTENT_ID))
                .when(service).delete(NON_EXISTENT_ID);

        mockMvc.perform(delete(BASE_URL + "/" + NON_EXISTENT_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }
}
