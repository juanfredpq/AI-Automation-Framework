package com.ai.automation.framework.base;

import com.ai.automation.framework.config.ConfigManager;
import com.ai.automation.framework.config.Environment;
import com.ai.automation.framework.driver.DriverManager;
import com.ai.automation.framework.reporting.ExtentManager;
import com.ai.automation.framework.reporting.ExtentReportListener;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

import java.time.Duration;

@Listeners(ExtentReportListener.class)
public abstract class BaseTest {

    protected WebDriver driver;
    protected Environment environment;
    protected String baseUrl;
    protected ConfigManager configManager = ConfigManager.getInstance();

    @BeforeClass(alwaysRun = true)
    protected void setUp() {
        this.environment = configManager.getEnvironment();
        this.baseUrl = configManager.getBaseUrl();
        ExtentManager.getExtentReports();

        DriverManager.initDriver(configManager.getBrowser());
        this.driver = DriverManager.getDriver();
        this.driver.manage().window().maximize();
        this.driver.manage().deleteAllCookies();
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(configManager.getImplicitWait()));
    }

    @AfterClass(alwaysRun = true)
    protected void tearDown() {
        DriverManager.quitDriver();
        ExtentManager.flushReports();
    }
}
