package com.demo;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class BStackDemoTest {

    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(1280, 800));
    }

    @Test
    public void BStackDemoAIFlow() throws InterruptedException {
        driver.get("https://bstackdemo.com/");
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        new WebDriverWait(driver, Duration.ofSeconds(15))
            .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".shelf-container")));
        Thread.sleep(3000);
        jse.executeScript("browserstack_executor: {\"action\": \"ai\", \"arguments\": [\"Click on the Sign In button\"]}");
        jse.executeScript("browserstack_executor: {\"action\": \"ai\", \"arguments\": [\"Select demouser from the username dropdown\"]}");
        jse.executeScript("browserstack_executor: {\"action\": \"ai\", \"arguments\": [\"Select testingisfun99 from the password dropdown\"]}");
        jse.executeScript("browserstack_executor: {\"action\": \"ai\", \"arguments\": [\"Click the Log In button\"]}");
        jse.executeScript("browserstack_executor: {\"action\": \"ai\", \"arguments\": [\"Validate that the login was successful\"]}");
        jse.executeScript("browserstack_executor: {\"action\": \"ai\", \"arguments\": [\"Type Artificial Intelligence in the search box\"]}");
        jse.executeScript("browserstack_executor: {\"action\": \"ai\", \"arguments\": [\"Click on the Apple button in the Vendors section\"]}");
        // jse.executeScript("browserstack_executor: {\"action\": \"ai\", \"arguments\": [\"Check if Add to cart button is present on the screen\"]}");
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}