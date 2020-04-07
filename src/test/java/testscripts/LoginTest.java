package testscripts;

import appcomponents.locators.LocatorReferences;
import appcomponents.screens.android.AndroidHome;
import appcomponents.screens.android.AndroidLogin;
import appcomponents.screens.base.Home;
import appcomponents.screens.base.Login;
import appcomponents.screens.iOS.IOSHome;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import reporting.ExtentReportManager;

import java.util.LinkedHashMap;

public class LoginTest extends LocatorReferences {
    private String currentExecutingScope = "";
    Home home;
    Login login;

    /**
     * this method instantiate required helpers depending on the platform(android or iOS)
     */
    @BeforeMethod
    public void instantiateHelpers() {
        switch (platform) {
            case "android":
                home = new AndroidHome();
                login = new AndroidLogin();
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
    public void verifyLoginFunctionality(String tcName, LinkedHashMap<String, String> testData) throws Exception {
        System.out.println(testData);
        ExtentReportManager.startTest(tcName, testData);
        if (currentExecutingScope.equals(""))
            currentExecutingScope = getTestScopeCategory(testData);
        ExtentReportManager.getTest().assignCategory(currentExecutingScope);
        login.navigateToLoginScreen();
        login.verifyLoginScreen();
        login.loginWithDetails(testData.get("UserName"), testData.get("Password"));
        login.verifyLogin(testData.get("UserName"));
        login.logout();
    }
}
