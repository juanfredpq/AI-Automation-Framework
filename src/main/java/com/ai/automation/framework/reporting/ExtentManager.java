package com.ai.automation.framework.reporting;

import com.ai.automation.framework.config.ConfigManager;
import com.ai.automation.framework.utils.FileUtils;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public final class ExtentManager {

    private static ExtentReports extentReports;

    private ExtentManager() {
    }

    public static ExtentReports getExtentReports() {
        if (extentReports == null) {
            String reportDirectory = ConfigManager.getInstance().getReportPath();
            FileUtils.createDirectoryIfMissing(reportDirectory);
            String reportFile = reportDirectory + "/" + ConfigManager.getInstance().getReportFile();

            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportFile);
            sparkReporter.config().setDocumentTitle("Automation Execution Report");
            sparkReporter.config().setReportName("AI Automation Framework Results");

            extentReports = new ExtentReports();
            extentReports.attachReporter(sparkReporter);
        }
        return extentReports;
    }

    public static void flushReports() {
        if (extentReports != null) {
            extentReports.flush();
        }
    }
}
