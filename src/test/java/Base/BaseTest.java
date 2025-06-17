package Base;

import java.lang.reflect.Method;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

import utility.ConfigReader;
import utility.ExtentReportManager;

public class BaseTest {

    protected AndroidDriver<MobileElement> driver;
    protected ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @BeforeSuite
    public void initializeReport() {
        extent = ExtentReportManager.getInstance();
    }

    @BeforeClass
    public void setup() throws Exception {
        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability("platformName", ConfigReader.getProperty("PlatformName"));
        caps.setCapability("deviceName", ConfigReader.getProperty("deviceName"));
        caps.setCapability("appPackage", ConfigReader.getProperty("appPackage"));
        caps.setCapability("appActivity", ConfigReader.getProperty("appActivity"));
        caps.setCapability("automationName", ConfigReader.getProperty("automationName"));
        caps.setCapability("noReset", Boolean.parseBoolean(ConfigReader.getProperty("noReset")));
        caps.setCapability("dontStopAppOnReset", true);

       
        caps.setCapability("uiautomator2ServerLaunchTimeout", 60000);
        caps.setCapability("appWaitDuration", 60000);

        // 🚀 Launch the Appium server session
        URL serverUrl = new URL(ConfigReader.getProperty("appiumServer"));
        driver = new AndroidDriver<>(serverUrl, caps);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterSuite
    public void closeReport() {
        extent.flush();
    }

    public static ExtentTest getTest() {
        return test.get();
    }

    public ExtentReports getExtent() {
        return extent;
    }

    public static void setTest(ExtentTest testInstance) {
        test.set(testInstance);
    }
}
