package components.screens.android;

import components.screens.base.SignInWith;

public class AndroidSignInWith extends SignInWith {
    @Override
    public void skipDefaultSignIn() throws Exception {
        click(signinWithScreen.noneOfTheAbove_Button);
        click(signinWithScreen.skip_Button);
    }
}
