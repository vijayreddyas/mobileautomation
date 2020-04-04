package reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ExtentReportManager {
    private static ExtentHtmlReporter htmlReporter;
    public static ExtentReports extent;

    public synchronized static ExtentReports getReporter(String filePath) {
        if (htmlReporter == null) {
            htmlReporter = new ExtentHtmlReporter(filePath);
            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);
            htmlReporter.config().setDocumentTitle("APPIUM Automation Report");
            htmlReporter.config().setReportName("APPIUM Execution Results");
            htmlReporter.config().setTheme(Theme.DARK);

            // General information related to application
            extent.setSystemInfo("OS", System.getProperty("os.name"));
        }
        return extent;
    }

    private static Map<Integer, ExtentTest> extentTestMap = new HashMap<>();

    public static synchronized ExtentTest getTest() {
        return extentTestMap.get((int) (long) (Thread.currentThread().getId()));
    }

    public static synchronized void removeTest() {
        extent.removeTest(extentTestMap.get((int) (long) (Thread.currentThread().getId())));
    }

    public static synchronized ExtentTest startTest(String testName) {
        return startTest(testName, "");
    }

    public static void assignTestCategory(LinkedHashMap<String, String> testData) {
        //ExtentReportManager.getTest().assignCategory("SMOKE");
        if (testData.get("Smoke").equalsIgnoreCase("Y"))
            ExtentReportManager.getTest().assignCategory("SMOKE");
        else if (testData.get("Regression").equalsIgnoreCase("Y"))
            ExtentReportManager.getTest().assignCategory("REGRESSION");
        else if (testData.get("Sanity").equalsIgnoreCase("Y"))
            ExtentReportManager.getTest().assignCategory("SANITY");
    }

    public static void startTest(String tcName, LinkedHashMap<String, String> testData) {
        if (!testData.get("Description").equalsIgnoreCase(""))
            ExtentReportManager.startTest(tcName, testData.get("Description"));
        else
            ExtentReportManager.startTest(tcName);
        assignTestCategory(testData);
    }

    public static synchronized ExtentTest startTest(String testName, String desc) {
        ExtentTest test = extent.createTest(testName, desc);
        extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
        return test;
    }

}
