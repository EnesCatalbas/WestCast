package main.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MovieSearchSeleniumTest extends BaseTest {

    @Test
    void testSearchMovie() throws InterruptedException {
        driver.get("http://localhost:8080/search.html");

        driver.findElement(By.id("searchInput")).sendKeys("matrix");
        driver.findElement(By.id("searchBtn")).click();

        Thread.sleep(1000);

        String pageSource = driver.getPageSource();

        assertTrue(pageSource.toLowerCase().contains("matrix"));
    }

    @Test
    void testSearchMovieNotFound() throws InterruptedException {
        driver.get("http://localhost:8080/search.html");

        driver.findElement(By.id("searchInput")).sendKeys("olmayanfilm123");
        driver.findElement(By.id("searchBtn")).click();

        Thread.sleep(1000);

        String pageSource = driver.getPageSource();

        assertTrue(
                !pageSource.toLowerCase().contains("matrix"),
                "Olmayan film sonuçlarda görünmemeliydi"
        );
    }

}
