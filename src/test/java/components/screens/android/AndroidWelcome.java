package components.screens.android;

import components.screens.base.Welcome;

public class AndroidWelcome extends Welcome {
    @Override
    public void checkPresenceOfHomeIcon() throws Exception {
        info("[AndroidWelcome] Verifying presence of Home Icon on Welcome Screen");
        if (isElementPresent(welcomeScreen.home_Button))
            info("[AndroidWelcome] Verified Home Icon on Welcome Screen and the Icon is present");
    }

    @Override
    public void checkPresenceOfTripsIcon() throws Exception {
        info("[AndroidWelcome] Verifying presence of Trips Icon on Welcome Screen");
        if (isElementPresent(welcomeScreen.trips_Button))
            info("[AndroidWelcome] Verified Trips Icon on Welcome Screen and the Icon is present");
    }

    @Override
    public void checkPresenceOfMyprofileIcon() throws Exception {
        info("[AndroidWelcome] Verifying presence of my profile Icon on Welcome Screen");
        if (isElementPresent(welcomeScreen.myProfileIcon_Button))
            info("[AndroidWelcome] Verified my profile Icon on Welcome Screen and the Icon is present");
    }

    @Override
    public void checkPresenceOfTripIdeasIcon() throws Exception {
        info("[AndroidWelcome] Verifying presence of Trip Ideas Icon on Welcome Screen");
        if (isElementPresent(welcomeScreen.tripIdeas_Button))
            info("[AndroidWelcome] Verified Trip Ideas Icon on Welcome Screen and the Icon is present");
    }

    @Override
    public void checkPresenceOfHelpIcon() throws Exception {
        info("[AndroidWelcome] Verifying presence of Help Icon on Welcome Screen");
        if (isElementPresent(welcomeScreen.help_Button))
            info("[AndroidWelcome] Verified Help Icon on Welcome Screen and the Icon is present");
    }
}
