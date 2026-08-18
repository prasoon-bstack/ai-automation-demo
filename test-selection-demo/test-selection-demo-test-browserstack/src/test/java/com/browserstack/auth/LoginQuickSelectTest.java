package com.browserstack.auth;

import com.browserstack.BaseUiTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginQuickSelectTest extends BaseUiTest {

    @Test
    public void quickSelectUserCanLogin() {
        String email = getFirstDemoUserEmail();
        loginUsingQuickSelect(email);
        openProfilePage();
        waitFor(10).until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(@class,'text-xl')]")));
        WebElement profileEmail = driver.findElement(By.xpath("//div[contains(@class,'text-gray-600')][1]"));
        Assert.assertTrue(profileEmail.getText().contains(email));
    }
}
