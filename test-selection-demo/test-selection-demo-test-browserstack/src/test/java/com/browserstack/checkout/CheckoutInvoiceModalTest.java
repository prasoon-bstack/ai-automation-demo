package com.browserstack.checkout;

import com.browserstack.BaseUiTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckoutInvoiceModalTest extends BaseUiTest {

    @Test
    public void invoiceModalDisplaysLineItems() {
        loginWithRandomDemoUser();
        addProductToCart(2);
        openCartPage();
        driver.findElement(By.id("checkout-btn")).click();
        waitFor(5).until(ExpectedConditions.elementToBeClickable(By.id("place-order-btn"))).click();
        waitFor(10).until(ExpectedConditions.urlContains("/orders"));
        WebElement firstOrder = waitFor(5)
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li.order-list-item")));
        String orderId = firstOrder.getAttribute("data-order-id");
        driver.findElement(By.id("view-invoice-btn-" + orderId)).click();
        WebElement modalHeader = waitFor(5).until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Invoice for Order')]")));
        Assert.assertTrue(modalHeader.getText().contains(orderId));
        Assert.assertFalse(driver.findElements(By.xpath("//div[contains(@class,'shadow-lg')]//li"))
                .isEmpty());
    }
}
