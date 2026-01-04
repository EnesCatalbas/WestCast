package main.selenium;

import org.junit.jupiter.api.Test;

import static main.selenium.BaseTest.driver;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GeneralSeleniumTest extends BaseTest {
    @Test
    void publicPages_shouldBeAccessible() {
        driver.get("http://localhost:8080/login.html");
        assertFalse(driver.getPageSource().contains("403"));

        driver.get("http://localhost:8080/signup.html");
        assertFalse(driver.getPageSource().contains("403"));
    }

    @Test
    void applicationShouldBeUp() {
        driver.get("http://localhost:8080");

        String page = driver.getPageSource();

        assertTrue(
                page.length() > 20,
                "Uygulama ayakta olmalı ve boş sayfa dönmemeli"
        );
    }
}
