package com.northstar.crm.event;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {"crm.customer-events.v1", "crm.customer-events.v1.dlq"})
@TestPropertySource(properties = {
        "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}",
        "crm.kafka.customer-events-topic=crm.customer-events.v1"
})
class CustomerEventIntegrationTest {

    @Autowired
    private CustomerEventPublisher publisher;

    @Autowired
    private CustomerEventListener listener;

    @Autowired
    private ProcessedEventStore store;

    @Test
    void publishesAndConsumesCustomerCreated() {
        CustomerEvent event = buildEvent(UUID.randomUUID().toString(), "CUS-1001");

        publisher.publish(event);

        Awaitility.await().untilAsserted(() ->
                assertThat(listener.events())
                        .extracting(CustomerEvent::eventId)
                        .contains(event.eventId()));
        assertThat(store.markIfNew(event.eventId())).isFalse();
    }

    @Test
    void ignoresDuplicateEventByEventId() {
        String eventId = UUID.randomUUID().toString();
        CustomerEvent event = buildEvent(eventId, "CUS-1002");

        publisher.publish(event);
        publisher.publish(event);

        Awaitility.await().untilAsserted(() ->
                assertThat(listener.events().stream()
                        .filter(received -> eventId.equals(received.eventId()))
                        .count()).isEqualTo(1L));
    }

    private CustomerEvent buildEvent(String eventId, String customerId) {
        return new CustomerEvent(
                eventId,
                "CustomerCreated",
                1,
                Instant.now().truncatedTo(ChronoUnit.MILLIS),
                customerId,
                "lab-request-001",
                "integration-test",
                new CustomerEvent.CustomerData(customerId.equals("CUS-1001") ? "Amina Khan" : "Ravi Singh", "ACTIVE"));
    }
}