package helpers;

import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static reporting.LogFile.commonLog;


public class ExcelUtility {

    public static LinkedHashMap<String, LinkedHashMap<String, String>> fetchDataForExecution
            (String dataSourcePath, String testingType, String testEnvironment, String testCase) throws Exception {
        LinkedHashMap<String, LinkedHashMap<String, String>> executionData = new LinkedHashMap<>();
        LinkedHashMap<String, LinkedHashMap<String, String>> testCases = new LinkedHashMap<>();
        try {
            Fillo fillo = new Fillo();
            Connection connection = fillo.getConnection(dataSourcePath);
            //Fetch Details of TCs marked for execution based on Testing Scope Defined [e.g., SMOKE/SMOKE,REGRESSION]
            for (String testCategory : testingType.split(",")) {
                commonLog.info("Retrieving TC details marked for execution under " + testCategory + " category");
                //Fetch Test Cases for Execution
                String strQuery = "Select * from Base where Execution = 'Y' and Environment = '" + testEnvironment + "'"
                        + " and " + testCategory + " = 'Y'" + " and TestCaseName = '" + testCase + "'";
                commonLog.info("Query to be executed is :: " + strQuery);
                Recordset recordset = connection.executeQuery(strQuery);
                ArrayList<String> columnNames = recordset.getFieldNames();
                Recordset scenarioRecordSet = null;
                //For every TC marked for execution, fetch required Test Data from User & Module Sheets
                while (recordset.next()) {
                    LinkedHashMap<String, String> data = new LinkedHashMap<>();
                    for (String field : columnNames) {
                        data.put(field, recordset.getField(field));
                    }

                    //Fetch Test Data from Module Sheet & Update Test Data Map
                    String scenarioDataSelection = "Select * from " + data.get("Module") + " where TestCaseName = '" + data.
                            get("TestCaseName") + "' and Environment = '" + testEnvironment + "'";
                    scenarioRecordSet = connection.executeQuery(scenarioDataSelection);
                    ArrayList<String> scenarioColumnNames = scenarioRecordSet.getFieldNames();
                    while (scenarioRecordSet.next()) {
                        for (String field : scenarioColumnNames) {
                            data.put(field, scenarioRecordSet.getField(field));
                            if (field.equalsIgnoreCase(scenarioColumnNames.get(scenarioColumnNames.size() - 1))) {
                                testCases.put(data.get("TestCaseName"), data);
                            }
                        }
                    }
                }
                commonLog.info("TC & associated information for test case " + testCase + " is fetched successfully");
                recordset.close();
            }
            executionData.putAll(testCases);
            connection.close();
        } catch (Exception e) {
            commonLog.error("Failed while reading data from Test Data Source");
            commonLog.error("Please verify Test Instructions/Test Data and fix errors");
            commonLog.error(e.getMessage());
            throw new Exception(e);
        }
        return executionData;
    }
}