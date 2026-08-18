package org.browserstack.selfheal;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.urlContains;

public class SelfHealTest {

    private WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setup() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);
    }

    @Test(priority = 1)
    public void testLogin1() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Navigate to Browserstack Selfheal Demo Website
        driver.get("https://browserstack.github.io/selfheal-demo-app");

        // Check the title
        Assert.assertTrue(driver.getTitle().matches("browserstack-selfheal-demo"));

        // Click on "Try Demo Scenarios" button
        driver.findElement(By.id("cta-button")).click();
        Thread.sleep(2000);

        // Id Attribute Scenario
        driver.findElement(By.id("static-id-field"))
                .sendKeys("This is a test for static id field");

        // Xpath Scenario
        driver.findElement(By.xpath("//div[@id='xpath-form']/input"))
                .sendKeys("This is a test for xpath field");

        // Content Description Change Scenario Button
        driver.findElement(By.xpath("//button[@title='Submit']")).click();
        Thread.sleep(1000);

        // Text Change Scenario Button
        driver.findElement(By.xpath("//button[text()='Proceed']")).click();
        Thread.sleep(1000);

        // Class Name Change Scenario for Toggle Button
        driver.findElement(By.className("feature-toggle")).click();

        // Id change Scenario for progress bar
        Assert.assertEquals(driver.findElement(By.id("progress-status-id")).getText(), "Status: In Progress");
        driver.findElement(By.id("progress-btn-100")).click();
        Assert.assertEquals(driver.findElement(By.id("progress-status-id")).getText(), "Status: Complete");
    }

    @Test(priority = 2)
    public void testLogin2() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Navigate to Browserstack Selfheal Demo Website
        driver.get("https://browserstack.github.io/selfheal-demo-app");

        // Check the title
        Assert.assertTrue(driver.getTitle().matches("browserstack-selfheal-demo"));

        // Toggle the healing demo feature
        driver.findElement(By.id("self-heal-demo-toggle")).click();

        // Click on "Try Demo Scenarios" button
        driver.findElement(By.id("cta-button")).click();
        Thread.sleep(2000);

        // Id Attribute Scenario
        driver.findElement(By.id("static-id-field"))
                .sendKeys("This is a test for static id field");

        // Xpath Scenario
        driver.findElement(By.xpath("//div[@id='xpath-form']/input"))
                .sendKeys("This is a test for xpath field");

        // Content Description Change Scenario Button
        driver.findElement(By.xpath("//button[@title='Submit']")).click();
        Thread.sleep(1000);

        // Text Change Scenario Button
        driver.findElement(By.xpath("//button[text()='Proceed']")).click();
        Thread.sleep(1000);

        // Class Name Change Scenario for Toggle Button
        driver.findElement(By.className("feature-toggle")).click();

        // Id change Scenario for progress bar
        Assert.assertEquals(driver.findElement(By.id("progress-status-id")).getText(), "Status: In Progress");
        driver.findElement(By.id("progress-btn-100")).click();
        Assert.assertEquals(driver.findElement(By.id("progress-status-id")).getText(), "Status: Complete");
    }

    @AfterMethod(alwaysRun = true)
    public void closeDriver(ITestResult tr) {
        driver.quit();
    }

}