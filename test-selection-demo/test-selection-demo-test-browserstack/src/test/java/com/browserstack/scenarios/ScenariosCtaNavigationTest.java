package com.browserstack.scenarios;
import com.browserstack.BaseUiTest;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ScenariosCtaNavigationTest extends BaseUiTest {

    @Test
    public void primaryCtaNavigatesToScenarios() {
        goHome();
        driver.findElement(By.id("primary-cta")).click();
        waitFor(10).until(d -> d.getCurrentUrl().contains("/scenarios"));
        Assert.assertTrue(driver.findElement(By.id("static-id-field")).isDisplayed());
    }
}
