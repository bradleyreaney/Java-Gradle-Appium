package org.example.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * Base page class that provides common functionality for all page objects.
 * This class handles page initialization and common page operations.
 */
public abstract class BasePage {

    protected static final Logger logger = LoggerFactory.getLogger(BasePage.class);
    protected AppiumDriver driver;
    protected WebDriverWait wait;

    /**
     * Constructor for BasePage
     * 
     * @param driver AppiumDriver instance
     */
    public BasePage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Initialize page elements using AppiumFieldDecorator
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);

        logger.debug("Initialized page: {}", this.getClass().getSimpleName());
    }

    /**
     * Get the current driver instance
     * 
     * @return AppiumDriver instance
     */
    protected AppiumDriver getDriver() {
        return driver;
    }

    /**
     * Get the WebDriverWait instance
     * 
     * @return WebDriverWait instance
     */
    protected WebDriverWait getWait() {
        return wait;
    }

    /**
     * Wait for a specific duration
     * 
     * @param seconds Number of seconds to wait
     */
    protected void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.warn("Wait was interrupted: {}", e.getMessage());
        }
    }

    /**
     * Check if an element is displayed
     * 
     * @param element The element to check
     * @return true if element is displayed, false otherwise
     */
    protected boolean isElementDisplayed(org.openqa.selenium.WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            logger.debug("Element not displayed: {}", e.getMessage());
            return false;
        }
    }
}
