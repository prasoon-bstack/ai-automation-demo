package com.browserstack.scenarios;
import com.browserstack.BaseUiTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ScenarioNestedXpathInputTest extends BaseUiTest {

    @Test
    public void nestedXpathInputIsInteractable() {
        openScenariosPage();
        WebElement nestedInput = waitFor(5)
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("xpath-input")));
        nestedInput.sendKeys("Nested input text");
        Assert.assertEquals(nestedInput.getAttribute("value"), "Nested input text");
    }
}
