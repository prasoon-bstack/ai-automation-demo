package com.browserstack.cart;

import com.browserstack.BaseUiTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CartClearAllTest extends BaseUiTest {

    @Test
    public void clearCartButtonRemovesAllItems() {
        addProductsToCart(1, 2);
        openCartPage();
        WebElement clearButton = waitFor(5).until(ExpectedConditions.elementToBeClickable(By.id("clear-cart-btn")));
        clearButton.click();
        WebElement emptyMessage = waitFor(5)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='Your cart is empty.']")));
        Assert.assertTrue(emptyMessage.isDisplayed());
    }
}
