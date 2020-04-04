package testscripts;

import components.locators.LocatorReferences;
import helpers.ExcelUtility;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import reporting.ExtentReportManager;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static reporting.LogFile.commonLog;

public class Script1 extends LocatorReferences {
    private String testScope;
    private String testEnvironment;
    private static ArrayList<String> tcNames = new ArrayList<>();
    private String currentExecutingScope = "";
    private Boolean anyTestFailed = false;
    private static int totalNoOfTCsMarkedForExecution;

    @BeforeTest(alwaysRun = true)
    public void setParameters() {
        try {
            // First looks for runtime parameters for scope & environment information
            testScope = System.getProperty("testScope");
            commonLog.info("Executing TCs from " + testScope + " categories");
            testEnvironment = System.getProperty("testEnvironment");
            commonLog.info("Executing TCs against " + testEnvironment + " environment");
        }catch (Exception e){
            commonLog.error("Unable to resolve testScope & testEnvironment parameters to proceed with execution");
            commonLog.error(e.getMessage());
            commonLog.error("Terminating executing as critical information is not provided");
            System.exit(1);
        }

    }

    @Test(dataProvider = "testData")
    public void script1(String tcName, LinkedHashMap<String, String> testData){
        System.out.println(testData);
        ExtentReportManager.startTest(tcName, testData);
        if (currentExecutingScope.equals(""))
            currentExecutingScope = getTestScopeCategory(testData);
        ExtentReportManager.getTest().assignCategory(currentExecutingScope);
        System.out.println(testData.get("Module"));
        System.out.println(testData.get("customerId"));
    }

    @DataProvider(parallel = true)
    public Object[][] testData() {
        try {
            LinkedHashMap<String, LinkedHashMap<String, String>> testDataFetched = ExcelUtility.fetchDataForExecution
                    (System.getProperty("user.dir") + "/src/test/resources/testdata/TEST_DATA.xlsx",
                            testScope, testEnvironment);
            totalNoOfTCsMarkedForExecution = testDataFetched.size();
            Object[][] testData = new Object[testDataFetched.size()][2];
            int counter = 0;
            for (String key : testDataFetched.keySet()) {
                tcNames.add(key);
                testData[counter][0] = key;
                testData[counter][1] = testDataFetched.get(key);
                counter += 1;
            }
            return testData;
        }catch (Exception e){
            commonLog.error("Exception occurred while retrieving data from test data file, terminating execution");
            commonLog.error(e.getMessage());
            System.exit(1);
            return null;
        }
    }

    private String getTestScopeCategory(LinkedHashMap<String, String> testData) {
        if (testScope.toUpperCase().contains("SMOKE") && testData.get("Smoke").equalsIgnoreCase("Y"))
            return "SMOKE";
        else if (testScope.toUpperCase().contains("SANITY") && testData.get("Sanity").equalsIgnoreCase("Y"))
            return "SANITY";
        else if (testScope.toUpperCase().contains("REGRESSION") && testData.get("Regression").equalsIgnoreCase("Y"))
            return "REGRESSION";
        return null;
    }

}
