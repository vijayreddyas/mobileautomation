package base;

import io.appium.java_client.AppiumDriver;

public class TestContext {
    public AppiumDriver dr = null;
    private AppiumDriver driver = null;

    // Thread local variable containing each thread's WebDriver object
    private static final ThreadLocal<TestContext> thread = new ThreadLocal<TestContext>() {
        protected TestContext initialValue() {
            return new TestContext();
        }
    };

    protected AppiumDriver getDriver() {
        return getAppiumDriver();
    }

    protected TestContext get() {
        return thread.get();
    }

    public AppiumDriver getAppiumDriver() {
        return thread.get().driver;
    }

    public void setAppiumDriver(AppiumDriver driver) {
        thread.get().driver = driver;
    }
}
