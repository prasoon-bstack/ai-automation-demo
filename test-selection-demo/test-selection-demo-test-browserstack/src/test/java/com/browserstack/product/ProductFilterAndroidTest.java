package com.browserstack.product;
import com.browserstack.BaseUiTest;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductFilterAndroidTest extends BaseUiTest {

    @Test
    public void categoryFilterLimitsResultsForAndroid() {
        openProductsPage();
        Select categorySelect = new Select(driver.findElement(By.cssSelector("select")));
        categorySelect.selectByValue("android");
        waitFor(5).until(ExpectedConditions.numberOfElementsToBe(By.id("product-card-2"), 0));
        int visibleCards = driver.findElements(By.cssSelector("[id^='product-card-']"))
            .size();
        Assert.assertEquals(visibleCards, 8, "Filtered catalog should show exactly 8 results");
    }
}
