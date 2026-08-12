package com.northstar.crm.account;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.junit.jupiter.api.*;

import java.time.Duration;
import java.util.concurrent.*;
import java.util.function.Supplier;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.assertj.core.api.Assertions.assertThat;

class AccountProfileResilienceTest {

    static WireMockServer wireMock;
    AccountClient client;
    CircuitBreaker circuitBreaker;
    TimeLimiter timeLimiter;
    ExecutorService executor;

    @BeforeAll
    static void startWireMock() {
        wireMock = new WireMockServer(options().dynamicPort());
        wireMock.start();
    }

    @AfterAll
    static void stopWireMock() {
        wireMock.stop();
    }

    @BeforeEach
    void setUp() {
        wireMock.resetAll();
        executor = Executors.newCachedThreadPool();
        client = new AccountClient("http://localhost:" + wireMock.port());

        // Tight window so circuit opens quickly in tests
        circuitBreaker = CircuitBreaker.of("accountProfile",
                CircuitBreakerConfig.custom()
                        .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                        .slidingWindowSize(6)
                        .minimumNumberOfCalls(4)
                        .failureRateThreshold(50)
                        .waitDurationInOpenState(Duration.ofSeconds(60)) // stays OPEN for test duration
                        .build());

        timeLimiter = TimeLimiter.of("accountProfile",
                TimeLimiterConfig.custom()
                        .timeoutDuration(Duration.ofMillis(1500))
                        .cancelRunningFuture(true)
                        .build());
    }

    @AfterEach
    void tearDown() {
        executor.shutdownNow();
    }

    /**
     * Chains CircuitBreaker (sync) → async → TimeLimiter, returns fallback on any exception.
     * CB wraps the synchronous client.fetch so failures are correctly recorded.
     */
    private AccountSummary callWithResilience(String customerId) {
        // CB decorates the synchronous call so failures are recorded when client.fetch throws
        Callable<AccountSummary> cbCallable = circuitBreaker.decorateCallable(
                () -> client.fetch(customerId));

        // Run async so TimeLimiter can interrupt a slow stub
        Supplier<Future<AccountSummary>> futureSupplier = () ->
                CompletableFuture.supplyAsync(() -> {
                    try {
                        return cbCallable.call();
                    } catch (RuntimeException e) {
                        throw e;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }, executor);

        Callable<AccountSummary> timedCallable = TimeLimiter.decorateFutureSupplier(timeLimiter, futureSupplier);
        try {
            return timedCallable.call();
        } catch (Exception e) {
            return AccountSummary.unavailable(customerId);
        }
    }

    @Test
    void healthyCall_returnsAvailable() {
        // Stub: CUS-1001 returns a healthy summary
        wireMock.stubFor(get("/accounts/CUS-1001/summary")
                .willReturn(okJson("{\"customerId\":\"CUS-1001\",\"available\":true,\"note\":\"ok\"}")));

        AccountSummary result = callWithResilience("CUS-1001");

        assertThat(result.available()).isTrue();
        assertThat(result.customerId()).isEqualTo("CUS-1001");
        assertThat(result.note()).isEqualTo("ok");
        assertThat(wireMock.getAllServeEvents()).hasSize(1);
    }

    @Test
    void openCircuit_failsFastWithoutHittingStub() {
        // Stub: permanent 503 to drive the circuit OPEN
        wireMock.stubFor(get("/accounts/CUS-1001/summary")
                .willReturn(aResponse().withStatus(503)));

        // Send 5 failing calls: exceeds minimum-number-of-calls=4 and failure-rate-threshold=50%
        for (int i = 0; i < 5; i++) {
            callWithResilience("CUS-1001");
        }
        assertThat(circuitBreaker.getState()).isEqualTo(CircuitBreaker.State.OPEN);

        int wireMockCallsBefore = wireMock.getAllServeEvents().size();

        // Calls while OPEN must fail fast — WireMock must receive no additional requests
        for (int i = 0; i < 3; i++) {
            AccountSummary degraded = callWithResilience("CUS-1001");
            assertThat(degraded.available()).isFalse();
            assertThat(degraded.note()).isEqualTo("account-profile-unavailable");
        }

        assertThat(wireMock.getAllServeEvents()).hasSize(wireMockCallsBefore);
    }

    @Test
    void timeout_returnsUnavailableFallback() {
        // Stub: 3000ms delay — well above the 1500ms TimeLimiter budget
        wireMock.stubFor(get("/accounts/CUS-1001/summary")
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"customerId\":\"CUS-1001\",\"available\":true,\"note\":\"ok\"}")
                        .withFixedDelay(3000)));

        long start = System.currentTimeMillis();
        AccountSummary result = callWithResilience("CUS-1001");
        long elapsed = System.currentTimeMillis() - start;

        assertThat(result.available()).isFalse();
        assertThat(result.note()).isEqualTo("account-profile-unavailable");
        // Must cut off well before the 3s stub delay
        assertThat(elapsed).isLessThan(2500);
    }
}