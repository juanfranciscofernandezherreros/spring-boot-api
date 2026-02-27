package com.example.api.controller;

import com.example.api.application.TransferenciaService;
import com.example.api.dto.response.TransferenciaResponse;
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

@WebMvcTest(TransferenciaController.class)
class TransferenciaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransferenciaService service;

    private static final String BASE_URL = "/api/v1/transferencias";
    private static final Long SAMPLE_ID = 1L;
    private static final Long NON_EXISTENT_ID = 999L;

    private TransferenciaResponse sampleResponse() {
        return new TransferenciaResponse(
                SAMPLE_ID, 100L, 200L, new BigDecimal("500.00"), "EUR",
                "Test payment", "PENDIENTE",
                Instant.parse("2024-01-01T12:00:00Z"), null, "REF-001");
    }

    @Test
    void create_shouldReturn201() throws Exception {
        when(service.create(any())).thenReturn(sampleResponse());

        Map<String, Object> request = Map.of(
                "cuentaOrigenId", 100,
                "cuentaDestinoId", 200,
                "importe", "500.00",
                "divisa", "EUR",
                "concepto", "Test payment",
                "referenciaExterna", "REF-001"
        );

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idTransferencia").value(SAMPLE_ID))
                .andExpect(jsonPath("$.cuentaOrigenId").value(100))
                .andExpect(jsonPath("$.cuentaDestinoId").value(200))
                .andExpect(jsonPath("$.divisa").value("EUR"));
    }

    @Test
    void create_shouldReturn400_whenValidationFails() throws Exception {
        Map<String, Object> request = Map.of(
                "cuentaOrigenId", 100
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
                .andExpect(jsonPath("$.idTransferencia").value(SAMPLE_ID))
                .andExpect(jsonPath("$.cuentaOrigenId").value(100));
    }

    @Test
    void getById_shouldReturn404_whenNotFound() throws Exception {
        when(service.getById(NON_EXISTENT_ID)).thenThrow(
                new ResourceNotFoundException("Transfer not found with id: " + NON_EXISTENT_ID));

        mockMvc.perform(get(BASE_URL + "/" + NON_EXISTENT_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void getAll_shouldReturn200() throws Exception {
        Page<TransferenciaResponse> page = new PageImpl<>(List.of(sampleResponse()));
        when(service.getAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].cuentaOrigenId").value(100));
    }

    @Test
    void update_shouldReturn200() throws Exception {
        when(service.update(eq(SAMPLE_ID), any())).thenReturn(sampleResponse());

        Map<String, Object> request = Map.of(
                "cuentaOrigenId", 100,
                "cuentaDestinoId", 200,
                "importe", "500.00",
                "divisa", "EUR",
                "concepto", "Test payment",
                "estado", "COMPLETADA",
                "referenciaExterna", "REF-001"
        );

        mockMvc.perform(put(BASE_URL + "/" + SAMPLE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idTransferencia").value(SAMPLE_ID));
    }

    @Test
    void update_shouldReturn404_whenNotFound() throws Exception {
        when(service.update(eq(NON_EXISTENT_ID), any())).thenThrow(
                new ResourceNotFoundException("Transfer not found with id: " + NON_EXISTENT_ID));

        Map<String, Object> request = Map.of(
                "cuentaOrigenId", 100,
                "cuentaDestinoId", 200,
                "importe", "500.00",
                "divisa", "EUR",
                "estado", "PENDIENTE"
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
        doThrow(new ResourceNotFoundException("Transfer not found with id: " + NON_EXISTENT_ID))
                .when(service).delete(NON_EXISTENT_ID);

        mockMvc.perform(delete(BASE_URL + "/" + NON_EXISTENT_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }
}
