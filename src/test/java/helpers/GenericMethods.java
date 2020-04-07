package helpers;

import base.TestBase;
import com.google.common.base.Throwables;
import io.appium.java_client.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.connection.ConnectionState;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;


public class GenericMethods extends TestBase {

    // common timeout for all tests can be set here
    public final int timeOut = 30;

    /**
     * method verify whether element is present on screen
     *
     * @param targetElement element to be present
     * @return true if element is present else throws exception
     * @throws InterruptedException Thrown when a thread is waiting, sleeping,
     *                              or otherwise occupied, and the thread is interrupted, either before
     *                              or during the activity.
     */
    public Boolean isElementPresent(By targetElement) throws Exception {
        try {
            info("Verifying Element Present By :: " + targetElement);
            Boolean isPresent = getDriver().findElements(targetElement).size() > 0;
            return isPresent;
        } catch (Exception e) {
            error("Exception Occurred in while checking for element's presence :: " +
                    targetElement);
            error(Throwables.getStackTraceAsString(e));
            throw new Exception(Throwables.getStackTraceAsString(e));
        }
    }

    /**
     * method verify whether element is present on screen
     *
     * @param targetElement element to be present
     * @return true if element is present else false
     */
    public boolean isElementExists(By targetElement) throws Exception {
        try {
            info("Verifying Element Present By :: " + targetElement);
            boolean isPresent = getDriver().findElements(targetElement).size() > 0;
            return isPresent;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * method to hide keyboard
     */
    public void hideKeyboard() {
        getDriver().hideKeyboard();
    }

    /**
     * method to wait for an element to be visible
     *
     * @param targetElement element to be visible
     * @return true if element is visible else throws TimeoutException
     */
    public boolean waitForVisibility(By targetElement) {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), timeOut);
            wait.until(ExpectedConditions.visibilityOfElementLocated(targetElement));
            return true;
        } catch (TimeoutException e) {
            error("Exception Occurred while waiting for visibility of element with definition :: " +
                    targetElement);
            error(Throwables.getStackTraceAsString(e));
            throw new TimeoutException(Throwables.getStackTraceAsString(e));
        }
    }

    /**
     * method to wait for an element until it is invisible
     *
     * @param targetElement element to be invisible
     * @return true if element gets invisible else throws TimeoutException
     */
    public boolean waitForInvisibility(By targetElement) {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), timeOut);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(targetElement));
            return true;
        } catch (TimeoutException e) {
            error("Exception Occurred in while waiting for invisibility of element with definition :: " +
                    targetElement);
            error(Throwables.getStackTraceAsString(e));
            throw new TimeoutException(Throwables.getStackTraceAsString(e));
        }
    }

    /**
     * method to wait for an element until it is invisible
     *
     * @param targetElement element to be invisible
     * @return true if element gets invisible else throws TimeoutException
     */
    public boolean waitForInvisibility(By targetElement, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), timeOut);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(targetElement));
            return true;
        } catch (TimeoutException e) {
            error("Exception Occurred in while waiting for invisibility of element with definition :: " +
                    targetElement);
            error(Throwables.getStackTraceAsString(e));
            throw new TimeoutException(Throwables.getStackTraceAsString(e));
        }
    }

    /**
     * method to tap on the screen on provided coordinates
     *
     * @param xPosition x coordinate to be tapped
     * @param yPosition y coordinate to be tapped
     */
    public void tap(double xPosition, double yPosition) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        HashMap<String, Double> tapObject = new HashMap<String, Double>();
        tapObject.put("startX", xPosition);
        tapObject.put("startY", yPosition);
        js.executeScript("mobile: tap", tapObject);
    }

    /**
     * method to find an element
     *
     * @param locator element to be found
     * @return WebElement if found else throws NoSuchElementException
     */
    public WebElement findElement(By locator) {
        try {
            WebElement element = getDriver().findElement(locator);
            return element;
        } catch (NoSuchElementException e) {
            error("Exception Occurred in while finding element with definition :: " + locator);
            error(Throwables.getStackTraceAsString(e));
            throw new NoSuchElementException(e.getMessage());
        }
    }

    /**
     * Wait for screen to get loaded
     *
     * @return void
     * @throws Exception
     */
    public void waitForScreenLoad() throws Exception {
        try {
            Thread.sleep(2000);
            //TODO implement generic way of handling screen loading, page transaction
        } catch (Exception e) {
            error("Exception Occurred in while waiting screen to get loaded");
            error(Throwables.getStackTraceAsString(e));
            throw new Exception(Throwables.getStackTraceAsString(e));
        }
    }

    /**
     * method to find all the elements of specific locator
     *
     * @param locator element to be found
     * @return return the list of elements if found else throws NoSuchElementException
     */
    public List<WebElement> findElements(By locator) {
        try {
            List<WebElement> element = getDriver().findElements(locator);
            return element;
        } catch (NoSuchElementException e) {
            error("element not found" + locator);
            throw new NoSuchElementException(e.getMessage());
        }
    }

    /**
     * method to get message test of alert
     *
     * @return message text which is displayed
     */
    public String getAlertText() {
        try {
            Alert alert = getDriver().switchTo().alert();
            String alertText = alert.getText();
            return alertText;
        } catch (NoAlertPresentException e) {
            throw new NoAlertPresentException();
        }
    }

    /**
     * method to verify if alert is present
     *
     * @return returns true if alert is present else false
     */
    public boolean isAlertPresent() {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), timeOut);
            wait.until(ExpectedConditions.alertIsPresent());
            getDriver().switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            throw new NoAlertPresentException();
        }
    }

    /**
     * method to Accept Alert if alert is present
     */

    public void acceptAlert() {
        WebDriverWait wait = new WebDriverWait(getDriver(), timeOut);
        wait.until(ExpectedConditions.alertIsPresent());
        getDriver().switchTo().alert().accept();
    }

    /**
     * method to Dismiss Alert if alert is present
     */

    public void dismissAlert() {
        WebDriverWait wait = new WebDriverWait(getDriver(), timeOut);
        wait.until(ExpectedConditions.alertIsPresent());
        getDriver().switchTo().alert().dismiss();
    }

    /**
     * method to get network settings
     */
    public void getNetworkConnection() {
        System.out.println(((AndroidDriver) getDriver()).getConnection());
    }

    /**
     * method to set network settings
     *
     * @param airplaneMode pass true to activate airplane mode else false
     * @param wifi         pass true to activate wifi mode else false
     * @param data         pass true to activate data mode else false
     */
    public void setNetworkConnection(boolean airplaneMode, boolean wifi, boolean data) {

        long mode = 1L;

        if (wifi) {
            mode = 2L;
        } else if (data) {
            mode = 4L;
        }

        ConnectionState connectionState = new ConnectionState(mode);
        ((AndroidDriver) getDriver()).setConnection(connectionState);
        System.out.println("Your current connection settings are :" + ((AndroidDriver) getDriver()).getConnection());
    }

    /**
     * method to get all the context handles at particular screen
     */
    public void getContext() {
        ((AppiumDriver) getDriver()).getContextHandles();
    }

    /**
     * method to set the context to required view.
     *
     * @param context view to be set
     */
    public void setContext(String context) {

        Set<String> contextNames = ((AppiumDriver) getDriver()).getContextHandles();

        if (contextNames.contains(context)) {
            ((AppiumDriver) getDriver()).context(context);
            info("Context changed successfully");
        } else {
            info(context + "not found on this page");
        }

        info("Current context" + ((AppiumDriver) getDriver()).getContext());
    }

    /**
     * method to long press on specific element by passing locator
     *
     * @param locator element to be long pressed
     */
    public void longPress(By locator) {
        try {
            WebElement element = getDriver().findElement(locator);

            TouchAction touch = new TouchAction((MobileDriver) getDriver());
            LongPressOptions longPressOptions = new LongPressOptions();
            longPressOptions.withElement(ElementOption.element(element));
            touch.longPress(longPressOptions).release().perform();
            info("Long press successful on element: " + element);
        } catch (NoSuchElementException e) {
            error("Element not found" + locator);
            throw new NoSuchElementException(e.getMessage());
        }

    }

    /**
     * method to long press on specific x,y coordinates
     *
     * @param x x offset
     * @param y y offset
     */
    public void longPress(int x, int y) {
        TouchAction touch = new TouchAction((MobileDriver) getDriver());
        PointOption pointOption = new PointOption();
        pointOption.withCoordinates(x, y);
        touch.longPress(pointOption).release().perform();
        info("Long press successful on coordinates: " + "( " + x + "," + y + " )");

    }

    /**
     * method to long press on element with absolute coordinates.
     *
     * @param locator element to be long pressed
     * @param x       x offset
     * @param y       y offset
     */
    public void longPress(By locator, int x, int y) {
        try {
            WebElement element = getDriver().findElement(locator);
            TouchAction touch = new TouchAction((MobileDriver) getDriver());
            LongPressOptions longPressOptions = new LongPressOptions();
            longPressOptions.withPosition(new PointOption().withCoordinates(x, y)).withElement(ElementOption.element(element));
            touch.longPress(longPressOptions).release().perform();
            info("Long press successful on element: " + element + "on coordinates" + "( " + x + "," + y + " )");
        } catch (NoSuchElementException e) {
            error("Element not found" + locator);
            throw new NoSuchElementException(e.getMessage());
        }

    }

    /**
     * method to swipe on the screen on provided coordinates
     *
     * @param startX   - start X coordinate to be tapped
     * @param endX     - end X coordinate to be tapped
     * @param startY   - start y coordinate to be tapped
     * @param endY     - end Y coordinate to be tapped
     * @param duration duration to be tapped
     */

    public void swipe(double startX, double startY, double endX, double endY, double duration) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        HashMap<String, Double> swipeObject = new HashMap<String, Double>();
        // swipeObject.put("touchCount", 1.0);
        swipeObject.put("startX", startX);
        swipeObject.put("startY", startY);
        swipeObject.put("endX", endX);
        swipeObject.put("endY", endY);
        swipeObject.put("duration", duration);
        js.executeScript("mobile: swipe", swipeObject);
    }

    /**
     * method to launchApp
     */

    public void launchApp() {
        getDriver().launchApp();
    }


    /**
     * method to click on Element By Name
     *
     * @param elementByName - String element name to be clicked
     */
    public void clickByName(String elementByName) {
        getDriver().findElementByName(elementByName).click();
    }

    /**
     * method to scroll down on screen from java-client 6
     *
     * @param swipeTimes       number of times swipe operation should work
     * @param durationForSwipe time duration of a swipe operation
     */
    public void scrollDown(int swipeTimes, int durationForSwipe) {
        Dimension dimension = getDriver().manage().window().getSize();
        for (int i = 0; i <= swipeTimes; i++) {
            int start = (int) (dimension.getHeight() * 0.5);
            int end = (int) (dimension.getHeight() * 0.3);
            int x = (int) (dimension.getWidth() * .5);
            new TouchAction(getDriver()).press(PointOption.point(x, start)).moveTo(PointOption.point(x, end))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(durationForSwipe)))
                    .release().perform();
        }
    }


    /**
     * Scroll to an element using its ID
     *
     * @param text text attribute of element
     * @return MobileElement
     * @throws Exception
     */
    public MobileElement android_scrollToText(String text) throws Exception {
        info("Trying to scroll to android element with text - " + text);
        MobileElement el =
                (MobileElement)
                        wait_until_MobileElementIs_Visible(
                                MobileBy.AndroidUIAutomator(
                                        "new UiScrollable(new UiSelector()).scrollIntoView(text(\""
                                                + text
                                                + "\"));"));
        return el;
    }

    /**
     * Scroll to an element using its ID
     *
     * @param text text attribute of element
     * @return MobileElement
     * @throws Exception
     */
    public void android_scrollToTextAndClick(String text) throws Exception {
        info("Trying to scroll to android element with text - " + text);
        wait_until_MobileElementIs_Visible(
                MobileBy.AndroidUIAutomator(
                        "new UiScrollable(new UiSelector()).scrollIntoView(text(\""
                                + text
                                + "\"));")).click();
    }

    /**
     * WAIT FOR ELEMENT IS VISIBLE
     *
     * @param locator element defiition
     * @return WebElement
     * @throws Exception
     */
    public WebElement wait_until_MobileElementIs_Visible(By locator) throws Exception {
        try {
            info("Waiting for element " + locator + " visibility state");
            WebDriverWait wait = new WebDriverWait(getDriver(), timeOut);
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            error("Exception occurred while trying to check visibility of element with definition " + locator);
            error(Throwables.getStackTraceAsString(e));
            throw new Exception(Throwables.getStackTraceAsString(e));
        }
    }

    /**
     * method to scroll up on screen from java-client 6
     *
     * @param swipeTimes       number of times swipe operation should work
     * @param durationForSwipe time duration of a swipe operation
     */
    public void scrollUp(int swipeTimes, int durationForSwipe) {
        Dimension dimension = getDriver().manage().window().getSize();
        for (int i = 0; i <= swipeTimes; i++) {
            int start = (int) (dimension.getHeight() * 0.3);
            int end = (int) (dimension.getHeight() * 0.5);
            int x = (int) (dimension.getWidth() * .5);
            new TouchAction(getDriver()).press(PointOption.point(x, start)).moveTo(PointOption.point(x, end))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(durationForSwipe)))
                    .release().perform();
        }
    }

    /**
     * capture screenshot of element & log into test report
     *
     * @param path_screenshot Path of the screenshot folder
     * @param testCaseName    name of the testcase or any string to save the screenshot
     * @return void
     * @throws Exception
     */
    public void captureScreenshot(String path_screenshot, String testCaseName) {
        try {
            File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
            String filename = testCaseName + "_" + UUID.randomUUID().toString();
            File targetFile = new File(path_screenshot + filename + ".jpg");
            FileUtils.copyFile(srcFile, targetFile);
        } catch (Exception e) {
            error("Failed while capturing screenshot");
            error(Throwables.getStackTraceAsString(e));
        }
    }

    /**
     * capture screenshot of element & log into test report
     *
     * @param webElementName Name of the web element
     * @param element        WebElement Object
     * @return void
     * @throws IOException
     */
    public void captureElementScreenshot(String webElementName, WebElement element) throws IOException {
        try {
            WrapsDriver wrapsDriver = (WrapsDriver) element;
            File screenshot = ((TakesScreenshot) wrapsDriver.getWrappedDriver()).getScreenshotAs(OutputType.FILE);
            Rectangle rectangle = new Rectangle(element.getRect().getX(), element.getRect().getY(), element.getRect().getHeight(), element.getRect().getWidth());
            Point location = element.getLocation();
            BufferedImage bufferedImage = ImageIO.read(screenshot);
            BufferedImage destImage = bufferedImage.getSubimage(location.x, location.y, rectangle.width, rectangle.height);
            ImageIO.write(destImage, "png", screenshot);
            File file = new File(System.getProperty("user.dir") + "/screenshots/" + webElementName + ".png");
            FileUtils.copyFile(screenshot, file);
            info("<img src=\"file:///" + System.getProperty("user.dir") + "/screenshots/" + webElementName + ".png"
                    + "\"alt='" + webElementName + "'\"/>");
        } catch (Exception e) {
            error("Failed to capture screenshot: <br/>" + e.getMessage());
            error(Throwables.getStackTraceAsString(e));
            throw new IOException(Throwables.getStackTraceAsString(e));
        }
    }

    /**
     * capture screenshot & log into test report
     *
     * @param driver   WebDriver object
     * @param testName name of the test or any name to be used for saving screenshot
     * @return String
     */
    public String captureScreenshot(WebDriver driver, String testName) {
        String randomValue = "_" + StringUtils.getRandomAlphaNumeric(5);
        String fullPath = System.getProperty("user.dir") + "/screenshots/" + testName + randomValue + ".png";
        String captured = "No";
        try {
            WebDriver augmentedDriver = new Augmenter().augment(driver);
            File source = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(source, new File(fullPath));
            return fullPath;
        } catch (IOException e) {
            error("Failed to capture screenshot: <br/>" + e.getMessage());
            error(Throwables.getStackTraceAsString(e));
            return captured;
        }
    }

    /**
     * capture screenshot & log into test report
     *
     * @param name name of the screenshot
     * @return void
     */
    public synchronized void logScreenshot(String name) {
        try {
            if (get().getAppiumDriver() != null && System.getProperty("captureScreenshotForCurrentTest").
                    equalsIgnoreCase("Yes")) {
                String captureScreenshot = captureScreenshot(get().getAppiumDriver(), name);
                if (!captureScreenshot.equalsIgnoreCase("No"))
                    info("<img src=\"file:///" + captureScreenshot + "\"alt='Screenshot'\" width=\"200\" height=\"200\"/>");
            }
        } catch (Exception e) {
            error("Exception occurred while taking the Screen to click element with definition " + name);
            error(Throwables.getStackTraceAsString(e));
        }
    }

    /**
     * method to wait for an element's attribute state
     *
     * @param targetElement element to be clickable
     * @return true if element is visible else throws TimeoutException
     * @throws TimeoutException
     */

    public boolean waitForElementAttributeToBe(By targetElement, String attributeName, String attributeValue) {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), timeOut);
            wait.until(ExpectedConditions.attributeToBe(targetElement, attributeName, attributeValue));
            return true;
        } catch (TimeoutException e) {
            System.out.println("Element is not clickable: " + targetElement);
            throw new TimeoutException(Throwables.getStackTraceAsString(e));
        }
    }

    /**
     * method to perform click operation on back button
     *
     * @param backElement element to be searched for & to perform click operation
     * @return void if operation gets through else throws NoSuchElementException or TimeoutException
     * @throws NoSuchElementException or TimeOutException
     */
    public void navigateToBackScreen(By backElement) throws Exception {
        try {
            waitForScreenLoad();
            click(backElement);
        } catch (TimeoutException e) {
            error("Failed to Navigate to :: " + backElement);
            error(Throwables.getStackTraceAsString(e));
            throw new TimeoutException(Throwables.getStackTraceAsString(e));
        } catch (NoSuchElementException e) {
            error("Failed to Navigate to :: " + backElement);
            error(Throwables.getStackTraceAsString(e));
            throw new NoSuchElementException(Throwables.getStackTraceAsString(e));
        }
    }

    /**
     * method to wait for an element to be clickable
     *
     * @param targetElement element to be clickable
     * @return boolean
     * @throws TimeoutException
     */
    public boolean waitForElementClickable(By targetElement) {
        try {
            info("Verifying readiness of the element :: " + targetElement + " to accept click operation");
            WebDriverWait wait = new WebDriverWait(getDriver(), timeOut);
            wait.until(ExpectedConditions.elementToBeClickable(targetElement));
            return true;
        } catch (TimeoutException e) {
            error("Exception occurred while verifying readiness of the element :: " + targetElement);
            error(Throwables.getStackTraceAsString(e));
            throw new TimeoutException(Throwables.getStackTraceAsString(e));
        }
    }

    /**
     * method to perform click operation on an element matching the definition provided
     *
     * @param elementDefinition element to be clicked
     * @return void
     * @throws NoSuchElementException
     */
    public void click(By elementDefinition) throws Exception {
        try {
            waitForVisibility(elementDefinition);
            waitForElementClickable(elementDefinition);
            info("Performing click operation on element :: " + elementDefinition);
            getDriver().findElement(elementDefinition).click();
        } catch (Exception e) {
            error("Exception occurred while trying to click element with definition " + elementDefinition);
            error(Throwables.getStackTraceAsString(e));
            throw new Exception(Throwables.getStackTraceAsString(e));
        }
    }

    /**
     * method to perform sendKeys operation on an element matching the definition provided
     *
     * @param elementDefinition element to be filled
     * @param textToFill        text to field
     * @return void
     * @throws NoSuchElementException
     */
    public void sendKeys(By elementDefinition, String textToFill) throws Exception {
        try {
            waitForElementClickable(elementDefinition);
            info("Performing send keys operation on element :: " + elementDefinition);
            getDriver().findElement(elementDefinition).clear();
            getDriver().findElement(elementDefinition).sendKeys(textToFill);
           /* try {
                captureElementScreenshot("Text_" + StringUtils.getRandomAlpha(5),
                        getDriver().findElement(elementDefinition));
            } catch (Exception e) {

            }*/
        } catch (Exception e) {
            error("Exception occurred while trying to fill text into element with definition " + elementDefinition);
            error(Throwables.getStackTraceAsString(e));
            throw new Exception(Throwables.getStackTraceAsString(e));
        }
    }

    /**
     * method to get attribute of an element matching the definition provided
     *
     * @param elementDefinition element to be searched for
     * @param attributeName     attribute identifier
     * @return attribute value if found else throws NoSuchElementException
     * @throws NoSuchElementException
     */
    public String getAttributeValue(By elementDefinition, String attributeName) {
        String attributeValue = null;
        try {
            info("Retrieving attribute value for attribute " + attributeName + " for the element :: " +
                    elementDefinition);
            waitForVisibility(elementDefinition);
            attributeValue = findElement(elementDefinition).getAttribute(attributeName);
            return attributeValue;
        } catch (Exception e) {
            error("Exception occurred while retrieving value of " + attributeName + " for the element with " +
                    "definition " + elementDefinition);
            error(Throwables.getStackTraceAsString(e));
            throw new NoSuchElementException(Throwables.getStackTraceAsString(e));
        }
    }

    /**
     * @param uiSelector
     * @return
     */
    static String UiScrollable(String uiSelector) {
        return "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(" + uiSelector + ".instance(0));";
    }

    /**
     * @return
     */
    public boolean swipeFromUpToBottom() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) getDriver();
            HashMap<String, String> scrollObject = new HashMap<String, String>();
            scrollObject.put("direction", "up");
            js.executeScript("mobile: scroll", scrollObject);
            System.out.println("Swipe up was Successfully done.");
            return true;
        } catch (Exception e) {
            System.out.println("swipe up was not successfull");
        }
        return false;
    }

    /**
     * @return
     */
    public boolean swipeFromBottomToUp() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) getDriver();
            HashMap<String, String> scrollObject = new HashMap<String, String>();
            scrollObject.put("direction", "down");
            js.executeScript("mobile: scroll", scrollObject);
            info("Swipe down was Successfully done");
        } catch (Exception e) {
            error("swipe down was not successfull");
        }
        return false;
    }

    /**
     * @param fromX
     * @param fromY
     * @param toX
     * @param toY
     */
    public void scroll(int fromX, int fromY, int toX, int toY) {
        TouchAction touchAction = new TouchAction((MobileDriver) getDriver());
        touchAction.press(PointOption.point(fromX, fromY)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(8000))).
                moveTo(PointOption.point(toX, toY)).release().perform();
    }

    /**
     * @param expected
     */
    public void swipeLeftUntilTextExists(String expected) {
        do {
            swipeLeft();
        } while (!getDriver().getPageSource().contains(expected));
    }

    /**
     *
     */
    public void swipeRight() {
        Dimension size = getDriver().manage().window().getSize();
        int startx = (int) (size.width * 0.9);
        int endx = (int) (size.width * 0.20);
        int starty = size.height / 2;
        new TouchAction(getDriver()).press(PointOption.point(startx, starty))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
                .moveTo(PointOption.point(endx, starty)).release().perform();
    }

    /**
     *
     */
    public void swipeLeft() {
        Dimension size = getDriver().manage().window().getSize();
        int startx = (int) (size.width * 0.8);
        int endx = (int) (size.width * 0.20);
        int starty = size.height / 2;
        new TouchAction(getDriver()).press(PointOption.point(startx, starty))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
                .moveTo(PointOption.point(endx, starty)).release();
    }

    /**
     * method to go back by Android Native back click
     */
    public void backKeyAndroid() {
        ((AndroidDriver) getDriver()).pressKey(new KeyEvent().withKey(AndroidKey.BACK));
    }

    /**
     * method to go home by Android Native home click
     */
    public void homeKeyAndroid() {
        ((AndroidDriver) getDriver()).pressKey(new KeyEvent().withKey(AndroidKey.HOME));
    }

    /**
     * method to unlock the device by Android Native unlockDevive
     */
    public void unlockDevice() {
        ((AndroidDriver) getDriver()).pressKey(new KeyEvent().withKey(AndroidKey.HOME));
    }

    /**
     * method to open notifications on Android
     */

    public void openNotifications() {
        ((AndroidDriver<WebElement>) getDriver()).openNotifications();
    }

    public void hardWait(int seconds) throws InterruptedException {
        Thread.sleep(seconds * 1000);
    }
}

