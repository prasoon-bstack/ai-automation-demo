package com.browserstack.scenarios;
import com.browserstack.BaseUiTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ScenarioNotificationToastTest extends BaseUiTest {

    @Test
    public void notificationButtonShowsToast() {
        openScenariosPage();
        WebElement submitButton = waitFor(5)
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[title='Submit']")));
        submitButton.click();
        WebElement toast = waitForToastWithTitle("Demo notification");
        Assert.assertTrue(toast.isDisplayed(), "Toast title should be visible");
    }

    private WebElement waitForToastWithTitle(String titleText) {
        return waitFor(5).until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class,'font-semibold') and contains(normalize-space(), '" + titleText + "')]")));
    }
}
