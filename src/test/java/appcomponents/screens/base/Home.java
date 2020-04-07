package appcomponents.screens.base;

import appcomponents.locators.LocatorReferences;

public abstract class Home extends LocatorReferences {
    public abstract void checkPresenceOfExploreIcon() throws Exception;
    public abstract void checkPresenceOfMyListsIcon() throws Exception;
    public abstract void checkPresenceOfHistoryIcon() throws Exception;
    public abstract void checkPresenceOfNearByIcon() throws Exception;
    public abstract void checkPresenceOfSearchBar() throws Exception;
    public abstract void checkPresenceOfMenuIcon() throws Exception;
}
