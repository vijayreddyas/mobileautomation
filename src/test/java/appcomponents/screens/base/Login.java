package appcomponents.screens.base;

import appcomponents.locators.LocatorReferences;

public abstract class Login extends LocatorReferences {
    public abstract void navigateToLoginScreen() throws Exception;
    public abstract void verifyLoginScreen() throws Exception;
    public abstract void loginWithDetails(String username, String password) throws Exception;
    public abstract void verifyLogin(String usernam) throws Exception;
    public abstract void logout() throws Exception;
}
