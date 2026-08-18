package com.browserstack.product;
import com.browserstack.BaseUiTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductSearchFilterTest extends BaseUiTest {

    @Test
    public void searchFiltersResults() {
        openProductsPage();
        WebElement searchInput = driver.findElement(By.cssSelector("input[placeholder='Search products...']"));
        searchInput.clear();
        searchInput.sendKeys("pixel");
        sleep(500);
        int visibleCards = driver.findElements(By.cssSelector("[id^='product-card-']"))
            .size();
        Assert.assertEquals(visibleCards, 1, "Filtered catalog should show exactly 1 result");
    }
}
