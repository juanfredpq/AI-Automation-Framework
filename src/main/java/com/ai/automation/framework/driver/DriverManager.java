package com.ai.automation.framework.driver;

import org.openqa.selenium.WebDriver;

public final class DriverManager {

    private DriverManager() {
    }

    public static void initDriver(String browser) {
        DriverFactory.createDriver(browser);
    }

    public static WebDriver getDriver() {
        return DriverFactory.getDriver();
    }

    public static void quitDriver() {
        DriverFactory.quitDriver();
    }
}
