package org.example.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HomePage class represents the main/home screen of the iOS application.
 * This class contains all the elements and actions related to the home page.
 */
public class HomePage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(HomePage.class);

    // Page elements using iOS XCUITest locators
    @iOSXCUITFindBy(accessibility = "Welcome")
    private WebElement welcomeLabel;

    @iOSXCUITFindBy(accessibility = "Get Started")
    private WebElement getStartedButton;

    @iOSXCUITFindBy(accessibility = "Settings")
    private WebElement settingsButton;

    @iOSXCUITFindBy(accessibility = "Profile")
    private WebElement profileButton;

    @iOSXCUITFindBy(accessibility = "Search")
    private WebElement searchField;

    @iOSXCUITFindBy(accessibility = "Menu")
    private WebElement menuButton;

    @iOSXCUITFindBy(accessibility = "Alert Views")
    private WebElement alertViewsButton;

    /**
     * Constructor for HomePage
     * 
     * @param driver AppiumDriver instance
     */
    public HomePage(AppiumDriver driver) {
        super(driver);
        logger.info("HomePage initialized");
    }

    /**
     * Check if the home page is displayed
     * 
     * @return true if home page is displayed, false otherwise
     */
    public boolean isHomePageDisplayed() {
        logger.debug("Checking if home page is displayed");
        return isElementDisplayed(welcomeLabel);
    }

    /**
     * Click on the Get Started button
     */
    public void clickGetStarted() {
        logger.info("Clicking Get Started button");
        if (isElementDisplayed(getStartedButton)) {
            getStartedButton.click();
        } else {
            logger.warn("Get Started button is not displayed");
        }
    }

    /**
     * Click on the Settings button
     */
    public void clickSettings() {
        logger.info("Clicking Settings button");
        if (isElementDisplayed(settingsButton)) {
            settingsButton.click();
        } else {
            logger.warn("Settings button is not displayed");
        }
    }

    /**
     * Click on the Profile button
     */
    public void clickProfile() {
        logger.info("Clicking Profile button");
        if (isElementDisplayed(profileButton)) {
            profileButton.click();
        } else {
            logger.warn("Profile button is not displayed");
        }
    }

    /**
     * Enter text in the search field
     * 
     * @param searchText Text to enter in the search field
     */
    public void enterSearchText(String searchText) {
        logger.info("Entering search text: {}", searchText);
        if (isElementDisplayed(searchField)) {
            searchField.clear();
            searchField.sendKeys(searchText);
        } else {
            logger.warn("Search field is not displayed");
        }
    }

    /**
     * Click on the Menu button
     */
    public void clickMenu() {
        logger.info("Clicking Menu button");
        if (isElementDisplayed(menuButton)) {
            menuButton.click();
        } else {
            logger.warn("Menu button is not displayed");
        }
    }

    /**
     * Click on the Alert Views button
     */
    public void clickAlertViews() {
        logger.info("Clicking Alert Views button");
        if (isElementDisplayed(alertViewsButton)) {
            alertViewsButton.click();
        } else {
            logger.warn("Alert Views button is not displayed");
        }
    }

    /**
     * Get the welcome text
     * 
     * @return Welcome text or empty string if not found
     */
    public String getWelcomeText() {
        logger.debug("Getting welcome text");
        if (isElementDisplayed(welcomeLabel)) {
            return welcomeLabel.getText();
        }
        return "";
    }

    /**
     * Wait for the home page to load
     */
    public void waitForHomePageToLoad() {
        logger.info("Waiting for home page to load");
        waitForSeconds(2);
    }
}
