package com.example.api.cucumber;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class CounterpartyRiskProfileStepDefinitions {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ScenarioContext scenarioContext;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @When("the client sends a POST to {string} with body:")
    public void theClientSendsPostWithBody(String url, String body) throws Exception {
        scenarioContext.setLastResult(mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andReturn());
    }

    @And("the response body should contain {string} with value {string}")
    public void theResponseBodyShouldContainWithValue(String field, String value) throws Exception {
        String responseBody = scenarioContext.getLastResult().getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        assertEquals(value, jsonNode.get(field).asText());
    }
}
