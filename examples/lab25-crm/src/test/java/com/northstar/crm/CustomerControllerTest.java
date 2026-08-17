package com.northstar.crm;

import com.northstar.crm.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerService customerService;

    @Test
    void createCustomerOverHttpAddsItToServiceList() throws Exception {
        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Correlation-Id", "lab-request-001")
                        .content("""
                                {
                                  "id":"CUS-1003",
                                  "name":"Maya Chen",
                                  "email":"maya@example.com",
                                  "status":"PROSPECT"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("CUS-1003"))
                .andExpect(jsonPath("$.name").value("Maya Chen"))
                .andExpect(jsonPath("$.email").value("maya@example.com"))
                .andExpect(jsonPath("$.status").value("PROSPECT"));

        assertTrue(customerService.list().stream().anyMatch(customer -> "CUS-1003".equals(customer.getId())));
    }
}
