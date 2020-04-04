package helpers;

import base.TestBase;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import java.io.File;

public class AppiumServer extends TestBase {
    public static AppiumDriverLocalService service;
    public static AppiumServiceBuilder builder;
    private static String date = FormatDates.getCurrentDateTime();

    /**
     * @param appiumAddress
     * @param port
     * @throws InterruptedException
     */
    public static void startServer(String appiumAddress, int port) throws InterruptedException {
        Boolean flag = false;
        //Build the Appium service
        builder = new AppiumServiceBuilder().withArgument(GeneralServerFlag.RELAXED_SECURITY).
                withArgument(GeneralServerFlag.LOG_LEVEL, "info")
                .withLogFile(new File(System.getProperty("user.dir") + "/appiumLogs/" + date + "/appiumserverlog.txt"))
                .withIPAddress(appiumAddress)
                .usingPort(port);
        //Start the server with the builder
        service = AppiumDriverLocalService.buildService(builder);
        service.start();

        //verify if the server is started
        if (service.isRunning())
            flag = true;

        for (int i = 1; i <= 5; i++) {
            if (flag) {
                commonLog.info("[TestBase] Appium Server is started, hence registering the driver");
                break;
            } else {
                Thread.sleep(1000);
            }
        }
    }

    /**
     *
     */
    public static void stopServer() {
        service.stop();
    }
}


