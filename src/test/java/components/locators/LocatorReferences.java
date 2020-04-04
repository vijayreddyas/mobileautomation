package components.locators;

import components.locators.android.*;
import components.locators.iOS.IOSWelcomeScreen;
import components.screens.iOS.IOSWelcome;
import helpers.GenericMethods;

public class LocatorReferences extends GenericMethods {
    public LoginOrSignUpScreen loginOrSignUpScreen = new LoginOrSignUpScreen();
    public SiginWithScreen signinWithScreen = new SiginWithScreen();
    public SignUpScreen signUpScreen = new SignUpScreen();
    public WelcomeScreen welcomeScreen = new WelcomeScreen();
    public IOSWelcomeScreen iosWelcomeScreen = new IOSWelcomeScreen();
}
