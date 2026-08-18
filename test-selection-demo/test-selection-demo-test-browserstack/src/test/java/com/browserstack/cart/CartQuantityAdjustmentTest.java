package com.browserstack.cart;

import com.browserstack.BaseUiTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

public class CartQuantityAdjustmentTest extends BaseUiTest {

    @Test
    public void incrementAndDecrementButtonsUpdateQuantity() {
        addProductToCart(5);
        openCartPage();
        By quantityLocator = By.xpath("//div[@id='cart-item']//span[contains(@id,'item-quantity')]");
        WebElement plusButton = driver.findElement(By.xpath("//div[@id='cart-item']//button[text()='+']"));
        WebElement minusButton = driver.findElement(By.xpath("//div[@id='cart-item']//button[text()='-']"));
        plusButton.click();
        waitFor(5).until(ExpectedConditions.textToBe(quantityLocator, "2"));
        minusButton.click();
        waitFor(5).until(ExpectedConditions.textToBe(quantityLocator, "1"));
    }
}
