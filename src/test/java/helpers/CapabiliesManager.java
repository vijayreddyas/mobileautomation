package helpers;

import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CapabiliesManager {

    public static DesiredCapabilities capabilities = new DesiredCapabilities();
    public static Properties prop = new Properties();
    public static int explicitWaitTime;
    public static int implicitWaitTime;
    public static int defaultWaitTime;
    public static String basePackage;
    public static String appActivity;
    public static String browserName;
    public static String platformVersion;
    public static String newCommandTimeOut;
    public static String platformName;
    public static String deviceReadyTimeOut;
    public static String deviceName;
    public static String automationName;
    public static String app;

    /**
     * @throws IOException
     */
    public static void loadAndroidConfigProperties() throws IOException {
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/properties/android_app.properties");
        prop.load(fis);

        explicitWaitTime = Integer
                .parseInt(prop.getProperty("explicit.wait"));
        implicitWaitTime = Integer
                .parseInt(prop.getProperty("implicit.wait"));
        defaultWaitTime = Integer.parseInt(prop.getProperty("default.wait"));
        basePackage = prop.getProperty("base.pkg");
        appActivity = prop.getProperty("application.activity");
        deviceName = prop.getProperty("device.name");
        browserName = prop.getProperty("browser.name");
        platformName = prop.getProperty("platform.name");
        platformVersion = prop.getProperty("platform.version");
        newCommandTimeOut = prop.getProperty("new.command.timeout");
        deviceReadyTimeOut = prop.getProperty("device.ready.timeout");
        automationName = prop.getProperty("automation.name");
    }

    /**
     * @throws IOException
     */
    public static void loadiOSConfigProperties() throws IOException {
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/properties/ios_app.properties");
        prop.load(fis);

        explicitWaitTime = Integer
                .parseInt(prop.getProperty("explicit.wait"));
        implicitWaitTime = Integer
                .parseInt(prop.getProperty("implicit.wait"));
        defaultWaitTime = Integer.parseInt(prop.getProperty("default.wait"));
        deviceName = prop.getProperty("device.name");
        browserName = prop.getProperty("browser.name");
        platformName = prop.getProperty("platform.name");
        platformVersion = prop.getProperty("platform.version");
        newCommandTimeOut = prop.getProperty("new.command.timeout");
        deviceReadyTimeOut = prop.getProperty("device.ready.timeout");
        app = prop.getProperty("application.app");
    }

    /**
     * @return
     * @throws IOException
     */
    public static Capabilities androidCapabilities() throws IOException {
        loadAndroidConfigProperties();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,
                platformVersion);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,
                platformName);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,
                deviceName);
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT,
                newCommandTimeOut);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,
                automationName);
        capabilities.setCapability("clearDeviceLogsOnStart", true);
        capabilities.setCapability("appActivity",
                appActivity);
        capabilities.setCapability("appPackage",
                basePackage);
        return capabilities;
    }

    /**
     * @return
     * @throws IOException
     */
    public static Capabilities iOSCapabilities() throws IOException {
        loadiOSConfigProperties();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,
                platformVersion);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,
                platformName);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,
                deviceName);
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT,
                newCommandTimeOut);
        capabilities.setCapability(MobileCapabilityType.APP,
                app);
        return capabilities;
    }
}
