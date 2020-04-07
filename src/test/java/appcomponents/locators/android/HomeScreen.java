package appcomponents.locators.android;

import org.openqa.selenium.By;

public class HomeScreen {
    public By menuoverflow_Button = By.id("org.wikipedia.alpha:id/menu_overflow_button");
    public By searchbar_TextBox = By.xpath("//android.widget.TextView[@text='Search Wikipedia']");
    public By explore_Button = By.xpath("//android.widget.FrameLayout[@content-desc='Explore']");
    public By mylists_Button = By.xpath("//android.widget.FrameLayout[@content-desc='My lists']");
    public By history_Button = By.xpath("//android.widget.FrameLayout[@content-desc='History']");
    public By nearby_Button = By.xpath("//android.widget.FrameLayout[@content-desc='Nearby']");
    public By accountname_Text = By.id("org.wikipedia.alpha:id/explore_overflow_account_name");
    public By settings_Text = By.id("org.wikipedia.alpha:id/explore_overflow_settings");
    public By logout_Text = By.id("org.wikipedia.alpha:id/explore_overflow_log_out");
    public By toast_Text = By.id("org.wikipedia.alpha:id/snackbar_text");
}
