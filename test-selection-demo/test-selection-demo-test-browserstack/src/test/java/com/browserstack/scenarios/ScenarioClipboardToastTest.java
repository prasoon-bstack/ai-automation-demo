package com.browserstack.scenarios;
import com.browserstack.BaseUiTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ScenarioClipboardToastTest extends BaseUiTest {

    @Test
    public void clipboardButtonShowsLinkCopiedToast() {
        openScenariosPage();
        WebElement copyButton = waitFor(5)
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Copy share link')]")));
        copyButton.click();
        sleep(500);
        String copiedValue = driver.findElement(By.id("share-copy-result")).getText().trim();
        Assert.assertTrue(copiedValue.contains("https://demo-app.local/scenario"));
    }
}
