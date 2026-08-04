package com.northstar.crm.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;
import java.time.Duration;

/** Page Object — locate via data-testid only. */
public class CustomerFormPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    public CustomerFormPage(WebDriver d, WebDriverWait w) { driver = d; wait = w; }
    public CustomerFormPage open(String baseUrl) {
        driver.get(baseUrl + "/customers.html");
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-testid=customer-id]")));
        return this;
    }
    public CustomerFormPage fill(String id, String name, String status) {
        driver.findElement(By.cssSelector("[data-testid=customer-id]")).sendKeys(id);
        driver.findElement(By.cssSelector("[data-testid=full-name]")).sendKeys(name);
        new Select(driver.findElement(By.cssSelector("[data-testid=status]")))
                .selectByVisibleText(status);
        return this;
    }
    public void submit() {
        driver.findElement(By.cssSelector("[data-testid=submit]")).click();
    }
    public String resultText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-testid=result]"))).getText();
    }

}
