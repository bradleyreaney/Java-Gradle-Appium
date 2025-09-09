package org.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * ConfigManager class handles loading and managing configuration properties for
 * the automation suite.
 * It supports both YAML and JSON configuration files and provides a singleton
 * instance.
 */
public class ConfigManager {

    private static final Logger logger = LoggerFactory.getLogger(ConfigManager.class);
    private static ConfigManager instance;
    private AppiumConfig appiumConfig;

    private ConfigManager() {
        loadConfiguration();
    }

    /**
     * Get the singleton instance of ConfigManager
     * 
     * @return ConfigManager instance
     */
    public static synchronized ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    /**
     * Load configuration from YAML file
     */
    private void loadConfiguration() {
        try {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

            // Try to load from external file first
            File configFile = new File("config/appium-config.yml");
            if (configFile.exists()) {
                appiumConfig = mapper.readValue(configFile, AppiumConfig.class);
                logger.info("Configuration loaded from external file: {}", configFile.getAbsolutePath());
            } else {
                // Load from resources
                InputStream inputStream = getClass().getClassLoader().getResourceAsStream("appium-config.yml");
                if (inputStream != null) {
                    appiumConfig = mapper.readValue(inputStream, AppiumConfig.class);
                    logger.info("Configuration loaded from resources");
                } else {
                    // Use default configuration
                    appiumConfig = getDefaultConfig();
                    logger.info("Using default configuration");
                }
            }
        } catch (IOException e) {
            logger.error("Failed to load configuration, using defaults: {}", e.getMessage());
            appiumConfig = getDefaultConfig();
        }
    }

    /**
     * Get default configuration
     * 
     * @return AppiumConfig with default values
     */
    private AppiumConfig getDefaultConfig() {
        AppiumConfig config = new AppiumConfig();
        config.setAppiumServerUrl("http://localhost:4723");
        config.setDeviceName("iPhone 15");
        config.setPlatformVersion("17.0");
        config.setBundleId("com.example.app");
        config.setUdid("auto");
        config.setAppPath("");
        config.setNoReset(false);
        config.setFullReset(false);
        config.setImplicitWait(10);
        config.setExplicitWait(10);
        return config;
    }

    // Getters for configuration properties
    public String getAppiumServerUrl() {
        return appiumConfig.getAppiumServerUrl();
    }

    public String getDeviceName() {
        return appiumConfig.getDeviceName();
    }

    public String getPlatformVersion() {
        return appiumConfig.getPlatformVersion();
    }

    public String getBundleId() {
        return appiumConfig.getBundleId();
    }

    public String getUdid() {
        return appiumConfig.getUdid();
    }

    public String getAppPath() {
        return appiumConfig.getAppPath();
    }

    public boolean isNoReset() {
        return appiumConfig.isNoReset();
    }

    public boolean isFullReset() {
        return appiumConfig.isFullReset();
    }

    public int getImplicitWait() {
        return appiumConfig.getImplicitWait();
    }

    public int getExplicitWait() {
        return appiumConfig.getExplicitWait();
    }

    /**
     * Reload configuration from file
     */
    public void reloadConfiguration() {
        loadConfiguration();
    }
}
