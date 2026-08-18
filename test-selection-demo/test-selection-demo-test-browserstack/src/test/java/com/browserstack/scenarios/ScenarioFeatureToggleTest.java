package com.browserstack.scenarios;
import com.browserstack.BaseUiTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ScenarioFeatureToggleTest extends BaseUiTest {

    @Test
    public void featureToggleChangesVisualState() {
        openScenariosPage();
        WebElement toggleButton = waitFor(5)
                .until(ExpectedConditions.elementToBeClickable(By.id("feature-toggle-btn")));
        String initialClass = toggleButton.getAttribute("class");
        toggleButton.click();
        waitFor(5).until(driver -> !toggleButton.getAttribute("class").equals(initialClass));
        Assert.assertTrue(toggleButton.getAttribute("class").contains("bg-blue-"));
    }
}
