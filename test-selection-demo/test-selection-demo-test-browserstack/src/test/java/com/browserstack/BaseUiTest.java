package com.browserstack;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Shared helpers for interacting with the BrowserStack demo storefront.
 */
public abstract class BaseUiTest extends SeleniumTest {

    private static final Random RANDOM = new Random();
    private static final String CONFIG_RESOURCE = "ui-test.properties";
    private static final Properties CONFIG = loadConfig();

    protected String getBaseUrl() {
        String fromProperty = System.getProperty("appUrl");
        if (fromProperty != null && !fromProperty.trim().isEmpty()) {
            return normalizeBaseUrl(fromProperty);
        }

        String fromConfig = CONFIG.getProperty("appUrl");
        if (fromConfig != null && !fromConfig.trim().isEmpty()) {
            return normalizeBaseUrl(fromConfig);
        }

        return "https://demo-app-sts.surge.sh/";
    }

    private static Properties loadConfig() {
        Properties props = new Properties();
        String externalPath = System.getProperty("uiTestConfig");
        if (externalPath != null && !externalPath.trim().isEmpty()) {
            try (InputStream in = Files.newInputStream(Paths.get(externalPath))) {
                props.load(in);
                return props;
            } catch (IOException ignored) {
                // Fall back to classpath resource if custom path fails
            }
        }

        try (InputStream in = BaseUiTest.class.getClassLoader().getResourceAsStream(CONFIG_RESOURCE)) {
            if (in != null) {
                props.load(in);
            }
        } catch (IOException ignored) {
            // Use defaults when resource is missing
        }
        return props;
    }

    private String normalizeBaseUrl(String raw) {
        if (raw.endsWith("/")) {
            return raw.substring(0, raw.length() - 1);
        }
        return raw;
    }

    protected void openPath(String path) {
        String normalized = path.startsWith("/") ? path : "/" + path;
        driver.get(getBaseUrl() + normalized);
        sleep(5000);
        // dismissNgrokInterstitialIfPresent();
    }

    protected WebDriverWait waitFor(long seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds));
    }

    protected void goHome() {
        openPath("/");
        waitFor(30).until(ExpectedConditions.visibilityOfElementLocated(By.id("hero-title")));
    }

    protected void openScenariosPage() {
        openPath("/scenarios");
        waitFor(30).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(),'Scenarios')]")));
    }

    protected void openLoginPage() {
        openPath("/login");
        waitFor(30).until(d -> d.getCurrentUrl().contains("/login") || d.getCurrentUrl().contains("/profile"));
    }

    protected void loginAs(String email, String password) {
        openLoginPage();
        WebDriverWait wait = waitFor(10);
        WebElement emailInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("email-input")));
        emailInput.clear();
        emailInput.sendKeys(email);
        WebElement passwordInput = driver.findElement(By.id("password-input"));
        passwordInput.clear();
        passwordInput.sendKeys(password);
        driver.findElement(By.id("login-submit")).click();
        wait.until(ExpectedConditions.urlContains("/products"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("shopping-cart-btn")));
    }

    protected void loginWithRandomDemoUser() {
        openLoginPage();
        Select select = getUserSelect();
        List<WebElement> choices = getSelectableUserOptions(select);
        if (choices.isEmpty()) {
            throw new IllegalStateException("No demo users found in the login dropdown");
        }
        String email = choices.get(RANDOM.nextInt(choices.size())).getAttribute("value");
        completeQuickSelectLogin(select, email);
    }

    protected void loginUsingQuickSelect(String email) {
        openLoginPage();
        Select select = getUserSelect();
        completeQuickSelectLogin(select, email);
    }

    protected String getFirstDemoUserEmail() {
        openLoginPage();
        List<WebElement> options = getSelectableUserOptions(getUserSelect());
        if (options.isEmpty()) {
            throw new IllegalStateException("No demo users found in the login dropdown");
        }
        return options.get(0).getAttribute("value");
    }

    protected void openProductsPage() {
        openPath("/products");
        waitFor(30).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[id^='product-card-']")));
    }

    protected void openProfilePage() {
        openPath("/profile");
        waitFor(30).until(d -> d.getCurrentUrl().contains("/profile") || d.getCurrentUrl().contains("/login"));
    }

    protected void openOrdersPage() {
        openPath("/orders");
        waitFor(30).until(d -> d.getCurrentUrl().contains("/orders") || d.getCurrentUrl().contains("/login"));
    }

    protected void addProductToCart(int productId) {
        addProductToCart(productId, 1);
    }

    protected void addProductToCart(int productId, int quantity) {
        openProductsPage();
        WebDriverWait wait = waitFor(10);
        WebElement card = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("product-card-" + productId)));
        WebElement quantityField = card.findElement(By.cssSelector("input[type='number']"));
        quantityField.clear();
        quantityField.sendKeys(String.valueOf(quantity));
        int before = getCartBadgeCount();
        WebElement addButton = card.findElement(By.id("add-to-cart-" + productId));
        wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.cssSelector("#shopping-cart-btn span"), String.valueOf(before + quantity)));
    }

    protected void addProductsToCart(int... productIds) {
        Arrays.stream(productIds).forEach(this::addProductToCart);
    }

    protected int getCartBadgeCount() {
        String text = driver.findElement(By.cssSelector("#shopping-cart-btn span")).getText().trim();
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    protected void openCartPage() {
        driver.findElement(By.id("shopping-cart-btn")).click();
        waitFor(10).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(),'Shopping Cart')]")));
    }

    private Select getUserSelect() {
        WebDriverWait wait = waitFor(10);
        WebElement selectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-select")));
        return new Select(selectElement);
    }

    private List<WebElement> getSelectableUserOptions(Select select) {
        return select.getOptions().stream()
            .filter(option -> {
                String value = option.getAttribute("value");
                return value != null && !value.trim().isEmpty();
            })
            .collect(Collectors.toList());
    }

    private void completeQuickSelectLogin(Select select, String email) {
        WebDriverWait wait = waitFor(10);
        select.selectByValue(email);
        wait.until(ExpectedConditions.attributeToBe(By.id("email-input"), "value", email));
        driver.findElement(By.id("login-submit")).click();
        wait.until(ExpectedConditions.urlContains("/products"));
    }

    protected void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }

    protected void scrollIntoView(WebElement element) {
        if (element == null) {
            return;
        }
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", element);
        sleep(200);
    }

    protected void clickWithRetry(WebElement element) {
        if (element == null) {
            return;
        }
        try {
            element.click();
        } catch (ElementClickInterceptedException ex) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }
}
