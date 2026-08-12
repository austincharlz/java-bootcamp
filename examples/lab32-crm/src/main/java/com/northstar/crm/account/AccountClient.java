package com.northstar.crm.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class AccountClient {

    private static final Logger log = LoggerFactory.getLogger(AccountClient.class);
    private final RestClient restClient;

    public AccountClient(@Value("${account.api.base-url}") String baseUrl) {
        this.restClient = RestClient.builder().baseUrl(baseUrl).build();
    }

    public AccountSummary fetch(String customerId) {
        log.info("account_profile_fetch customerId={}", customerId);
        return restClient.get()
                .uri("/accounts/{customerId}/summary", customerId)
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                    throw new TemporaryAccountException(
                            "Account service returned " + res.getStatusCode() + " for " + customerId);
                })
                .body(AccountSummary.class);
    }
}