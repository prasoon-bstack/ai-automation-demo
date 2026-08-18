package com.browserstack.cart;

import com.browserstack.BaseUiTest;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

public class CartRemoveItemTest extends BaseUiTest {

    @Test
    public void removeItemLinkRemovesSingleItem() {
        addProductsToCart(7, 8);
        openCartPage();
        List<WebElement> beforeItems = driver.findElements(By.id("cart-item"));
        driver.findElement(By.xpath("(//div[@id='cart-item']//button[contains(text(),'Remove')])[1]")).click();
        waitFor(5).until(ExpectedConditions.numberOfElementsToBe(By.id("cart-item"), beforeItems.size() - 1));
    }
}
