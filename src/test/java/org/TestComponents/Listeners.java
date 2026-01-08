package org.TestComponents;

import Resources.ExtentReporterNG;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.io.IOException;

public class Listeners extends BaseTest implements ITestListener {

    ExtentTest test;
    ExtentReports extentReports = ExtentReporterNG.extentReportsConfig();
    ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {


        test = extentReports.createTest(result.getMethod().getMethodName());
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.log(Status.PASS,"Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        extentTest.get().fail(result.getThrowable());

        BaseTest baseTest = (BaseTest) result.getInstance();
        WebDriver driver = baseTest.driver;

        try {
            String screenshotPath = baseTest.getScreenShot(
                    result.getMethod().getMethodName(),
                    driver
            );

            extentTest.get().addScreenCaptureFromPath(screenshotPath);

        } catch (IOException e) {
            extentTest.get().warning("Screenshot capture failed: " + e.getMessage());
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        extentReports.flush();
    }
}
