package com.browserstack.auth;

import com.browserstack.BaseUiTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginPasswordToggleTest extends BaseUiTest {

    @Test
    public void showPasswordToggleChangesInputType() {
        openLoginPage();
        WebElement passwordField = driver.findElement(By.id("password-input"));
        Assert.assertEquals(passwordField.getAttribute("type"), "password");
        driver.findElement(By.id("show-password")).click();
        Assert.assertEquals(passwordField.getAttribute("type"), "text");
    }
}
