package com.browserstack.auth;

import com.browserstack.BaseUiTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LogoutClearsSessionTest extends BaseUiTest {

    @Test
    public void logoutFromProfileClearsSession() {
        loginWithRandomDemoUser();
        openProfilePage();
        waitFor(5).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Logout')]"))).click();
        WebElement loginHeader = waitFor(5).until(
            ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(),'Login') or contains(text(),'Welcome Back')]")));
        Assert.assertTrue(loginHeader.getText().contains("Login") || loginHeader.getText().contains("Welcome"));
    }
}
