package com.browserstack.profile;
import com.browserstack.BaseUiTest;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProfileLinkGuardTest extends BaseUiTest {

    @Test
    public void profileLinkRedirectsToLoginWhenLoggedOut() {
        goHome();
        driver.findElement(By.id("profile-btn")).click();
        waitFor(10).until(d -> d.getCurrentUrl().contains("/login"));
        Assert.assertEquals(driver.findElement(By.id("login-title")).getText(), "Welcome Back");
    }
}
