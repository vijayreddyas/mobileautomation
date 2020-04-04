package components.screens.android;

import components.screens.base.SignUp;
import org.apache.commons.lang3.StringUtils;

public class AndroidSignUp extends SignUp {
    @Override
    public void navigateToSignUpScreen() throws Exception {
        click(welcomeScreen.myProfileIcon_Button);
        click(loginOrSignUpScreen.createAccount_Button);
        click(signinWithScreen.noneOfTheAbove_Button);
    }

    @Override
    public void signUpWithDetails() throws Exception {
        click(signUpScreen.clearEnterMobileEmail_TextBox);
        sendKeys(signUpScreen.enterMobileEmail_TextBox, "vijayabhaskar12345@gmail.com");
        click(signUpScreen.continue_Button);
        wait_until_MobileElementIs_Visible(signUpScreen.confirmationOTPSent_Text);
        openNotifications();
        wait_until_MobileElementIs_Visible(signUpScreen.gmail_Text);
        hardWait(10);
        String gmailText = wait_until_MobileElementIs_Visible(signUpScreen.gmailOTP_Text).getText();
        String OTP = StringUtils.substringBetween(gmailText, "OTP is ", " to login");
        System.out.println("::::::::::" + OTP);
        backKeyAndroid();
        sendKeys(signUpScreen.inputOTP_TextBox, OTP);
        click(signUpScreen.submit_Button);
        wait_until_MobileElementIs_Visible(signUpScreen.welcomeAboard_Text);
        sendKeys(signUpScreen.fullName_TextBox, "Automation");
        sendKeys(signUpScreen.password_TextBox, "Automation@123");
        click(signUpScreen.save_Button);
    }
}
