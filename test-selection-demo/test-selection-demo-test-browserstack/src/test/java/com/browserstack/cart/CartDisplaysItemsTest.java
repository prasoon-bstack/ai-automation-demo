package com.browserstack.cart;

import com.browserstack.BaseUiTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CartDisplaysItemsTest extends BaseUiTest {

    @Test
    public void cartDisplaysItemsAddedFromCatalog() {
        addProductToCart(4);
        openCartPage();
        WebElement name = waitFor(5).until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@id='cart-item']//div[contains(@class,'font-semibold')]")));
        Assert.assertTrue(name.getText().contains("Galaxy Note"));
    }
}
