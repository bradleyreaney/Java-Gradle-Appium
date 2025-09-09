package org.example.base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.example.config.ConfigManager;
import org.example.utils.DriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

/**
 * Base test class that provides common setup and teardown functionality for iOS
 * automation tests.
 * This class handles driver initialization, configuration loading, and cleanup.
 */
public abstract class BaseTest {

    protected static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
    protected AppiumDriver driver;
    protected ConfigManager configManager;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        logger.info("Setting up test environment...");

        // Load configuration
        configManager = ConfigManager.getInstance();

        // Initialize driver
        driver = DriverManager.getDriver();

        // Set implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        logger.info("Test environment setup completed");
    }

    @AfterEach
    public void tearDown() {
        logger.info("Tearing down test environment...");

        if (driver != null) {
            driver.quit();
            DriverManager.resetDriver();
        }

        logger.info("Test environment teardown completed");
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
     * Get the configuration manager instance
     * 
     * @return ConfigManager instance
     */
    protected ConfigManager getConfigManager() {
        return configManager;
    }
}
