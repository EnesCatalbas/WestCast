package main.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SignupSeleniumTest extends BaseTest {

    @Test
    void testSignupWithSelenium() {

        driver.get("http://localhost:8080/signup.html");

        driver.findElement(By.id("username")).sendKeys("seleniumUser2");
        driver.findElement(By.id("password")).sendKeys("1234");
        driver.findElement(By.id("signupBtn")).click();

        String pageSource = driver.getPageSource();

        assertTrue(
                pageSource.toLowerCase().contains("successful"),
                "Signup başarısız! Sayfa içeriği: " + pageSource
        );
    }

    @Test
    void signupWithExistingUser_shouldFail() {
        ApiTestHelper.signup("existingUser", "1234");

        driver.get("http://localhost:8080/signup.html");

        driver.findElement(By.id("username")).sendKeys("existingUser");
        driver.findElement(By.id("password")).sendKeys("1234");
        driver.findElement(By.id("signupBtn")).click();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String pageSource = driver.getPageSource();

        assertFalse(
                pageSource.contains("Signup successful"),
                "Aynı kullanıcıyla signup başarılı olmamalıydı!"
        );
    }


}
