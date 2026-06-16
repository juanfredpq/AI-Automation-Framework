package com.ai.automation.tests;

import com.ai.automation.framework.base.BaseTest;
import com.ai.automation.framework.config.ConfigManager;
import com.ai.automation.framework.factory.PageFactoryManager;
import com.ai.automation.framework.pages.HomePage;
import com.ai.automation.framework.reporting.ExtentReportListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(ExtentReportListener.class)
public class SampleUiTest extends BaseTest {

    @Test(description = "Verify home page title is displayed")
    public void verifyHomePageTitle() {
        driver.get(ConfigManager.getInstance().getBaseUrl());
        HomePage homePage = PageFactoryManager.getPage(HomePage.class, driver, ConfigManager.getInstance().getExplicitWait());
        String title = homePage.getHeroTitle();
        Assert.assertNotNull(title, "Home page title should be visible");
    }
}
