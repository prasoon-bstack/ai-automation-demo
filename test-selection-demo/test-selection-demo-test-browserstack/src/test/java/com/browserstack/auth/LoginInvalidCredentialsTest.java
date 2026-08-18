package com.browserstack.auth;

import com.browserstack.BaseUiTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginInvalidCredentialsTest extends BaseUiTest {

    @Test
    public void loginFailsWithInvalidCredentials() {
        openLoginPage();
        driver.findElement(By.id("email-input")).sendKeys("fake@example.com");
        driver.findElement(By.id("password-input")).sendKeys("wrong-password");
        sleep(1000);
        driver.findElement(By.id("login-submit")).click();
        WebElement errorBanner = waitFor(5).until(
                ExpectedConditions.visibilityOfElementLocated(By.id("login-error")));
        Assert.assertTrue(errorBanner.getText().contains("Login failed"),
                "Inline login error banner should mention login failure");
        Assert.assertTrue(driver.getCurrentUrl().contains("/login"));
    }
}
