package appcomponents.locators.android;

import org.openqa.selenium.By;

public class LoginScreen {
    public By loginheader_Label = By.xpath("//android.widget.TextView[@text='Log in to Wikipedia']");
    public By username_Textbox = By.id("org.wikipedia.alpha:id/login_username_text");
    public By password_Textbox = By.xpath("//TextInputLayout[@text='Password']/android.widget.FrameLayout/android.widget.EditText");
    public By login_Button = By.id("org.wikipedia.alpha:id/login_button");
    public By createaccount_Link = By.id("org.wikipedia.alpha:id/login_create_account_link");
    public By privacypolicy_Link = By.id("org.wikipedia.alpha:id/privacy_policy_link");
}
