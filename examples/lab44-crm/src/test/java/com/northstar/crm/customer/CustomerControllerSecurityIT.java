package com.northstar.crm.customer;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@WebMvcTest(CustomerController.class)
@Import({com.northstar.crm.config.SecurityConfig.class, CustomerAccessPolicy.class})
class CustomerControllerSecurityIT {

    @Autowired
    MockMvc mvc;

    @MockBean
    CustomerService service;

    @Test
    @WithMockUser(username = "CUS-1001", roles = "AGENT")
    void agentCanReadOwnCustomer() throws Exception {
        when(service.getByPublicId("CUS-1001")).thenReturn(new CustomerResponse(
                1L,
                "CUS-1001",
                "Amina Khan",
                "amina.khan@example.test",
                "ACTIVE",
                0L,
                java.time.Instant.parse("2026-08-20T14:00:00Z")
        ));

        mvc.perform(get("/api/customers/{publicId}", "CUS-1001").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.publicId").value("CUS-1001"));
    }

    @Test
    @WithMockUser(username = "CUS-1001", roles = "AGENT")
    void agentCannotReadAnotherAgentsCustomer() throws Exception {
        mvc.perform(get("/api/customers/{publicId}", "CUS-1002").with(csrf()))
                .andExpect(status().isForbidden());
        verifyNoInteractions(service);
    }
}
