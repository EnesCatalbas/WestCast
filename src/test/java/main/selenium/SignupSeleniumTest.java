package main.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SignupSeleniumTest extends BaseTest {

    @Test
    void testSignupWithSelenium() {
        driver.get("http://localhost:8081/signup.html");

        // Benzersiz kullanıcı adı oluşturma (Timestamp ile)
        String uniqueUser = "user_" + System.currentTimeMillis();

        driver.findElement(By.id("username")).sendKeys(uniqueUser);
        driver.findElement(By.id("password")).sendKeys("1234");
        driver.findElement(By.id("signupBtn")).click();

        // ⏳ Sayfanın değişmesini veya içeriğin gelmesini bekle (En önemli kısım)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        String pageSource = driver.getPageSource();

        assertTrue(
                pageSource.toLowerCase().contains("successful"),
                "Signup başarısız! Sayfa içeriği: " + pageSource
        );
    }

    @Test
    void signupWithExistingUser_shouldFail() {
        // Önce bir kullanıcı oluştur
        String existingUser = "fixedUser_" + System.currentTimeMillis();
        driver.get("http://localhost:8081/signup.html");
        driver.findElement(By.id("username")).sendKeys(existingUser);
        driver.findElement(By.id("password")).sendKeys("1234");
        driver.findElement(By.id("signupBtn")).click();

        // Tekrar aynı kullanıcı ile dene
        driver.get("http://localhost:8081/signup.html");
        driver.findElement(By.id("username")).sendKeys(existingUser);
        driver.findElement(By.id("password")).sendKeys("1234");
        driver.findElement(By.id("signupBtn")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "already exists"));

        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("already exists"), "Hata mesajı görünmedi!");
    }
}