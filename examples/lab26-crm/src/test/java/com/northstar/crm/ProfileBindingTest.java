package com.northstar.crm;

import com.northstar.crm.config.NorthstarIntegrationProperties;
import com.northstar.crm.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class ProfileBindingTest {

    @Autowired
    private NorthstarIntegrationProperties props;

    @Autowired
    private CustomerService customerService;

    @Test
    public void testProfileBindsTimeoutAndCustomerSeed() {
        // Test that @ConfigurationProperties binds correctly under test profile
        assertEquals(100, props.getConnectTimeoutMs(), "test profile should set connect-timeout-ms to 100");
        assertEquals("http://localhost:9090", props.getApiBaseUrl(), "api-base-url should match base application.yml");

        // Test that CustomerService has seeded customer
        var customer = customerService.get("CUS-1001");
        assertNotNull(customer, "CUS-1001 should exist");
        assertEquals("Amina Khan", customer.getName(), "CUS-1001 should be Amina Khan");
    }
}