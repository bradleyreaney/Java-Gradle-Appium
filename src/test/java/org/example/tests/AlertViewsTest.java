package org.example.tests;

import org.example.base.BaseTest;
import org.example.pages.HomePage;
import org.example.pages.AlertViewsPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for AlertViews functionality.
 * This class contains test cases that verify the home page elements and
 * interactions.
 */
public class AlertViewsTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(AlertViewsTest.class);

    @Test
    @DisplayName("Verify alert views page is displayed correctly")
    public void testAlertViewsPageDisplayed() {
        logger.info("Starting test: Verify alert views page is displayed correctly");

        // Initialize home page
        HomePage homePage = new HomePage(getDriver());
        AlertViewsPage alertViewsPage = new AlertViewsPage(getDriver());

        // Wait for page to load
        homePage.waitForHomePageToLoad();

        // Click on alert views button
        homePage.clickAlertViews();

        // Verify alert views page is displayed
        assertThat(alertViewsPage.isAlertViewsPageDisplayed())
                .as("Alert views page should be displayed")
                .isTrue();

        alertViewsPage.

        logger.info("Test completed: Alert views page is displayed correctly");
    }

}
