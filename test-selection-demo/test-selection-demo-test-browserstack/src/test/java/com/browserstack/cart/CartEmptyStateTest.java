package com.browserstack.cart;

import com.browserstack.BaseUiTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CartEmptyStateTest extends BaseUiTest {

    @Test
    public void cartShowsEmptyStateForEmptyCart() {
        loginWithRandomDemoUser();
        openCartPage();
        WebElement empty = waitFor(5).until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Your cart is empty.')]")));
        Assert.assertTrue(empty.isDisplayed());
    }
}
