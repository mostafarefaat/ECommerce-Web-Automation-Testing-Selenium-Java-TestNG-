package Resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {

    public static ExtentReports extentReports;

    public static ExtentReports extentReportsConfig(){
        //ExtentReports , ExtentSparkReporter
        if (extentReports == null) {
            String path = System.getProperty("user.dir") + "//reports//report.html";
            ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter(path);
            extentSparkReporter.config().setReportName("Web Automation Results");
            extentSparkReporter.config().setDocumentTitle("Test Results");

            extentReports = new ExtentReports();
            extentReports.attachReporter(extentSparkReporter);
            extentReports.setSystemInfo("Tester", "Mostafa");
        }
        return extentReports;
    }
}
