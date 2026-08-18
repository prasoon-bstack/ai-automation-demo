package com.browserstack.product;
import com.browserstack.BaseUiTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductRemoveFromCartButtonTest extends BaseUiTest {

    @Test
    public void removeFromCartButtonAppearsForItems() {
        addProductToCart(2);
        WebElement removeButton = waitFor(5)
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("remove-from-cart-2")));
        int before = getCartBadgeCount();
        removeButton.click();
        waitFor(5).until(ExpectedConditions.textToBePresentInElementLocated(
                By.cssSelector("#shopping-cart-btn span"), String.valueOf(before - 1)));
        Assert.assertTrue(driver.findElements(By.id("remove-from-cart-2")).isEmpty());
    }
}
