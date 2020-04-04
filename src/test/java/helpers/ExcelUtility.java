package helpers;

import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static reporting.LogFile.commonLog;

public class ExcelUtility {

    public static LinkedHashMap<String, LinkedHashMap<String, String>> fetchDataForExecution
            (String dataSourcePath, String testingType, String testEnvironment) throws Exception {
        LinkedHashMap<String, LinkedHashMap<String, String>> executionData = new LinkedHashMap<>();
        LinkedHashMap<String, LinkedHashMap<String, String>> testCases_without_dependencies = new LinkedHashMap<>();
        LinkedHashMap<String, LinkedHashMap<String, String>> testCases_with_dependencies = new LinkedHashMap<>();
        try {
            Fillo fillo = new Fillo();
            Connection connection = fillo.getConnection(dataSourcePath);
            //Fetch Details of TCs marked for execution based on Testing Scope Defined [e.g., SMOKE/SMOKE,REGRESSION]
            for (String testCategory : testingType.split(",")) {
                commonLog.info("Retrieving TC details marked for execution under " + testCategory + " category");
                //Fetch Test Cases for Execution
                String strQuery = "Select * from Base where Execution = 'Y' and Environment = '" + testEnvironment + "'"
                        + " and " + testCategory + " = 'Y'";
                commonLog.info("Query to be executed is :: " + strQuery);
                Recordset recordset = connection.executeQuery(strQuery);
                ArrayList<String> columnNames = recordset.getFieldNames();

                //For every TC marked for execution, fetch required Test Data from Module Sheets
                while (recordset.next()) {
                    LinkedHashMap<String, String> data = new LinkedHashMap<>();
                    for (String field : columnNames) {
                        data.put(field, recordset.getField(field));
                    }

                    //Fetch Test Data from Module Sheet & Update Test Data Map
                    String scenarioDataSelection = "Select * from " + data.get("Module") + " where TestCaseName = '" + data.
                            get("TestCaseName") + "' and Environment = '" + testEnvironment + "'";
                    Recordset scenarioRecordSet = connection.executeQuery(scenarioDataSelection);
                    ArrayList<String> scenarioColumnNames = scenarioRecordSet.getFieldNames();
                    while (scenarioRecordSet.next()) {
                        for (String field : scenarioColumnNames) {
                            data.put(field, scenarioRecordSet.getField(field));
                            if (field.equalsIgnoreCase(scenarioColumnNames.get(scenarioColumnNames.size() - 1))) {
                                if (data.get("DependsOn").equalsIgnoreCase(""))
                                    testCases_without_dependencies.put(data.get("TestCaseName"), data);
                                else
                                    testCases_with_dependencies.put(data.get("TestCaseName"), data);
                                //executionData.put(data.get("TestCaseName"), data);
                            }
                        }
                    }
                }
                commonLog.info("Total No. Of Test Cases under " + testCategory + " are " + recordset.getCount());
                commonLog.info("TCs & associated information for " + testCategory + " category are successfully " +
                        "fetched");
                recordset.close();
            }
            executionData.putAll(testCases_without_dependencies);
            executionData.putAll(testCases_with_dependencies);
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