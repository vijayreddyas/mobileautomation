package appcomponents.screens.base;

import appcomponents.locators.LocatorReferences;

public abstract class Settings extends LocatorReferences {
    public abstract void navigateToSettingsScreen() throws Exception;
    public abstract void verifySettingsScreen() throws Exception;
    public abstract void selectLanguage(String language) throws Exception;
    public abstract void verifyLanguageSelection(String language) throws Exception;
}
