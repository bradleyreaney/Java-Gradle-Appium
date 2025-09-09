package org.example.utils;

import io.appium.java_client.AppiumDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ScreenshotUtils class provides utility methods for taking and managing
 * screenshots.
 * This class handles screenshot capture, file naming, and directory management.
 */
public class ScreenshotUtils {

    private static final Logger logger = LoggerFactory.getLogger(ScreenshotUtils.class);
    private static final String SCREENSHOT_DIR = "screenshots";
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    private final AppiumDriver driver;

    public ScreenshotUtils(AppiumDriver driver) {
        this.driver = driver;
        createScreenshotDirectory();
    }

    /**
     * Create the screenshots directory if it doesn't exist
     */
    private void createScreenshotDirectory() {
        try {
            Path screenshotPath = Paths.get(SCREENSHOT_DIR);
            if (!Files.exists(screenshotPath)) {
                Files.createDirectories(screenshotPath);
                logger.info("Created screenshots directory: {}", screenshotPath.toAbsolutePath());
            }
        } catch (IOException e) {
            logger.error("Failed to create screenshots directory: {}", e.getMessage());
        }
    }

    /**
     * Take a screenshot with a default filename
     * 
     * @return Path to the saved screenshot file
     */
    public String takeScreenshot() {
        return takeScreenshot("screenshot");
    }

    /**
     * Take a screenshot with a custom filename
     * 
     * @param filename Base filename (without extension)
     * @return Path to the saved screenshot file
     */
    public String takeScreenshot(String filename) {
        try {
            String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMATTER);
            String fullFilename = String.format("%s_%s.png", filename, timestamp);
            String filePath = Paths.get(SCREENSHOT_DIR, fullFilename).toString();

            // Take screenshot using Appium driver
            File screenshotFile = driver.getScreenshotAs(org.openqa.selenium.OutputType.FILE);

            // Copy to our desired location
            Files.copy(screenshotFile.toPath(), Paths.get(filePath));

            logger.info("Screenshot saved: {}", filePath);
            return filePath;

        } catch (IOException e) {
            logger.error("Failed to take screenshot: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Take a screenshot for test failure
     * 
     * @param testName Name of the test that failed
     * @return Path to the saved screenshot file
     */
    public String takeFailureScreenshot(String testName) {
        String filename = String.format("FAILED_%s", testName.replaceAll("[^a-zA-Z0-9]", "_"));
        return takeScreenshot(filename);
    }

    /**
     * Take a screenshot for test success
     * 
     * @param testName Name of the test that passed
     * @return Path to the saved screenshot file
     */
    public String takeSuccessScreenshot(String testName) {
        String filename = String.format("PASSED_%s", testName.replaceAll("[^a-zA-Z0-9]", "_"));
        return takeScreenshot(filename);
    }

    /**
     * Take a screenshot at a specific step
     * 
     * @param testName Name of the test
     * @param stepName Name of the step
     * @return Path to the saved screenshot file
     */
    public String takeStepScreenshot(String testName, String stepName) {
        String filename = String.format("STEP_%s_%s",
                testName.replaceAll("[^a-zA-Z0-9]", "_"),
                stepName.replaceAll("[^a-zA-Z0-9]", "_"));
        return takeScreenshot(filename);
    }

    /**
     * Get the screenshots directory path
     * 
     * @return Path to the screenshots directory
     */
    public String getScreenshotDirectory() {
        return Paths.get(SCREENSHOT_DIR).toAbsolutePath().toString();
    }

    /**
     * Clean up old screenshots (older than specified days)
     * 
     * @param daysToKeep Number of days to keep screenshots
     */
    public void cleanupOldScreenshots(int daysToKeep) {
        try {
            Path screenshotPath = Paths.get(SCREENSHOT_DIR);
            if (!Files.exists(screenshotPath)) {
                return;
            }

            long cutoffTime = System.currentTimeMillis() - (daysToKeep * 24L * 60L * 60L * 1000L);

            Files.walk(screenshotPath)
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".png"))
                    .filter(path -> {
                        try {
                            return Files.getLastModifiedTime(path).toMillis() < cutoffTime;
                        } catch (IOException e) {
                            return false;
                        }
                    })
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                            logger.info("Deleted old screenshot: {}", path.getFileName());
                        } catch (IOException e) {
                            logger.warn("Failed to delete old screenshot: {}", path.getFileName());
                        }
                    });

        } catch (IOException e) {
            logger.error("Failed to cleanup old screenshots: {}", e.getMessage());
        }
    }
}
