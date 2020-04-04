package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import helpers.AppiumServer;
import helpers.CapabiliesManager;
import helpers.CommonUtils;
import helpers.FormatDates;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.logging.LogEntry;
import org.testng.ITest;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import reporting.ExtentReportManager;
import reporting.LogFile;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class TestBase extends LogFile implements ITest {
    private ThreadLocal<List<LogEntry>> logEntries = new ThreadLocal<>();
    private ThreadLocal<PrintWriter> log_file_writer = new ThreadLocal<>();
    public ThreadLocal<String> testName = new ThreadLocal<>();
    public File logFile;
    URL hubUrl = null;
    private ExtentReports extent;
    public static long startTime;
    private String extentReportFilePath = System.getProperty("user.dir") + "/test-output/ExtentReport.html";
    public static Properties prop = new Properties();
    public String platform;

    /**
     * @throws IOException
     * @throws InterruptedException
     */
    public void initialize() throws IOException, InterruptedException {
        commonLog.info("[TestBase] Starting of Configuration");
        commonLog.info("[TestBase] Loading the base property file");
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/properties/base.properties");
        prop.load(fis);
        commonLog.info("[TestBase] Loaded the base property file");
        String appiumServer = prop.getProperty("appium.server");
        int port = CommonUtils.nextFreePort(4000, 5000);
        AppiumServer.startServer(appiumServer, port);
        commonLog.info("[TestBase] STARTED APPIUM SERVER WITH BELOW HOST AND PORT DETAILS");
        commonLog.info("[TestBase] --------------------------------------------------------");
        commonLog.info("[TestBase] HOST :: " + appiumServer);
        commonLog.info("[TestBase] PORT :: " + port);
        commonLog.info("[TestBase] --------------------------------------------------------");
        platform = prop.getProperty("platform");
        dr = defineDriver(prop.getProperty("platform"), appiumServer, port);
        get().setAppiumDriver(dr);
        getDriver().manage().timeouts().implicitlyWait(Integer.parseInt(prop.getProperty("implicit.wait")), TimeUnit.SECONDS);
    }

    /**
     * @param platform
     * @param appiumServer
     * @param port
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public synchronized AppiumDriver defineDriver(String platform, String appiumServer, int port) throws IOException, InterruptedException {
        switch (platform) {
            case "android":
                hubUrl = new URL("http://" + appiumServer + ":" + port + "/wd/hub");
                dr = new AndroidDriver(hubUrl, CapabiliesManager.androidCapabilities());
                break;

            case "ios":
                hubUrl = new URL("http://" + appiumServer + ":" + port + "/wd/hub");
                dr = new IOSDriver(hubUrl, CapabiliesManager.iOSCapabilities());
                break;

            case "webview":
                hubUrl = new URL("http://" + appiumServer + ":" + port + "/wd/hub");
                dr = new AppiumDriver(hubUrl, CapabiliesManager.androidCapabilities());
                break;

            default:
                error("Invalid platform");
        }
        return dr;
    }

    /**
     * @param method
     * @param ctx
     * @throws Exception
     */
    @BeforeMethod(alwaysRun = true)
    protected void beforeMethod(Method method, ITestContext ctx) throws Exception {
        commonLog.info("Inside Before Method of TestBase");
        testName.set(method.getName());
        ctx.setAttribute("testName", testName.get());
        LogFile.writeToTestLog(testName.get());
        //startLogging(testName.get());
        commonLog.info("Staring Test " + method.getName() + " Execution");
        flog("status.html", "<b>Executing Test </b> :: " + method.getName());
    }

    /**
     * @throws Exception
     */
    @BeforeSuite(alwaysRun = true)
    protected void beforeSuite() throws Exception {
        initialize();
        commonLog.info("Inside Before Suite of TestBase");
        startTime = new Date().getTime();
        new File(extentReportFilePath).delete();
        new File("status.html").delete();
        extent = ExtentReportManager.getReporter(extentReportFilePath);
        flog("status.html", "<meta http-equiv='refresh' content='3'>");
        flog("status.html", "<b>Execution in Progress !!</b>");
        openStatusReport("file:///" + System.getProperty("user.dir").replace("\\", "/") + "/status.html");
    }

    /**
     * @param method
     * @param result
     * @throws Exception
     */
    @AfterMethod(alwaysRun = true)
    protected void afterMethod(Method method, ITestResult result) throws Exception {
        commonLog.info("Inside After Method of TestBase");
        //endLogging(result.getTestName());
        if (result.getStatus() == ITestResult.FAILURE) {
            ExtentReportManager.getTest().log(Status.FAIL, MarkupHelper.createLabel(result.getTestName() +
                    " - Test Case Failed", ExtentColor.RED));
        } else if (result.getStatus() == ITestResult.SKIP) {
            ExtentReportManager.getTest().log(Status.SKIP, MarkupHelper.createLabel(result.getTestName() +
                    " - Test Case Skipped", ExtentColor.ORANGE));
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            ExtentReportManager.getTest().log(Status.PASS, MarkupHelper.createLabel(result.getTestName() +
                    " Test Case PASSED", ExtentColor.GREEN));
        }
        flog("status.html", "<b>Executed Test</b> :: " + method.getName() + " Status :: " +
                result.getStatus());
    }

    /**
     *
     */
    @AfterSuite(alwaysRun = true)
    protected void afterSuite() {
        commonLog.info("Inside After Suite of TestBase");
        getDriver().quit();
        AppiumServer.stopServer();
        ExtentReportManager.getReporter(extentReportFilePath).flush();
        commonLog.info("Total Execution Time: " + getMinutes(startTime));
        flog("status.html", "<b>Execution Completed !!</b> <br/> Total Execution Time :: " +
                getMinutes(startTime));
        replaceContentInFile(extentReportFilePath, "<a class='logo-content' " +
                "href='http://extentreports.relevantcodes.com'>", "");
        replaceContentInFile(extentReportFilePath,
                "<span>ExtentReports</span>", "<span>Execution Report</span>");
        openStatusReport("file:///" + System.getProperty("user.dir").replace("\\", "/") +
                "/test-output/ExtentReport.html");
    }

    /**
     * @return
     */
    @Override
    public String getTestName() {
        return testName.get();
    }

    /**
     * @param startTime
     * @return
     */
    private String getMinutes(long startTime) {
        return ((new Date().getTime() - startTime) / 1000) / 60 + " minutes";
    }

    /**
     * @param url
     */
    public void openStatusReport(String url) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("xdg-open " + url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param methodName
     * @throws IOException
     */
    public void startLogging(String methodName) throws IOException {
        java.util.List<LogEntry> logcat = getDriver().manage().logs().get("logcat").filter(Level.ALL);
        this.logEntries.set(logcat);
        this.logFile = new File(System.getProperty("user.dir") + "/deviceLogs/" + methodName + ".log");
        this.log_file_writer.set(new PrintWriter(this.logFile));
    }

    /**
     * @param methodName
     * @throws Exception
     */
    public void endLogging(String methodName) throws Exception {
        HashMap<String, String> logs = new HashMap<>();
        String adbPath = System.getProperty("user.dir") + "/deviceLogs/" + methodName + ".log";
        logs.put("adbLogs", adbPath);
        ((List) this.logEntries.get()).forEach(logEntry -> (
                (PrintWriter) this.log_file_writer.get()).println(logEntry));
        ((PrintWriter) this.log_file_writer.get()).close();
    }

    /**
     * @param file
     * @param find
     * @param replace
     */
    public void replaceContentInFile(String file, String find, String replace) {
        try {
            String content = FileUtils.readFileToString(new File(file), "UTF-8");
            content = content.replaceAll(find, replace);
            File tempFile = new File(file);
            FileUtils.writeStringToFile(tempFile, content, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Generating file failed", e);
        }
    }
}
