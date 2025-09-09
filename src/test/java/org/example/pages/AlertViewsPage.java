package org.example.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AlertViewsPage class represents the main/home screen of the iOS application.
 * This class contains all the elements and actions related to the home page.
 */
public class AlertViewsPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(AlertViewsPage.class);

    // Page elements using iOS XCUITest locators
    @iOSXCUITFindBy(className = "Alert Views")
    private WebElement alertViewsLabel;

    @iOSXCUITFindBy(accessibility = "Okay / Cancel")
    private WebElement okayCancelButton;

    @iOSXCUITFindBy(className = "A Short Title Is Best")
    private WebElement shortTitleAlert;

    @iOSXCUITFindBy(accessibility = "Cancel")
    private WebElement alertCancelOption;

    @iOSXCUITFindBy(accessibility = "OK")
    private WebElement alertOkayOption;

    /**
     * Constructor for AlertViewsPage
     * 
     * @param driver AppiumDriver instance
     */
    public AlertViewsPage(AppiumDriver driver) {
        super(driver);
        logger.info("AlertViewsPage initialized");
    }

    /**
     * Check if the alert views page is displayed
     * 
     * @return true if alert views page is displayed, false otherwise
     */
    public boolean isAlertViewsPageDisplayed() {
        logger.debug("Checking if alert views page is displayed");
        return isElementDisplayed(alertViewsLabel);
    }

    /**
     * Get the welcome text
     * 
     * @return Welcome text or empty string if not found
     */
    public String getAlertViewsText() {
        logger.debug("Getting alert views text");
        if (isElementDisplayed(alertViewsLabel)) {
            return alertViewsLabel.getText();
        }
        return "";
    }

    /**
     * Wait for the alert views page to load
     */
    public void waitForAlertViewsPageToLoad() {
        logger.info("Waiting for alert views page to load");
        waitForSeconds(2);
    }

    public void clickOkayCancelButton() {
        logger.info("Clicking Okan / Cancel button");
        if (isElementDisplayed(okayCancelButton)) {
            okayCancelButton.click();
        } else {
            logger.warn("Okay / Cancel button is not displayed");
        }
    }
}
