package com.ai.automation.framework.reporting;

import com.ai.automation.framework.driver.DriverManager;
import com.ai.automation.framework.utils.ScreenshotUtils;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentReportListener implements ITestListener {

    private static final ExtentReports extent = ExtentManager.getExtentReports();
    private static final ThreadLocal<ExtentTest> TEST_REPORT = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
        // No action needed on start beyond report initialization
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getMethod().getMethodName());
        TEST_REPORT.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        TEST_REPORT.get().log(Status.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        TEST_REPORT.get().log(Status.FAIL, result.getThrowable());
        try {
            String screenshotPath = ScreenshotUtils.captureScreenshot(
                    DriverManager.getDriver(),
                    result.getMethod().getMethodName(),
                    System.getProperty("user.dir") + "/target/reports/screenshots");
            TEST_REPORT.get().addScreenCaptureFromPath(screenshotPath);
        } catch (Exception ex) {
            TEST_REPORT.get().log(Status.WARNING, "Unable to capture screenshot for failed test: " + ex.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        TEST_REPORT.get().log(Status.SKIP, "Test skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // No additional implementation required
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.flushReports();
    }
}
