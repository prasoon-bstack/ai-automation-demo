package com.browserstack.scenarios;
import com.browserstack.BaseUiTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ScenarioStaticIdInputTest extends BaseUiTest {

    @Test
    public void staticIdFieldAcceptsInput() {
        openScenariosPage();
        WebElement staticField = waitFor(5)
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("static-id-field")));
        staticField.clear();
        staticField.sendKeys("ORD-2025-001");
        Assert.assertEquals(staticField.getAttribute("value"), "ORD-2025-001");
    }
}
