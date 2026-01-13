package main.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class WatchlistSeleniumTest extends BaseTest {

    @Test
    void testAddToWatchListWithSelenium() {
        try {
            String testUser = "selUser" + System.currentTimeMillis();
            ApiTestHelper.signup(testUser, "1234");

            driver.get("http://localhost:8081/search.html");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement movieInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("movieName")));
            movieInput.sendKeys("Matrix");

            WebElement userInput = driver.findElement(By.id("userName"));
            userInput.sendKeys(testUser);

            WebElement addBtn = driver.findElement(By.id("addWatchlistBtn"));
            addBtn.click();

            WebElement resultMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("watchlistResult")));

            System.out.println("Gelen Mesaj: " + resultMsg.getText());

            assertTrue(resultMsg.getText().contains("Matrix"), "Sonuç mesajı film adını içermiyor!");

        } catch (Exception e) {
            e.printStackTrace();
            fail("Test bir hata nedeniyle başarısız oldu: " + e.getMessage());
        }
    }
}