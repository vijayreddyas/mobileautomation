package components.screens.base;

import components.locators.LocatorReferences;

public abstract class Welcome extends LocatorReferences {
    public abstract void checkPresenceOfHomeIcon() throws Exception;
    public abstract void checkPresenceOfTripsIcon() throws Exception;
    public abstract void checkPresenceOfMyprofileIcon() throws Exception;
    public abstract void checkPresenceOfTripIdeasIcon() throws Exception;
    public abstract void checkPresenceOfHelpIcon() throws Exception;
}
