package com.browserstack.checkout;

import com.browserstack.BaseUiTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckoutOrderHistoryTest extends BaseUiTest {

    @Test
    public void canPlaceOrderAndReachOrderHistory() {
        loginWithRandomDemoUser();
        addProductToCart(1);
        openCartPage();
        driver.findElement(By.id("checkout-btn")).click();
        waitFor(5).until(ExpectedConditions.visibilityOfElementLocated(By.id("place-order-btn"))).click();
        waitFor(10).until(ExpectedConditions.urlContains("/orders"));
        Assert.assertFalse(driver.findElements(By.cssSelector("li.order-list-item")).isEmpty());
    }
}
