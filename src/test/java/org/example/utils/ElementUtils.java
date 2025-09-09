package org.example.utils;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

/**
 * ElementUtils class provides common utility methods for interacting with
 * mobile elements.
 * This class contains methods for common operations like scrolling, swiping,
 * and element interactions.
 */
public class ElementUtils {

    private static final Logger logger = LoggerFactory.getLogger(ElementUtils.class);
    private final AppiumDriver driver;
    private final WebDriverWait wait;

    public ElementUtils(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Wait for an element to be visible
     * 
     * @param locator Element locator
     * @return WebElement if found and visible
     */
    public WebElement waitForElementVisible(By locator) {
        logger.debug("Waiting for element to be visible: {}", locator);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait for an element to be clickable
     * 
     * @param locator Element locator
     * @return WebElement if found and clickable
     */
    public WebElement waitForElementClickable(By locator) {
        logger.debug("Waiting for element to be clickable: {}", locator);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Check if an element is present
     * 
     * @param locator Element locator
     * @return true if element is present, false otherwise
     */
    public boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if an element is visible
     * 
     * @param locator Element locator
     * @return true if element is visible, false otherwise
     */
    public boolean isElementVisible(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Scroll down on the screen
     */
    public void scrollDown() {
        logger.debug("Scrolling down");
        Dimension size = driver.manage().window().getSize();
        int startX = size.width / 2;
        int startY = (int) (size.height * 0.8);
        int endY = (int) (size.height * 0.2);

        performSwipe(startX, startY, startX, endY);
    }

    /**
     * Scroll up on the screen
     */
    public void scrollUp() {
        logger.debug("Scrolling up");
        Dimension size = driver.manage().window().getSize();
        int startX = size.width / 2;
        int startY = (int) (size.height * 0.2);
        int endY = (int) (size.height * 0.8);

        performSwipe(startX, startY, startX, endY);
    }

    /**
     * Swipe left on the screen
     */
    public void swipeLeft() {
        logger.debug("Swiping left");
        Dimension size = driver.manage().window().getSize();
        int startX = (int) (size.width * 0.8);
        int endX = (int) (size.width * 0.2);
        int y = size.height / 2;

        performSwipe(startX, y, endX, y);
    }

    /**
     * Swipe right on the screen
     */
    public void swipeRight() {
        logger.debug("Swiping right");
        Dimension size = driver.manage().window().getSize();
        int startX = (int) (size.width * 0.2);
        int endX = (int) (size.width * 0.8);
        int y = size.height / 2;

        performSwipe(startX, y, endX, y);
    }

    /**
     * Tap on specific coordinates
     * 
     * @param x X coordinate
     * @param y Y coordinate
     */
    public void tap(int x, int y) {
        logger.debug("Tapping at coordinates: ({}, {})", x, y);
        performTap(x, y);
    }

    /**
     * Long press on specific coordinates
     * 
     * @param x        X coordinate
     * @param y        Y coordinate
     * @param duration Duration in milliseconds
     */
    public void longPress(int x, int y, int duration) {
        logger.debug("Long pressing at coordinates: ({}, {}) for {} ms", x, y, duration);
        performLongPress(x, y, duration);
    }

    /**
     * Find all elements matching the locator
     * 
     * @param locator Element locator
     * @return List of WebElements
     */
    public List<WebElement> findElements(By locator) {
        logger.debug("Finding elements with locator: {}", locator);
        return driver.findElements(locator);
    }

    /**
     * Get text from an element
     * 
     * @param locator Element locator
     * @return Element text or empty string if not found
     */
    public String getElementText(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            return element.getText();
        } catch (Exception e) {
            logger.warn("Could not get text from element: {}", locator);
            return "";
        }
    }

    /**
     * Clear and enter text in an element
     * 
     * @param locator Element locator
     * @param text    Text to enter
     */
    public void clearAndEnterText(By locator, String text) {
        logger.debug("Clearing and entering text '{}' in element: {}", text, locator);
        WebElement element = waitForElementVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Click on an element
     * 
     * @param locator Element locator
     */
    public void clickElement(By locator) {
        logger.debug("Clicking element: {}", locator);
        WebElement element = waitForElementClickable(locator);
        element.click();
    }

    /**
     * Perform a swipe gesture using Selenium 4 Actions API
     * 
     * @param startX Starting X coordinate
     * @param startY Starting Y coordinate
     * @param endX   Ending X coordinate
     * @param endY   Ending Y coordinate
     */
    private void performSwipe(int startX, int startY, int endX, int endY) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);

        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(1000), PointerInput.Origin.viewport(), endX, endY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(swipe));
    }

    /**
     * Perform a tap gesture using Selenium 4 Actions API
     * 
     * @param x X coordinate
     * @param y Y coordinate
     */
    private void performTap(int x, int y) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1);

        tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(tap));
    }

    /**
     * Perform a long press gesture using Selenium 4 Actions API
     * 
     * @param x        X coordinate
     * @param y        Y coordinate
     * @param duration Duration in milliseconds
     */
    private void performLongPress(int x, int y, int duration) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence longPress = new Sequence(finger, 1);

        longPress.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
        longPress.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        longPress
                .addAction(finger.createPointerMove(Duration.ofMillis(duration), PointerInput.Origin.viewport(), x, y));
        longPress.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(longPress));
    }
}
