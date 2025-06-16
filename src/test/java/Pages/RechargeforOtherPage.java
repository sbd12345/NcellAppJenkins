package Pages;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import utility.ConfigReader;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RechargeforOtherPage {

    private final AndroidDriver<MobileElement> driver;
    private final WebDriverWait wait;
    private static final Logger logger = LogManager.getLogger(RechargeforOtherPage.class);

    public RechargeforOtherPage(AndroidDriver<MobileElement> driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 60);
    }
    private final By crossIconLocator = By.xpath("//android.widget.TextView[@text='']");
    private final By rechargeforotherLocator = By.xpath("//android.widget.TextView[@text='Recharge For Others']");
    private final By MOBILE_NUMBER = By.xpath("//android.widget.EditText[contains(@text,'Mobile') or contains(@content-desc,'Mobile')]");
    private final By PINLocator = By.xpath("//android.widget.EditText[contains(@text,'PIN')]");
    private final By homePageBackButtonLocator = MobileBy.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup");

  
    private By getByContentDesc(String partialText) {
        return By.xpath("//*[contains(@content-desc,'" + partialText + "')]");
    }

    private By getByText(String partialText) {
        return By.xpath("//*[contains(@text,'" + partialText + "')]");
    }


    public void recharge() {
        try {
        	Thread.sleep(8000);
            clickElement(rechargeforotherLocator, "rechargeforother");
        //    clickElement(crossIconLocator, "crossIcon");
            clickElement(getByContentDesc("Pay Online"), "payOnline");
            clickElement(getByContentDesc("Rs."), "enterAmount");
            enterMobileNumber(ConfigReader.getProperty("number"));
            clickElement(getByContentDesc("Recharge Card"), "rechargeCard");
            enterMobileNumber(ConfigReader.getProperty("number"));
            enterPin(ConfigReader.getProperty("PIN"));
            clickElement(getByContentDesc("Proceed"), "proceed");
            clickElement(getByText("Recent Recharges"), "recentRecharge");
            logger.info("recharge() flow completed successfully");
            clickElement(homePageBackButtonLocator, "Home Page Back Button/Icon");
        } catch (Exception e) {
            logger.error("recharge() flow failed: " + e.getMessage(), e);
            takeScreenshot("rechargeFlowError");
            Assert.fail("recharge() flow failed: " + e.getMessage());
        }
    }

    private void enterMobileNumber(String phoneNumber) {
        try {
            MobileElement numberField = (MobileElement) wait.until(ExpectedConditions.elementToBeClickable(MOBILE_NUMBER));
            numberField.sendKeys(phoneNumber);
            logger.info("Entered mobile number: " + phoneNumber);
        } catch (Exception e) {
            logger.error("enterMobileNumber() failed: " + e.getMessage(), e);
            takeScreenshot("enterMobileNumberError");
            Assert.fail("Failed to enter mobile number: " + e.getMessage());
        }
    }

    private void enterPin(String pin) {
        try {
            MobileElement pinField = (MobileElement) wait.until(ExpectedConditions.elementToBeClickable(PINLocator));
            pinField.sendKeys(pin);
            logger.info("Entered PIN");
        } catch (Exception e) {
            logger.error("enterPin() failed: " + e.getMessage(), e);
            takeScreenshot("enterPinError");
            Assert.fail("Failed to enter PIN: " + e.getMessage());
        }
    }

    private void clickElementWithSwipe(By locator, String name) {
        int maxSwipes = 5;
        boolean found = false;

        for (int i = 0; i < maxSwipes; i++) {
            try {
                List<MobileElement> elements = driver.findElements(locator);
                if (!elements.isEmpty()) {
                    MobileElement element = elements.get(0);
                    if (element.isDisplayed()) {
                        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
                        logger.info("Clicked on " + name);
                        found = true;
                        break;
                    }
                }
                swipeUp();
                waitAfterSwipe();
            } catch (Exception e) {
                logger.warn("Swipe " + (i + 1) + " for " + name + " failed: " + e.getMessage());
                if (i == maxSwipes - 1) {
                    takeScreenshot(name + "SwipeError");
                }
            }
        }

        if (!found) {
            logger.error(name + " not found after " + maxSwipes + " swipes.");
            takeScreenshot(name + "NotFound");
            Assert.fail(name + " not found after " + maxSwipes + " swipes.");
        }
    }

    private void clickElement(By locator, String name) {
        try {
            MobileElement element = (MobileElement) wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.click();
            logger.info("Clicked on: " + name);
        } catch (Exception e) {
            logger.error("Failed to click on " + name + ": " + e.getMessage(), e);
            takeScreenshot(name + "ClickError");
            Assert.fail("Failed to click on " + name + ": " + e.getMessage());
        }
    }

    private void swipeUp() {
        try {
            int height = driver.manage().window().getSize().height;
            int width = driver.manage().window().getSize().width;
            int startX = width / 2;
            int startY = (int) (height * 0.8);
            int endY = (int) (height * 0.3);

            new TouchAction<>(driver)
                    .press(PointOption.point(startX, startY))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(300)))
                    .moveTo(PointOption.point(startX, endY))
                    .release()
                    .perform();

            logger.info("swipeUp() performed successfully");
        } catch (Exception e) {
            logger.error("swipeUp() failed: " + e.getMessage(), e);
            takeScreenshot("swipeUpError");
        }
    }

    private void waitAfterSwipe() {
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void takeScreenshot(String screenshotName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File src = ts.getScreenshotAs(OutputType.FILE);

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String dest = "screenshots/" + screenshotName + "_" + timestamp + ".png";

            File screenshotsDir = new File("screenshots");
            if (!screenshotsDir.exists()) {
                screenshotsDir.mkdirs();
            }

            Files.copy(src.toPath(), Paths.get(dest));
            logger.info("Screenshot saved: " + dest);
        } catch (IOException e) {
            logger.error("Failed to take screenshot: " + e.getMessage(), e);
        }
    }
}
