package appcomponents.locators.android;

import org.openqa.selenium.By;

public class SettingsScreen {
    public By settings_Label = By.xpath("//android.widget.TextView[@text='Settings']");
    public By language_Dropdown = By.xpath("//android.widget.TextView[@text='Wikipedia language']");
    public By optionsSummary_Labels = By.id("android:id/summary");
}
