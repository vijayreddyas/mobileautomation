package testscripts;

import appcomponents.locators.LocatorReferences;
import appcomponents.screens.android.AndroidHome;
import appcomponents.screens.android.AndroidSettings;
import appcomponents.screens.base.Home;
import appcomponents.screens.base.Settings;
import appcomponents.screens.iOS.IOSHome;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import reporting.ExtentReportManager;

import java.util.LinkedHashMap;

public class SettingsScreenTests extends LocatorReferences {
    private String currentExecutingScope = "";
    Home home;
    Settings settings;

    /**
     * this method instantiate required helpers depending on the platform(android or iOS)
     */
    @BeforeMethod
    public void instantiateHelpers() {
        switch (platform) {
            case "android":
                home = new AndroidHome();
                settings = new AndroidSettings();
                break;
            case "ios":
                home = new IOSHome();
                break;
            case "default":
                error("unable to find any matching operating system");
                break;
        }
    }

    @Test(dataProvider = "testData")
    public void verifyLanguageSelectionFunctionality(String tcName, LinkedHashMap<String, String> testData) throws Exception {
        ExtentReportManager.startTest(tcName, testData);
        if (currentExecutingScope.equals(""))
            currentExecutingScope = getTestScopeCategory(testData);
        ExtentReportManager.getTest().assignCategory(currentExecutingScope);
        settings.navigateToSettingsScreen();
        settings.verifySettingsScreen();
        settings.selectLanguage(testData.get("Wikipedia Language"));
        settings.verifyLanguageSelection(testData.get("Wikipedia Language"));
    }
}
