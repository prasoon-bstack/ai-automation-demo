package com.browserstack.orderhistory;
import com.browserstack.BaseUiTest;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OrderHistoryLatestEntryTest extends BaseUiTest {

    @Test
    public void latestOrderAppearsFirst() {
        loginWithRandomDemoUser();
        placeOrderForProduct(3);
        String firstOrderId = waitFor(5)
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li.order-list-item")))
                .getAttribute("data-order-id");
        placeOrderForProduct(4);
        String latestOrderId = waitFor(5)
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li.order-list-item")))
                .getAttribute("data-order-id");
        Assert.assertNotEquals(latestOrderId, firstOrderId);
    }

    private void placeOrderForProduct(int productId) {
        addProductToCart(productId);
        openCartPage();
        driver.findElement(By.id("checkout-btn")).click();
        waitFor(5).until(ExpectedConditions.elementToBeClickable(By.id("place-order-btn"))).click();
        waitFor(10).until(ExpectedConditions.urlContains("/orders"));
    }
}
