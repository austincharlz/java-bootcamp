package com.northstar.crm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.xml.transform.StringSource;
import org.springframework.ws.test.server.MockWebServiceClient;

import javax.xml.transform.Source;

import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.noFault;
import static org.springframework.ws.test.server.ResponseMatchers.payload;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class CustomerEndpointTest {
    private static final String NAMESPACE_URI = "http://northstar.com/crm/customers";

    @Autowired
    private ApplicationContext applicationContext;

    private MockWebServiceClient client;

    @BeforeEach
    void setUp() {
        client = MockWebServiceClient.createClient(applicationContext);
    }

    @Test
    void getCustomerReturnsCus1001() throws Exception {
        Source request = new StringSource("""
                <ns:GetCustomerRequest xmlns:ns="%s">
                    <ns:customerId>CUS-1001</ns:customerId>
                </ns:GetCustomerRequest>
                """.formatted(NAMESPACE_URI));
        Source response = new StringSource("""
                <ns:GetCustomerResponse xmlns:ns="%s">
                    <ns:customerId>CUS-1001</ns:customerId>
                    <ns:name>Amina Khan</ns:name>
                    <ns:email>amina.khan@example.com</ns:email>
                    <ns:status>ACTIVE</ns:status>
                </ns:GetCustomerResponse>
                """.formatted(NAMESPACE_URI));

        client.sendRequest(withPayload(request))
                .andExpect(noFault())
                .andExpect(payload(response));
    }
}
