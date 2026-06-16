package com.ai.automation.framework.factory;

import com.ai.automation.framework.base.BasePage;
import org.openqa.selenium.WebDriver;

public final class PageFactoryManager {

    private PageFactoryManager() {
    }

    public static <T extends BasePage> T getPage(Class<T> pageClass, WebDriver driver, int waitSeconds) {
        try {
            return pageClass.getDeclaredConstructor(WebDriver.class, int.class).newInstance(driver, waitSeconds);
        } catch (Exception ex) {
            throw new RuntimeException("Unable to create page object: " + pageClass.getSimpleName(), ex);
        }
    }
}
