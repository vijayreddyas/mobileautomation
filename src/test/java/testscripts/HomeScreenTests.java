package testscripts;

import appcomponents.locators.LocatorReferences;
import appcomponents.screens.android.AndroidHome;
import appcomponents.screens.base.Home;
import appcomponents.screens.iOS.IOSHome;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import reporting.ExtentReportManager;

import java.util.LinkedHashMap;

public class HomeScreenTests extends LocatorReferences {
    Home home;
    private String currentExecutingScope = "";

    /**
     * this method instantiate required helpers depending on the platform(android or iOS)
     */
    @BeforeMethod
    public void instantiateHelpers() {
        switch (platform) {
            case "android":
                home = new AndroidHome();
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
    public void verifyWelcomeScreenIcons(String tcName, LinkedHashMap<String, String> testData) throws Exception {
        System.out.println(testData);
        ExtentReportManager.startTest(tcName, testData);
        if (currentExecutingScope.equals(""))
            currentExecutingScope = getTestScopeCategory(testData);
        ExtentReportManager.getTest().assignCategory(currentExecutingScope);
        home.checkPresenceOfExploreIcon();
        home.checkPresenceOfMyListsIcon();
        home.checkPresenceOfHistoryIcon();
        home.checkPresenceOfNearByIcon();
        home.checkPresenceOfSearchBar();
    }
}
