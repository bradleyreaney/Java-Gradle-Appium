# Mobile Automation Example

A comprehensive mobile automation suite built with Java, Gradle, Appium, and iOS support.

## Overview

This project provides a robust foundation for iOS mobile automation testing using Appium. It includes:

- **Page Object Model** implementation for maintainable test code
- **Configuration management** with YAML support
- **Utility classes** for common operations
- **Screenshot capabilities** for test reporting
- **Test data management** with external file support
- **Comprehensive logging** with Logback

## Prerequisites

Before running the tests, ensure you have the following installed:

1. **Java 11 or higher**
2. **Gradle 7.0 or higher**
3. **Appium Server** (latest version)
4. **Xcode** (for iOS development)
5. **iOS Simulator** or physical iOS device
6. **WebDriverAgent** (for iOS automation)

## Project Structure

```
src/
├── main/
│   └── java/
│       └── org/example/
│           └── Main.java
└── test/
    ├── java/
    │   └── org/example/
    │       ├── base/
    │       │   └── BaseTest.java
    │       ├── config/
    │       │   ├── ConfigManager.java
    │       │   └── AppiumConfig.java
    │       ├── pages/
    │       │   ├── BasePage.java
    │       │   ├── HomePage.java
    │       │   └── SettingsPage.java
    │       ├── tests/
    │       │   ├── HomePageTest.java
    │       │   └── SettingsPageTest.java
    │       └── utils/
    │           ├── DriverManager.java
    │           ├── ElementUtils.java
    │           ├── ScreenshotUtils.java
    │           └── TestDataUtils.java
    └── resources/
        ├── appium-config.yml
        ├── logback.xml
        └── test-data/
            └── sample-test-data.yml
```

## Configuration

### Appium Configuration

The project uses YAML configuration files for Appium settings. You can configure the following:

- **Appium Server URL**: Default is `http://localhost:4723`
- **Device Settings**: Device name, platform version, UDID
- **App Settings**: Bundle ID, app path, reset options
- **Wait Timeouts**: Implicit and explicit wait times

#### Configuration Files

1. **Default Configuration**: `src/test/resources/appium-config.yml`
2. **External Configuration**: `config/appium-config.yml` (overrides default)

### Example Configuration

```yaml
appium_server_url: "http://localhost:4723"
device_name: "iPhone 15"
platform_version: "17.0"
udid: "auto"
bundle_id: "com.example.app"
app_path: ""
no_reset: false
full_reset: false
implicit_wait: 10
explicit_wait: 10
```

## Running Tests

### Prerequisites Setup

1. **Start Appium Server**:
   ```bash
   appium
   ```

2. **Start iOS Simulator** or connect physical device

3. **Build the project**:
   ```bash
   ./gradlew build
   ```

### Running All Tests

```bash
./gradlew test
```

### Running Specific Test Classes

```bash
./gradlew test --tests "org.example.tests.HomePageTest"
./gradlew test --tests "org.example.tests.SettingsPageTest"
```

### Running Tests with Specific Configuration

```bash
./gradlew test -Dappium.server.url=http://localhost:4723
```

## Test Data Management

The project supports external test data management through YAML and JSON files:

- **Location**: `src/test/resources/test-data/` or `test-data/` in project root
- **Format**: YAML or JSON
- **Usage**: Load test data using `TestDataUtils` class

### Example Test Data

```yaml
user:
  username: "testuser@example.com"
  password: "testpassword123"
  
scenarios:
  login:
    validCredentials:
      username: "validuser@example.com"
      password: "validpassword123"
```

## Screenshots

The project automatically captures screenshots during test execution:

- **Location**: `screenshots/` directory
- **Naming**: Timestamped filenames with test context
- **Types**: Success, failure, and step screenshots

## Logging

Comprehensive logging is provided using Logback:

- **Console Output**: Real-time test execution logs
- **File Output**: Detailed logs saved to `logs/automation.log`
- **Log Levels**: Configurable per component

## Page Object Model

The project implements the Page Object Model pattern:

- **BasePage**: Common functionality for all pages
- **Page Classes**: Specific page implementations (HomePage, SettingsPage)
- **Element Locators**: iOS XCUITest locators using accessibility IDs

### Example Page Object

```java
public class HomePage extends BasePage {
    @iOSXCUITFindBy(accessibility = "Welcome")
    private WebElement welcomeLabel;
    
    public boolean isHomePageDisplayed() {
        return isElementDisplayed(welcomeLabel);
    }
}
```

## Utility Classes

### ElementUtils

Provides common element interactions:
- Element waiting and visibility checks
- Scrolling and swiping operations
- Touch actions (tap, long press)

### ScreenshotUtils

Handles screenshot capture and management:
- Automatic screenshot naming
- Test failure screenshots
- Screenshot cleanup

### TestDataUtils

Manages test data loading:
- YAML and JSON support
- External file override capability
- Type-safe data retrieval

## Best Practices

1. **Use Page Object Model**: Keep page logic separate from test logic
2. **External Configuration**: Use external config files for different environments
3. **Proper Wait Strategies**: Use explicit waits instead of hard-coded delays
4. **Screenshot on Failure**: Always capture screenshots when tests fail
5. **Logging**: Use appropriate log levels for debugging
6. **Test Data**: Keep test data external and maintainable

## Troubleshooting

### Common Issues

1. **Appium Server Not Running**:
   - Ensure Appium server is started on the correct port
   - Check server logs for connection issues

2. **Device Not Found**:
   - Verify iOS Simulator is running
   - Check device UDID in configuration
   - Ensure WebDriverAgent is properly installed

3. **Element Not Found**:
   - Verify accessibility IDs in the app
   - Check element visibility and timing
   - Use proper wait strategies

4. **Build Issues**:
   - Ensure Java 11+ is installed
   - Check Gradle version compatibility
   - Verify all dependencies are resolved

## Contributing

1. Follow the existing code structure and patterns
2. Add proper logging and documentation
3. Include test cases for new functionality
4. Update configuration files as needed

## License

This project is provided as an example for educational purposes.
