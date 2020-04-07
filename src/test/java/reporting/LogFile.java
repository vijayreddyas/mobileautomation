package reporting;


import base.TestContext;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import helpers.FormatDates;
import org.apache.log4j.*;
import org.testng.Reporter;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Properties;

public class LogFile extends TestContext {
    private static String date = FormatDates.getCurrentDateTime();

    public static Category commonLog = writeToCommonLog();

    public static Category writeToCommonLog() {
        initializeLogger();
        commonLog = Category.getInstance(LogFile.class);
        return commonLog;
    }

    private static LinkedHashMap<String, Category> logStore = new LinkedHashMap<>();
    private static ThreadLocal<String> testName = new ThreadLocal<>();

    public static synchronized Category writeToTestLog(String myTestCaseName) throws Exception {
        //log = initializeTestLogger(myTestCaseName);
        testName.set(myTestCaseName);
        try {
            logStore.put(testName.get(), initializeTestLogger(myTestCaseName));
        }catch (FileNotFoundException e){
            try {
                if(new File("log/" + date + "/" + testName.get() + ".log").createNewFile())
                    commonLog.info("Log file for Test Case [" + myTestCaseName + "] is created successfully");
                else
                    commonLog.error("Failed to created Log file for Test Case [" + myTestCaseName + "]");
            }catch (Exception e1){
                commonLog.error(e1.getMessage());
                throw new Exception(e1);
            }
        }
        logStore.get(testName.get()).info("Setting up Test Case ["+ testName.get() +"] Log File ");
        return logStore.get(testName.get());
    }

    private static synchronized Logger initializeTestLogger(String myTestCaseName) throws Exception {
        // Initialize the logger
        String loggerId = myTestCaseName + "_" + Thread.currentThread().getId();
        Logger myLogger = Logger.getLogger(loggerId);
        FileAppender myFileAppender;
        try {
            PatternLayout layout = new PatternLayout();
            String conversionPattern = "%d{yyyy-MM-dd HH:mm:ss} [%-5p] [%c{1}] - [%M] %m%n";
            layout.setConversionPattern(conversionPattern);

            // creates console appender
            ConsoleAppender consoleAppender = new ConsoleAppender();
            consoleAppender.setLayout(layout);
            consoleAppender.activateOptions();

            myFileAppender = new FileAppender(layout, "log/" + date + "/"
                    + myTestCaseName + ".log", false);
            myFileAppender.activateOptions();
            myLogger.addAppender(myFileAppender);
        } catch (IOException e1) {
            myLogger.info(e1.getMessage());
            commonLog.error(e1.getMessage());
            throw new Exception(e1);
        }
        return myLogger;
    }

    private static synchronized void initializeLogger() {
        Properties logProperties = new Properties();
        try {
            // load our log4j properties / configuration file
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir")
                    + "/src/test/resources/properties/log4j.properties");
            logProperties.load(fis);
            PropertyConfigurator.configure(logProperties);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
            commonLog.error(e.getMessage());
        }
    }

    public static void error(String msg) {
        logStore.get(testName.get()).error(msg);
        ReportLog(msg);
        if (ExtentReportManager.getTest() != null)
            ExtentReportManager.getTest().log(Status.ERROR, msg);
    }

    public static void fail(String msg) {
        ReportLog(msg);
        logStore.get(testName.get()).error(msg);
        if (ExtentReportManager.getTest() != null)
            ExtentReportManager.getTest().log(Status.FAIL, msg);
            ExtentReportManager.getTest().log(Status.FAIL, MarkupHelper.createLabel(msg, ExtentColor.RED));
    }

    protected static void skip(String msg) {
        ReportLog(msg);
        logStore.get(testName.get()).error(msg);
        if (ExtentReportManager.getTest() != null)
            ExtentReportManager.getTest().log(Status.SKIP, msg);
    }

    protected static void fatal(String msg) {
        ReportLog(msg);
        logStore.get(testName.get()).error(msg);
        if (ExtentReportManager.getTest() != null)
            ExtentReportManager.getTest().log(Status.FATAL, msg);
    }

    protected static void skipMarkUpLabel(String msg, String color) {
        ReportLog(msg);
        logStore.get(testName.get()).error(msg);
        if (ExtentReportManager.getTest() != null)
            ExtentReportManager.getTest().log(Status.SKIP, MarkupHelper.createLabel(msg, ExtentColor.valueOf(color)));
    }

    protected static void success(String msg) {
        logStore.get(testName.get()).info(msg);
        ReportLog(msg);
        if (ExtentReportManager.getTest() != null)
            ExtentReportManager.getTest().log(Status.PASS, msg);
    }

    protected static void info(String msg) {
        logStore.get(testName.get()).info(msg);
        ReportLog(msg);
        if (ExtentReportManager.getTest() != null)
            ExtentReportManager.getTest().log(Status.INFO, msg);
    }

    protected static void infoMarkUpCodeBlock(String msg) {
        logStore.get(testName.get()).info(msg);
        ReportLog(msg);
        if (ExtentReportManager.getTest() != null)
            ExtentReportManager.getTest().log(Status.INFO, MarkupHelper.createCodeBlock(msg));
    }

    protected static void infoMarkUpCodeBlock(String msg, String language) {
        logStore.get(testName.get()).info(msg);
        ReportLog(msg);
        if (ExtentReportManager.getTest() != null)
            ExtentReportManager.getTest().log(Status.INFO, MarkupHelper.createCodeBlock(msg,
                    CodeLanguage.valueOf(language)));
    }

    protected static void infoMarkUpLabel(String msg, String color) {
        logStore.get(testName.get()).info(msg);
        ReportLog(msg);
        if (ExtentReportManager.getTest() != null)
            ExtentReportManager.getTest().log(Status.INFO, MarkupHelper.createLabel(msg, ExtentColor.valueOf(color)));
    }

    protected static void debug(String msg) {
        ReportLog(msg);
        logStore.get(testName.get()).info(msg);
        if (ExtentReportManager.getTest() != null)
            ExtentReportManager.getTest().log(Status.DEBUG, msg);
    }

    public static void flog(String msg) {
        flog("flog.html", msg);
    }

    private static void flog(String fileName, String msg, String suffix)  {
        try {
            FileWriter fw = new FileWriter(fileName, true);
            fw.write(msg + suffix);
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
            commonLog.error(e.getMessage());
        }
    }

    public static void flog(String fileName, String msg)  {
        flog(fileName, msg, "<br>\n");
    }

    //Logs with TimeStamp
    private static void ReportLog(String sMessage) {
        Date sDate = new Date();
        SimpleDateFormat formatDate = new SimpleDateFormat("HH:mm:SS");
        String time = formatDate.format(sDate);
        Reporter.log(time + " " + sMessage, false);
    }
}
