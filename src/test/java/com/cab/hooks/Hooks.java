

package com.cab.hooks;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.cab.base.DriverFactory;
import com.cab.utils.LoggerUtil;
import com.cab.utils.Reports;

import io.cucumber.java.*;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class Hooks {

    private static final Logger logger = LoggerUtil.getLogger(Hooks.class);
    private static final String reportPath = "target/ExtentReport.html";

    public static ExtentReports extent;
    public static ExtentTest test;
    private WebDriver driver;

    @BeforeAll
    public static void initReport() {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setDocumentTitle("Reports for CallTaxi");
        sparkReporter.config().setReportName("CallTaxiService Automation Results");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        logger.info("Extent Report initialized.");
    }

    @AfterAll
    public static void flushReport() {
        extent.flush();
        logger.info("Extent Report flushed and saved to: {}", reportPath);
    }

    @Before
    public void setUp(Scenario scenario) {
        driver = DriverFactory.initDriver();
        test = extent.createTest(scenario.getName());
        logger.info("Scenario Started: {} ", scenario.getName());
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            logger.error("Scenario Failed: {}", scenario.getName());

            // Capture screenshot
            String screenshotPath = Reports.captureScreenshot(driver, scenario.getName().replaceAll(" ", "_"));
            test.fail("Scenario failed. Screenshot attached.")
                .addScreenCaptureFromPath(screenshotPath);

            logger.info("Screenshot captured at: {}", screenshotPath);
        } else {
            logger.info("Scenario Passed: {}", scenario.getName());
            test.pass("Scenario passed.");
        }

        DriverFactory.quitDriver();
        logger.info("Scenario Ended: {}", scenario.getName());
    }
}
