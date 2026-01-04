package main.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.*;

public class LoginSeleniumTest extends BaseTest {

    @Test
    void testLoginWithSelenium() throws Exception {

        ApiTestHelper.signup("seleniumUser", "1234");

        driver.get("http://localhost:8080/login.html");

        Thread.sleep(3000);

        driver.findElement(By.id("username")).sendKeys("seleniumUser");
        driver.findElement(By.id("password")).sendKeys("1234");

        Thread.sleep(1000);

        driver.findElement(By.id("loginBtn")).click();

        Thread.sleep(30000);

        String resultText =
                driver.findElement(By.id("result")).getText();

        assertTrue(
                resultText.contains("login is successful"),
                "Login sonucu ekranda görünmüyor! Result: " + resultText
        );
    }

    @Test
    void loginWithWrongPassword_shouldFail() {
        ApiTestHelper.signup("wrongPassUser2", "1234");

        driver.get("http://localhost:8080/login.html");

        driver.findElement(By.id("username")).sendKeys("wrongPassUser2");
        driver.findElement(By.id("password")).sendKeys("9999");
        driver.findElement(By.id("loginBtn")).click();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String pageSource = driver.getPageSource();

        assertFalse(
                pageSource.contains("login is successful"),
                "Yanlış şifreyle login başarılı olmamalıydı!"
        );
    }

    @Test
    void loginWithEmptyFields_shouldFail() {
        driver.get("http://localhost:8080/login.html");

        driver.findElement(By.id("loginBtn")).click();

        String page = driver.getPageSource();

        assertTrue(
                page.toLowerCase().contains("login"),
                "Boş alanlarla login başarısız olmalıydı"
        );
    }

}
