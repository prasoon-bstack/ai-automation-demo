package com.browserstack.cart;

import com.browserstack.BaseUiTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CartBadgeDefaultTest extends BaseUiTest {

    @Test
    public void cartBadgeStartsAtZero() {
        goHome();
        String badgeValue = driver.findElement(By.cssSelector("#shopping-cart-btn span")).getText().trim();
        Assert.assertEquals(badgeValue, "0");
    }
}
