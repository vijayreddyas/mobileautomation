package appcomponents.locators;

import appcomponents.locators.android.*;
import appcomponents.locators.iOS.IOSHomeScreen;
import helpers.GenericMethods;

public class LocatorReferences extends GenericMethods {
    public LoginScreen loginScreen = new LoginScreen();
    public HomeScreen homeScreen = new HomeScreen();
    public SettingsScreen settingsScreen = new SettingsScreen();
    public IOSHomeScreen iosHomeScreen = new IOSHomeScreen();
}
