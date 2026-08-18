package com.browserstack.product;
import com.browserstack.BaseUiTest;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductCatalogDefaultLoadTest extends BaseUiTest {

    @Test
    public void catalogDefaultShowsAllProducts() {
        openProductsPage();
        waitFor(5).until(ExpectedConditions.numberOfElementsToBe(By.cssSelector("[id^='product-card-']"), 10));
        int totalCards = driver.findElements(By.cssSelector("[id^='product-card-']")).size();
        Assert.assertEquals(totalCards, 10, "Default catalog load should render 10 products");
    }
}
