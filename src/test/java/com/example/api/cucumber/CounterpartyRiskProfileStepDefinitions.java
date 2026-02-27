package com.example.api.cucumber;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class CounterpartyRiskProfileStepDefinitions {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ScenarioContext scenarioContext;

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
        assertTrue(responseBody.contains("\"" + field + "\":\"" + value + "\""));
    }
}
