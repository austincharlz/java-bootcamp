package com.northstar.crm.ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.northstar.crm.ui.pages.CustomerFormPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UI smoke test for the minimal customers page.
 * Uses headless Chrome via WebDriverManager so CI doesn't need a checked-in driver.
 * For local headed debugging, remove the --headless=new option.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerUiIT {

    @LocalServerPort
    int port;

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        // Headless for CI; remove for local headed debugging
        options.addArguments("--headless=new", "--window-size=1280,900");
        driver = new ChromeDriver(options);

        // Prefer explicit waits; avoid implicit wait surprises by setting to 0
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void createCustomerViaUi() {
        String baseUrl = "http://localhost:" + port;
        CustomerFormPage page = new CustomerFormPage(driver, wait).open(baseUrl);
        page.fill("CUS-1002", "Ravi", "PROSPECT").submit();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.cssSelector("[data-testid=result]"), "CUS-1002"));
        String text = page.resultText();
        assertTrue(text.contains("CUS-1002") && text.contains("Ravi"), "Result should contain id and name");
    }

    @Test
    void blankNameShowsValidationMessage() {
        String baseUrl = "http://localhost:" + port;
        CustomerFormPage page = new CustomerFormPage(driver, wait).open(baseUrl);
        page.fill("CUS-1002", "", "PROSPECT").submit();
        wait.until(ExpectedConditions.or(
                ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("[data-testid=result]"), "full"),
                ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("[data-testid=result]"), "error")
        ));
        String res = page.resultText().toLowerCase();
        assertTrue(res.contains("full") || res.contains("error"), "Expected validation or error message");
    }

}
