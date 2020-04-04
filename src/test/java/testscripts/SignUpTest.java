package testscripts;

import components.locators.LocatorReferences;
import components.screens.android.AndroidSignInWith;
import components.screens.android.AndroidSignUp;
import components.screens.android.AndroidWelcome;
import components.screens.base.SignInWith;
import components.screens.base.SignUp;
import components.screens.base.Welcome;
import components.screens.iOS.IOSWelcome;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SignUpTest extends LocatorReferences {
    SignInWith signInWith;
    Welcome welcome;
    SignUp signUp;

    /**
     * this method instantiate required helpers depending on the platform(android or iOS)
     */
    @BeforeMethod
    public void instantiateHelpers() {
        switch (platform) {
            case "android":
                signInWith = new AndroidSignInWith();
                welcome = new AndroidWelcome();
                signUp = new AndroidSignUp();
                break;
            case "ios":
                welcome = new IOSWelcome();
                break;
            case "default":
                error("unable to find any matching operating system");
                break;
        }
    }

    @Test
    public void signUp() throws Exception {
        signInWith.skipDefaultSignIn();
        signUp.navigateToSignUpScreen();
        signUp.signUpWithDetails();
    }
}
