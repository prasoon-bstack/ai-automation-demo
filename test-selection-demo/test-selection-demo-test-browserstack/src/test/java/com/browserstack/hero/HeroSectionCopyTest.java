package com.browserstack.hero;
import com.browserstack.BaseUiTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HeroSectionCopyTest extends BaseUiTest {

    @Test
    public void heroSectionDisplaysExpectedCopy() {
        goHome();
        WebElement heroTitle = driver.findElement(By.id("hero-title"));
        WebElement heroSubtitle = driver.findElement(By.id("hero-subtitle"));
        Assert.assertTrue(heroTitle.getText().contains("Demo Playground"));
        Assert.assertTrue(heroSubtitle.getText().contains("BrowserStack-ready"));
    }
}
