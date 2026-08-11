package com.northstar.crm;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ErrorEnvelopeTest {
    private static final String CORRELATION_ID = "lab-request-001";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void validationReturns400Envelope() throws Exception {
        String token = loginAndGetToken();

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .header("X-Correlation-Id", CORRELATION_ID)
                        .content("""
                                {"id":"","name":"","email":"not-an-email","status":"ACTIVE"}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.correlationId").value(CORRELATION_ID))
                .andExpect(jsonPath("$.violations.length()").value(greaterThanOrEqualTo(1)))
                .andExpect(jsonPath("$.violations[?(@.field=='email')]").isNotEmpty());
    }

    @Test
    void missingCustomerReturns404Envelope() throws Exception {
        String token = loginAndGetToken();

        mockMvc.perform(get("/api/customers/CUS-9999")
                        .header("Authorization", "Bearer " + token)
                        .header("X-Correlation-Id", CORRELATION_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.correlationId").value(CORRELATION_ID))
                .andExpect(jsonPath("$.message").value(containsString("Customer not found")));
    }

    @Test
    void duplicateReturns409Envelope() throws Exception {
        String token = loginAndGetToken();

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .header("X-Correlation-Id", CORRELATION_ID)
                        .content("""
                                {"id":"CUS-1001","name":"Amina Khan","email":"amina@northstar.test","status":"ACTIVE"}
                                """))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.error").value("Conflict"))
                .andExpect(jsonPath("$.correlationId").value(CORRELATION_ID))
                .andExpect(jsonPath("$.message").value(containsString("Duplicate customer")));
    }

    @Test
    void securityStillRequiresToken() throws Exception {
        mockMvc.perform(get("/api/customers/CUS-1001"))
                .andExpect(status().isUnauthorized());
    }

    private String loginAndGetToken() throws Exception {
        MvcResult loginResult = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"username":"agent1","password":"agent1"}
                                """))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode body = objectMapper.readTree(loginResult.getResponse().getContentAsString());
        return body.get("accessToken").asText();
    }
}