package appcomponents.screens.android;

import appcomponents.screens.base.Home;

public class AndroidHome extends Home {
    @Override
    public void checkPresenceOfExploreIcon() throws Exception {
        info("[AndroidHome] Verifying presence of Explore Icon on Home Screen");
        if (isElementPresent(homeScreen.explore_Button))
            info("[AndroidHome] Verified Explore Icon on Home Screen and the Icon is present");
    }

    @Override
    public void checkPresenceOfMyListsIcon() throws Exception {
        info("[AndroidHome] Verifying presence of MyLists Icon on Home Screen");
        if (isElementPresent(homeScreen.mylists_Button))
            info("[AndroidHome] Verified MyLists Icon on Home Screen and the Icon is present");
    }

    @Override
    public void checkPresenceOfHistoryIcon() throws Exception {
        info("[AndroidHome] Verifying presence of History Icon on Home Screen");
        if (isElementPresent(homeScreen.history_Button))
            info("[AndroidHome] Verified History Icon on Home Screen and the Icon is present");
    }

    @Override
    public void checkPresenceOfNearByIcon() throws Exception {
        info("[AndroidHome] Verifying presence of NearBy Icon on Home Screen");
        if (isElementPresent(homeScreen.nearby_Button))
            info("[AndroidHome] Verified NearBy Icon on Home Screen and the Icon is present");
    }

    @Override
    public void checkPresenceOfSearchBar() throws Exception {
        info("[AndroidHome] Verifying presence of SearchBar on Home Screen");
        if (isElementPresent(homeScreen.searchbar_TextBox))
            info("[AndroidHome] Verified SearchBar on Home Screen and the Icon is present");
    }

    @Override
    public void checkPresenceOfMenuIcon() throws Exception {
        info("[AndroidHome] Verifying presence of overflow menu on Home Screen");
        if (isElementPresent(homeScreen.menuoverflow_Button))
            info("[AndroidHome] Verified overflow menu on Home Screen and the Icon is present");
    }
}
