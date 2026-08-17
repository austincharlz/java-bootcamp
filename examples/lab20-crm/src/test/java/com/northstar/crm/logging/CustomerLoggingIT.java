package com.northstar.crm.logging;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CustomerLoggingIT {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate rest;

    private ListAppender<ILoggingEvent> appender;
    private PatternLayout layout;
    private Logger logger;

    @AfterEach
    void tearDown() {
        if (logger != null && appender != null) {
            logger.detachAppender(appender);
        }
        if (appender != null) {
            appender.stop();
        }
        if (layout != null) {
            layout.stop();
        }
    }

    @Test
    void logsStructuredMdcAndAvoidsPii() {
        logger = (Logger) LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

        layout = new PatternLayout();
        layout.setPattern("%d{ISO8601} %-5level [%thread] %logger{36} corr=%X{corr} cust=%X{cust} op=%X{op} - %msg%n");
        layout.setContext(context);
        layout.start();

        appender = new ListAppender<>();
        appender.setContext(context);
        appender.start();
        logger.addAppender(appender);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Correlation-Id", "lab-request-001");
        headers.setContentType(MediaType.APPLICATION_JSON);

        String createBody = """
                {"customerId":"CUS-3001","fullName":"Amina Khan","status":"ACTIVE"}
                """;
        ResponseEntity<String> created = rest.exchange(
                "http://localhost:" + port + "/api/customers",
                HttpMethod.POST,
                new HttpEntity<>(createBody, headers),
                String.class);
        assertEquals(HttpStatus.CREATED, created.getStatusCode());

        ResponseEntity<String> fetched = rest.exchange(
                "http://localhost:" + port + "/api/customers/CUS-3001",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class);
        assertEquals(HttpStatus.OK, fetched.getStatusCode());

        String invalidBody = """
                {"customerId":"CUS-3002","fullName":"","status":"PROSPECT"}
                """;
        ResponseEntity<String> rejected = rest.exchange(
                "http://localhost:" + port + "/api/customers",
                HttpMethod.POST,
                new HttpEntity<>(invalidBody, headers),
                String.class);
        assertEquals(HttpStatus.BAD_REQUEST, rejected.getStatusCode());

        List<String> lines = appender.list.stream()
                .map(layout::doLayout)
                .toList();
        String output = String.join("\n", lines);

        assertTrue(output.contains("corr=lab-request-001"));
        assertTrue(output.contains("cust=CUS-3001"));
        assertTrue(output.contains("op=create"));
        assertTrue(output.contains("op=get"));
        assertTrue(output.contains("reason=missing_full_name"));
        assertFalse(output.contains("Amina"));
        assertFalse(output.contains("amina"));
        assertFalse(output.contains("email"));
        assertFalse(output.contains("phone"));
    }
}
