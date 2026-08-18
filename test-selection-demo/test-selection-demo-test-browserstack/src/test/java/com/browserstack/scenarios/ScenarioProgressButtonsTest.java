package com.browserstack.scenarios;
import com.browserstack.BaseUiTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ScenarioProgressButtonsTest extends BaseUiTest {

    @Test
    public void progressButtonsUpdateStatus() {
        openScenariosPage();
        driver.findElement(By.id("progress-btn-100"))
                .click();
        WebElement status = waitFor(5)
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("progress-status-id")));
        Assert.assertTrue(status.getText().contains("Complete"));

        driver.findElement(By.id("progress-btn-50"))
                .click();
        WebElement statusInProgress = waitFor(5)
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("progress-status-id")));
        Assert.assertTrue(statusInProgress.getText().contains("In Progress"));
    }
}
