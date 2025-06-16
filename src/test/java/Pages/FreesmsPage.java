package Pages;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import utility.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FreesmsPage {

    private static final Logger logger = LogManager.getLogger(FreesmsPage.class);

    private final AndroidDriver<MobileElement> driver;
    private final WebDriverWait wait;

    public FreesmsPage(AndroidDriver<MobileElement> driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 40);
    }

    private final By freesmsLocator = MobileBy.AccessibilityId("Free SMS");
    private final By homePageBackButtonLocator = MobileBy.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup");


    private By getDownArrowLocator() {
        return By.xpath("//android.view.ViewGroup[@resource-id='card-container']");
    }

    private By getNumberLocator() {
        return By.xpath("//android.widget.EditText[contains(@text, 'Enter Number') or @index='0']");
    }

    private By getTextLocator() {
        return By.xpath("//android.widget.EditText[@text='160 characters remaining']");
    }

    private By getSendLocator() {
        return By.xpath("//android.widget.Button[@content-desc='Send']/android.view.ViewGroup/android.view.View");
    }

    public void freesms() {
        try {
            clickElementWithSwipe(getDownArrowLocator(), "Down Arrow");
        } catch (Exception e) {
            logger.error("Error in freesms(): {}", e.getMessage(), e);
            takeScreenshot("FreeSMS_Failure");
            Assert.fail("Error in freesms(): " + e.getMessage());
        }
    }

    public void sms() {
        try {
            clickElement(freesmsLocator, "Free SMS");
            enterMobileNumber(ConfigReader.getProperty("e"));
            enterText(ConfigReader.getProperty("d"));
            clickElement(getSendLocator(), "Send");
        } catch (Exception e) {
            logger.error("Error in sms() flow: {}", e.getMessage(), e);
            takeScreenshot("SMS_Flow_Failure");
            Assert.fail("Error in sms() flow: " + e.getMessage());
        }
    }

    private void enterMobileNumber(String phoneNumber) {
        try {
            MobileElement numberField = (MobileElement) wait.until(ExpectedConditions.elementToBeClickable(getNumberLocator()));
            numberField.clear();
            numberField.sendKeys(phoneNumber);
            logger.info("Entered mobile number: {}", phoneNumber);
        } catch (Exception e) {
            logger.error("Failed to enter mobile number: {}", e.getMessage(), e);
            takeScreenshot("EnterMobileNumber_Failure");
            Assert.fail("Failed to enter mobile number: " + e.getMessage());
        }
    }

    private void enterText(String text) {
        try {
            MobileElement textField = (MobileElement) wait.until(ExpectedConditions.elementToBeClickable(getTextLocator()));
            textField.clear();
            textField.sendKeys(text);
            clickElement(homePageBackButtonLocator, "Home Page Back Button/Icon");
            logger.info("Entered text: {}", text);
        } catch (Exception e) {
            logger.error("Failed to enter text: {}", e.getMessage(), e);
            takeScreenshot("EnterText_Failure");
            Assert.fail("Failed to enter text: " + e.getMessage());
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
                        logger.info("Clicked on {}", name);
                        found = true;
                        break;
                    }
                }
                logger.debug("Element '{}' not found, swiping up... (Attempt {})", name, i + 1);
                swipeUp();
                waitAfterSwipe();
            } catch (Exception e) {
                logger.warn("Swipe {} for {} failed: {}", i + 1, name, e.getMessage(), e);
            }
        }

        if (!found) {
            logger.error("{} not found after {} swipes.", name, maxSwipes);
            takeScreenshot(name + "_NotFound");
            Assert.fail(name + " not found after " + maxSwipes + " swipes.");
        }
    }

    private void clickElement(By locator, String name) {
        try {
            MobileElement element = (MobileElement) wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.click();
            logger.info("Clicked on: {}", name);
        } catch (Exception e) {
            logger.error("Failed to click on {}: {}", name, e.getMessage(), e);
            takeScreenshot(name + "_ClickFailed");
            Assert.fail("Failed to click on " + name + ": " + e.getMessage());
        }
    }

    private void swipeUp() {
        int height = driver.manage().window().getSize().height;
        int width = driver.manage().window().getSize().width;
        int startX = width / 2;
        int startY = (int) (height * 0.8);
        int endY = (int) (height * 0.3);

        logger.debug("Performing swipe up gesture.");
        new TouchAction<>(driver)
                .press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(300)))
                .moveTo(PointOption.point(startX, endY))
                .release()
                .perform();
    }

    private void waitAfterSwipe() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            logger.warn("Interrupted during swipe wait", e);
            Thread.currentThread().interrupt();
        }
    }

    private void takeScreenshot(String screenshotName) {
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File srcFile = screenshot.getScreenshotAs(OutputType.FILE);

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String destinationPath = "screenshots/" + screenshotName + "_" + timestamp + ".png";

            File screenshotDirectory = new File("screenshots");
            if (!screenshotDirectory.exists()) {
                boolean dirCreated = screenshotDirectory.mkdirs();
                if (dirCreated) {
                    logger.info("Screenshots directory created.");
                } else {
                    logger.warn("Failed to create screenshots directory.");
                }
            }

            Files.copy(srcFile.toPath(), Paths.get(destinationPath));
            logger.info("Screenshot saved at: {}", destinationPath);
        } catch (IOException e) {
            logger.error("Failed to save screenshot: {}", e.getMessage());
        }
    }
}


