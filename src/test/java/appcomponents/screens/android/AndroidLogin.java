package appcomponents.screens.android;

import appcomponents.screens.base.Login;

public class AndroidLogin extends Login {

    @Override
    public void navigateToLoginScreen() throws Exception {
        info("[AndroidLogin] Navigating to Login screen");
        click(homeScreen.menuoverflow_Button);
        click(homeScreen.accountname_Text);
    }

    @Override
    public void verifyLoginScreen() throws Exception {
        info("[AndroidLogin] Verifying Login screen navigation");
        if (isElementPresent(loginScreen.loginheader_Label))
            info("[AndroidLogin] Login screen navigation verification success");
    }

    @Override
    public void loginWithDetails(String username, String password) throws Exception {
        info("[AndroidLogin] Performing Login with user :: " + username);
        sendKeys(loginScreen.username_Textbox, username);
        sendKeys(loginScreen.password_Textbox, password);
        click(loginScreen.login_Button);
    }

    @Override
    public void verifyLogin(String username) throws Exception {
        info("[AndroidLogin] Verifying Login for user :: " + username);
        click(homeScreen.menuoverflow_Button);
        if (wait_until_MobileElementIs_Visible(homeScreen.accountname_Text).getText().equalsIgnoreCase(username))
            info("[AndroidLogin] User Loged in as : " + username);
        info("[AndroidLogin] Verify Logout on Home screen");
        if (isElementPresent(homeScreen.logout_Text))
            info("[AndroidLogin] Login success for user : " + username);
    }

    public void logout() throws Exception {
        info("[AndroidLogin] Logging out from the app");
        click(homeScreen.logout_Text);
        String logOutMessage = wait_until_MobileElementIs_Visible(homeScreen.toast_Text).getText();
        if (logOutMessage.equalsIgnoreCase("Logged out")) {
            infoMarkUpLabel("User Logged out successfully", "GREEN");
        } else {
            fail("USer Logout not success");
        }
    }
}
