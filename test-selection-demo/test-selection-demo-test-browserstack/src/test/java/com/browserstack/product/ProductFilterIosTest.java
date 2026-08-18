package com.browserstack.product;
import com.browserstack.BaseUiTest;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductFilterIosTest extends BaseUiTest {

    @Test
    public void categoryFilterLimitsResultsForIOS() {
        openProductsPage();
        Select categorySelect = new Select(driver.findElement(By.cssSelector("select")));
        categorySelect.selectByValue("ios");
        waitFor(5).until(ExpectedConditions.numberOfElementsToBe(By.id("product-card-1"), 0));
        int visibleCards = driver.findElements(By.cssSelector("[id^='product-card-']"))
            .size();
        Assert.assertEquals(visibleCards, 2, "Filtered catalog should show exactly two results");
    }
}
