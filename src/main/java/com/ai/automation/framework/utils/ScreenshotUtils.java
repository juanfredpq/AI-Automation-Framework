package com.ai.automation.framework.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public final class ScreenshotUtils {

    private ScreenshotUtils() {
    }

    public static String captureScreenshot(WebDriver driver, String screenshotName, String reportPath) {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Path destination = Path.of(reportPath, screenshotName + ".png");
            Files.createDirectories(destination.getParent());
            Files.copy(screenshot.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
            return destination.toAbsolutePath().toString();
        } catch (IOException ex) {
            throw new RuntimeException("Unable to capture screenshot: " + screenshotName, ex);
        }
    }
}
