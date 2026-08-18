package com.browserstack.product;
import com.browserstack.BaseUiTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductMultiQuantityCartTest extends BaseUiTest {

    @Test
    public void addingMultipleQuantityPersistsInCart() {
        addProductToCart(3, 3);
        openCartPage();
        WebElement cartItem = waitFor(5).until(
                ExpectedConditions.visibilityOfElementLocated(By.id("cart-item")));
        Assert.assertTrue(cartItem.getText().contains("x 3"));
    }
}
