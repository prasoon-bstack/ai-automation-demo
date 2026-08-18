package com.browserstack.cart;

import com.browserstack.BaseUiTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CartCheckoutVisibilityTest extends BaseUiTest {

    @Test
    public void checkoutButtonHiddenWhenLoggedOut() {
        addProductToCart(6);
        openCartPage();
        WebElement notice = waitFor(5).until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class,'text-yellow-800')]")));
        Assert.assertTrue(notice.getText().contains("Please"));
    }
}
