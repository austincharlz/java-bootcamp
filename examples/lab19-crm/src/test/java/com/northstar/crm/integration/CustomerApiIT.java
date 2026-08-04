package com.northstar.crm.integration;

import com.northstar.crm.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerApiIT {
    @LocalServerPort int port;
    @Autowired TestRestTemplate rest;

    @Test
    void createAndGetCus1001() {
        var headers = new HttpHeaders();
        headers.set("X-Correlation-Id", "lab-request-001");
        headers.setContentType(MediaType.APPLICATION_JSON);
        var body = """
      {"customerId":"CUS-1001","fullName":"Amina Khan","status":"ACTIVE"}
      """;
        var created = rest.exchange(
                "http://localhost:" + port + "/api/customers",
                HttpMethod.POST, new HttpEntity<>(body, headers), Customer.class);
        assertEquals(HttpStatus.CREATED, created.getStatusCode());
        assertEquals("lab-request-001", created.getHeaders().getFirst("X-Correlation-Id"));

        var got = rest.getForEntity("http://localhost:" + port + "/api/customers/CUS-1001", Customer.class);
        assertEquals("CUS-1001", got.getBody().getCustomerId());
        assertEquals("lab-request-001", got.getHeaders().getFirst("X-Correlation-Id"));
    }

    @Test
    void getMissingReturns404() {
        var headers = new HttpHeaders();
        headers.set("X-Correlation-Id", "lab-request-001");
        var resp = rest.getForEntity("http://localhost:" + port + "/api/customers/CUS-9999", String.class);
        assertEquals(HttpStatus.NOT_FOUND, resp.getStatusCode());
        assertEquals("lab-request-001", resp.getHeaders().getFirst("X-Correlation-Id"));
    }

    @Test
    void invalidBodyReturns400() {
        var headers = new HttpHeaders();
        headers.set("X-Correlation-Id", "lab-request-001");
        headers.setContentType(MediaType.APPLICATION_JSON);
        var body = """
      {"fullName":"NoId","status":"ACTIVE"}
      """;
        var resp = rest.exchange("http://localhost:" + port + "/api/customers", HttpMethod.POST, new HttpEntity<>(body, headers), String.class);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        assertEquals("lab-request-001", resp.getHeaders().getFirst("X-Correlation-Id"));
    }
}
