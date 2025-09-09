package org.example.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.example.config.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

/**
 * DriverManager class handles the creation and management of Appium driver
 * instances.
 * This class ensures that only one driver instance exists at a time and
 * provides
 * proper cleanup functionality.
 */
public class DriverManager {

    private static final Logger logger = LoggerFactory.getLogger(DriverManager.class);
    private static AppiumDriver driver;
    private static final ThreadLocal<AppiumDriver> threadLocalDriver = new ThreadLocal<>();

    /**
     * Get the current driver instance. Creates a new one if none exists.
     * 
     * @return AppiumDriver instance
     * @throws MalformedURLException if the Appium server URL is malformed
     */
    public static AppiumDriver getDriver() throws MalformedURLException {
        if (threadLocalDriver.get() == null) {
            driver = createDriver();
            threadLocalDriver.set(driver);
        }
        return threadLocalDriver.get();
    }

    /**
     * Create a new iOS driver instance with the specified capabilities
     * 
     * @return AppiumDriver instance
     * @throws MalformedURLException if the Appium server URL is malformed
     */
    private static AppiumDriver createDriver() throws MalformedURLException {
        logger.info("Creating new iOS driver instance...");

        ConfigManager config = ConfigManager.getInstance();

        // Set up iOS capabilities
        XCUITestOptions options = new XCUITestOptions()
                .setDeviceName(config.getDeviceName())
                .setPlatformVersion(config.getPlatformVersion())
                .setBundleId(config.getBundleId())
                .setUdid(config.getUdid())
                .setAutomationName("XCUITest")
                .setNewCommandTimeout(Duration.ofSeconds(300))
                .setWdaLaunchTimeout(Duration.ofSeconds(60))
                .setWdaConnectionTimeout(Duration.ofSeconds(60));

        // Add additional capabilities if specified
        if (config.getAppPath() != null && !config.getAppPath().isEmpty()) {
            options.setApp(config.getAppPath());
        }

        if (config.isNoReset()) {
            options.setNoReset(true);
        }

        if (config.isFullReset()) {
            options.setFullReset(true);
        }

        // Create driver instance
        URL appiumServerUrl = new URL(config.getAppiumServerUrl());
        driver = new IOSDriver(appiumServerUrl, options);

        logger.info("iOS driver created successfully");
        return driver;
    }

    /**
     * Reset the driver instance (for cleanup)
     */
    public static void resetDriver() {
        if (threadLocalDriver.get() != null) {
            threadLocalDriver.remove();
        }
        driver = null;
        logger.info("Driver instance reset");
    }

    /**
     * Get the current driver instance without creating a new one
     * 
     * @return AppiumDriver instance or null if none exists
     */
    public static AppiumDriver getCurrentDriver() {
        return threadLocalDriver.get();
    }
}
