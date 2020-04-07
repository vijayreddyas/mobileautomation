package appcomponents.screens.android;

import appcomponents.screens.base.Settings;

public class AndroidSettings extends Settings {
    @Override
    public void navigateToSettingsScreen() throws Exception {
        info("[AndroidSettings] Navigating to Settings screen");
        click(homeScreen.menuoverflow_Button);
        click(homeScreen.settings_Text);
    }

    @Override
    public void verifySettingsScreen() throws Exception {
        info("[AndroidSettings] Verify navigation to Settings screen");
        if (isElementPresent(settingsScreen.settings_Label)) {
            info("[AndroidSettings] Navigated to Settings screen");
        } else {
            fail("[AndroidSettings] failed to navigate to Settings screen");
        }
    }

    @Override
    public void selectLanguage(String language) throws Exception {
        info("[AndroidSettings] Selecting the language as :: " + language + " from the Wikipedia language dropdown");
        click(settingsScreen.language_Dropdown);
        android_scrollToTextAndClick(language);
    }

    @Override
    public void verifyLanguageSelection(String language) throws Exception {
        info("[AndroidSettings] Verifying the Language selection");
        if (wait_until_MobileElementIs_Visible(settingsScreen.optionsSummary_Labels).getText().equalsIgnoreCase("Simple English")) {
            infoMarkUpLabel("[AndroidSettings] Successfully selected the desired language, " + language + " from the dropdown", "GREEN");
        } else {
            fail("[AndroidSettings] failed to select the desired language, " + language + " from the dropdown");
        }
    }
}
