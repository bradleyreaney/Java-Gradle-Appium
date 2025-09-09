package org.example.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * TestDataUtils class provides utility methods for loading and managing test
 * data.
 * This class supports loading test data from YAML and JSON files.
 */
public class TestDataUtils {

    private static final Logger logger = LoggerFactory.getLogger(TestDataUtils.class);
    private static final ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
    private static final ObjectMapper jsonMapper = new ObjectMapper();

    /**
     * Load test data from a YAML file
     * 
     * @param fileName Name of the YAML file (without extension)
     * @return Map containing the test data
     */
    public static Map<String, Object> loadTestDataFromYaml(String fileName) {
        try {
            // Try to load from external file first
            File configFile = new File("test-data/" + fileName + ".yml");
            if (configFile.exists()) {
                Map<String, Object> data = yamlMapper.readValue(configFile, Map.class);
                logger.info("Test data loaded from external file: {}", configFile.getAbsolutePath());
                return data;
            } else {
                // Load from resources
                InputStream inputStream = TestDataUtils.class.getClassLoader()
                        .getResourceAsStream("test-data/" + fileName + ".yml");
                if (inputStream != null) {
                    Map<String, Object> data = yamlMapper.readValue(inputStream, Map.class);
                    logger.info("Test data loaded from resources: {}", fileName);
                    return data;
                } else {
                    logger.warn("Test data file not found: {}", fileName);
                    return Map.of();
                }
            }
        } catch (IOException e) {
            logger.error("Failed to load test data from YAML file {}: {}", fileName, e.getMessage());
            return Map.of();
        }
    }

    /**
     * Load test data from a JSON file
     * 
     * @param fileName Name of the JSON file (without extension)
     * @return Map containing the test data
     */
    public static Map<String, Object> loadTestDataFromJson(String fileName) {
        try {
            // Try to load from external file first
            File configFile = new File("test-data/" + fileName + ".json");
            if (configFile.exists()) {
                Map<String, Object> data = jsonMapper.readValue(configFile, Map.class);
                logger.info("Test data loaded from external file: {}", configFile.getAbsolutePath());
                return data;
            } else {
                // Load from resources
                InputStream inputStream = TestDataUtils.class.getClassLoader()
                        .getResourceAsStream("test-data/" + fileName + ".json");
                if (inputStream != null) {
                    Map<String, Object> data = jsonMapper.readValue(inputStream, Map.class);
                    logger.info("Test data loaded from resources: {}", fileName);
                    return data;
                } else {
                    logger.warn("Test data file not found: {}", fileName);
                    return Map.of();
                }
            }
        } catch (IOException e) {
            logger.error("Failed to load test data from JSON file {}: {}", fileName, e.getMessage());
            return Map.of();
        }
    }

    /**
     * Get a specific value from test data
     * 
     * @param testData Map containing test data
     * @param key      Key to retrieve
     * @return Value associated with the key, or null if not found
     */
    public static Object getTestDataValue(Map<String, Object> testData, String key) {
        return testData.get(key);
    }

    /**
     * Get a specific value from test data with a default value
     * 
     * @param testData     Map containing test data
     * @param key          Key to retrieve
     * @param defaultValue Default value to return if key is not found
     * @return Value associated with the key, or default value if not found
     */
    public static Object getTestDataValue(Map<String, Object> testData, String key, Object defaultValue) {
        return testData.getOrDefault(key, defaultValue);
    }

    /**
     * Get a string value from test data
     * 
     * @param testData Map containing test data
     * @param key      Key to retrieve
     * @return String value associated with the key, or empty string if not found
     */
    public static String getStringValue(Map<String, Object> testData, String key) {
        Object value = testData.get(key);
        return value != null ? value.toString() : "";
    }

    /**
     * Get an integer value from test data
     * 
     * @param testData Map containing test data
     * @param key      Key to retrieve
     * @return Integer value associated with the key, or 0 if not found or invalid
     */
    public static int getIntValue(Map<String, Object> testData, String key) {
        try {
            Object value = testData.get(key);
            if (value instanceof Number) {
                return ((Number) value).intValue();
            } else if (value instanceof String) {
                return Integer.parseInt((String) value);
            }
        } catch (NumberFormatException e) {
            logger.warn("Could not parse integer value for key {}: {}", key, e.getMessage());
        }
        return 0;
    }

    /**
     * Get a boolean value from test data
     * 
     * @param testData Map containing test data
     * @param key      Key to retrieve
     * @return Boolean value associated with the key, or false if not found
     */
    public static boolean getBooleanValue(Map<String, Object> testData, String key) {
        Object value = testData.get(key);
        if (value instanceof Boolean) {
            return (Boolean) value;
        } else if (value instanceof String) {
            return Boolean.parseBoolean((String) value);
        }
        return false;
    }

    /**
     * Create test data directory if it doesn't exist
     */
    public static void createTestDataDirectory() {
        try {
            File testDataDir = new File("test-data");
            if (!testDataDir.exists()) {
                testDataDir.mkdirs();
                logger.info("Created test data directory: {}", testDataDir.getAbsolutePath());
            }
        } catch (Exception e) {
            logger.error("Failed to create test data directory: {}", e.getMessage());
        }
    }
}
