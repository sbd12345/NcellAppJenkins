package Pages;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResourceExchangePage {

    private final AndroidDriver<MobileElement> driver;
    private final WebDriverWait wait;
    private static final Logger logger = LogManager.getLogger(ResourceExchangePage.class);

    private final By homePageBackButtonLocator = MobileBy.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup");

    public ResourceExchangePage(AndroidDriver<MobileElement> driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 40);
        logger.info("Initialized ResourceExchangePage");
    }

    public By getResourceExchangeLocator() {
        return MobileBy.AccessibilityId("Resource Exchange");
    }

    public By getDownArrowLocator() {
        return MobileBy.xpath("//android.view.ViewGroup[@resource-id='card-container']");
    }

    public By getNumberLocator() {
        return MobileBy.xpath("//android.widget.EditText[contains(@text, 'Enter')]");
    }

    public By getTextLocator() {
        return MobileBy.xpath("//android.widget.EditText[contains(@text,'160 characters remaining')]");
    }

    public By getSendLocator() {
        return MobileBy.xpath("//android.widget.Button[@content-desc='Send']/android.view.ViewGroup/android.view.View");
    }

    public void rs() {
        try {
            clickElementWithSwipe(getDownArrowLocator(), "Down Arrow");
        } catch (Exception e) {
            logger.error("Error in rs(): {}", e.getMessage(), e);
            takeScreenshot("rs_Error");
        }
    }

    public void s() {
        try {
            clickElement(getResourceExchangeLocator(), "Resource Exchange");
            Thread.sleep(25000);
            clickElement(getNumberLocator(), "Enter Number");
            clickElement(getTextLocator(), "Enter Message");
            clickElement(getSendLocator(), "Send");
            clickElement(homePageBackButtonLocator, "Home Page Back Button/Icon");
        } catch (Exception e) {
            logger.error("Error in s(): {}", e.getMessage(), e);
            takeScreenshot("s_Error");
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
                swipeUp();
                waitAfterSwipe();
            } catch (Exception e) {
                logger.warn("Swipe {} for {} failed: {}", i + 1, name, e.getMessage());
                takeScreenshot("SwipeFail_" + name.replaceAll("\\s+", "_") + "_Attempt" + (i + 1));
            }
        }

        if (!found) {
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
            logger.error("Failed to click on {}: {}", name, e.getMessage());
            takeScreenshot("ClickFailed_" + name.replaceAll("\\s+", "_"));
            Assert.fail("Failed to click on " + name + ": " + e.getMessage());
        }
    }

    private void swipeUp() {
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

        logger.debug("Performed swipe up");
    }

    private void waitAfterSwipe() {
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void takeScreenshot(String screenshotName) {
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

        try {
            Files.copy(srcFile.toPath(), Paths.get(destinationPath));
            logger.info("Screenshot saved at: {}", destinationPath);
        } catch (IOException e) {
            logger.error("Failed to save screenshot: {}", e.getMessage());
        }
    }
}
