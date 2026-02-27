package com.example.api.cucumber;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class HelloStepDefinitions {

    @Autowired
    private MockMvc mockMvc;

    private MvcResult result;

    @When("the client calls GET {string}")
    public void theClientCallsGET(String url) throws Exception {
        result = mockMvc.perform(get(url)).andReturn();
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int statusCode) {
        assertEquals(statusCode, result.getResponse().getStatus());
    }

    @And("the response body should be {string}")
    public void theResponseBodyShouldBe(String body) throws Exception {
        assertEquals(body, result.getResponse().getContentAsString());
    }
}
