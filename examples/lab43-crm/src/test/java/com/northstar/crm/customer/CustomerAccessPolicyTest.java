package com.northstar.crm.customer;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.TestingAuthenticationToken;

class CustomerAccessPolicyTest {

    private final CustomerAccessPolicy policy = new CustomerAccessPolicy();

    @Test
    void adminCanReadAnyCustomer() {
        TestingAuthenticationToken authentication = new TestingAuthenticationToken("agent@example.test", "password", "ROLE_ADMIN");

        assertThat(policy.canReadCustomer(authentication, "CUS-1002")).isTrue();
    }

    @Test
    void customerCanReadOwnRecordCaseInsensitively() {
        TestingAuthenticationToken authentication = new TestingAuthenticationToken("Amina.Khan@Example.Test", "password", "ROLE_AGENT");

        assertThat(policy.canReadCustomer(authentication, "amina.khan@example.test")).isTrue();
    }

    @Test
    void anonymousCannotReadCustomer() {
        TestingAuthenticationToken authentication = new TestingAuthenticationToken("anonymous", "password");
        authentication.setAuthenticated(false);

        assertThat(policy.canReadCustomer(authentication, "CUS-1001")).isFalse();
    }
}
