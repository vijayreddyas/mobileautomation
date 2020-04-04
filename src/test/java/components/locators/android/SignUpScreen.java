package components.locators.android;

import org.openqa.selenium.By;

public class SignUpScreen {
    public By clearEnterMobileEmail_TextBox = By.id("com.makemytrip:id/text_input_end_icon");
    public By enterMobileEmail_TextBox = By.xpath("//android.widget.EditText[@text='Enter Mobile No./Email']");
    public By continue_Button = By.id("com.makemytrip:id/btn_submit");
    public By confirmationOTPSent_Text = By.xpath("//android.widget.TextView[@text='OTP has been sent to EMAIL']");
    public By gmail_Text = By.xpath("//android.widget.TextView[@text='Gmail']");
    public By makeMyTrip_Text = By.xpath("//android.widget.TextView[@text='OTP has been sent to EMAIL']");
    public By gmailOTP_Text = By.id("android:id/big_text");
    public By inputOTP_TextBox = By.className("android.widget.EditText");
    public By submit_Button = By.id("com.makemytrip:id/btn_continue");
    public By welcomeAboard_Text = By.xpath("//android.widget.TextView[@text='Welcome Aboard!']");
    public By fullName_TextBox = By.xpath("//android.widget.EditText[@text='Full Name']");
    public By password_TextBox = By.xpath("//android.widget.EditText[@text='Password(Optional)']");
    public By save_Button = By.xpath("//android.widget.Button[@text='SAVE']");
}
