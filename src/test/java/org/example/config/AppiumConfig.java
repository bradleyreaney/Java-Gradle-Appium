package org.example.config;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Configuration class that maps to the YAML configuration file.
 * Contains all the necessary properties for Appium iOS automation.
 */
public class AppiumConfig {

    @JsonProperty("appium_server_url")
    private String appiumServerUrl;

    @JsonProperty("device_name")
    private String deviceName;

    @JsonProperty("platform_version")
    private String platformVersion;

    @JsonProperty("bundle_id")
    private String bundleId;

    @JsonProperty("udid")
    private String udid;

    @JsonProperty("app_path")
    private String appPath;

    @JsonProperty("no_reset")
    private boolean noReset;

    @JsonProperty("full_reset")
    private boolean fullReset;

    @JsonProperty("implicit_wait")
    private int implicitWait;

    @JsonProperty("explicit_wait")
    private int explicitWait;

    // Default constructor
    public AppiumConfig() {
    }

    // Getters and setters
    public String getAppiumServerUrl() {
        return appiumServerUrl;
    }

    public void setAppiumServerUrl(String appiumServerUrl) {
        this.appiumServerUrl = appiumServerUrl;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getPlatformVersion() {
        return platformVersion;
    }

    public void setPlatformVersion(String platformVersion) {
        this.platformVersion = platformVersion;
    }

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public String getAppPath() {
        return appPath;
    }

    public void setAppPath(String appPath) {
        this.appPath = appPath;
    }

    public boolean isNoReset() {
        return noReset;
    }

    public void setNoReset(boolean noReset) {
        this.noReset = noReset;
    }

    public boolean isFullReset() {
        return fullReset;
    }

    public void setFullReset(boolean fullReset) {
        this.fullReset = fullReset;
    }

    public int getImplicitWait() {
        return implicitWait;
    }

    public void setImplicitWait(int implicitWait) {
        this.implicitWait = implicitWait;
    }

    public int getExplicitWait() {
        return explicitWait;
    }

    public void setExplicitWait(int explicitWait) {
        this.explicitWait = explicitWait;
    }

    @Override
    public String toString() {
        return "AppiumConfig{" +
                "appiumServerUrl='" + appiumServerUrl + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", platformVersion='" + platformVersion + '\'' +
                ", bundleId='" + bundleId + '\'' +
                ", udid='" + udid + '\'' +
                ", appPath='" + appPath + '\'' +
                ", noReset=" + noReset +
                ", fullReset=" + fullReset +
                ", implicitWait=" + implicitWait +
                ", explicitWait=" + explicitWait +
                '}';
    }
}
