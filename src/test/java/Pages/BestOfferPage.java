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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BestOfferPage {

    private final AndroidDriver<MobileElement> driver;
    private final WebDriverWait wait;
    private static final Logger logger = LoggerFactory.getLogger(BestOfferPage.class);

    public BestOfferPage(AndroidDriver<MobileElement> driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 80);
    }

  
    private By getByContentDesc(String contentDesc) {
        return By.xpath("//*[@content-desc='" + contentDesc + "']");
    }

    private By getBuyPackLocatorByIndex(int index) {
        return By.xpath("(//*[@content-desc='BUY'])[" + index + "]/android.view.ViewGroup");
    }

    private By getDetailLocatorByIndex(int index) {
        return By.xpath("(//*[@content-desc='Details'])[" + index + "]");
    }

    private By getInnerBuyButtonXPath() {
        return By.xpath("//android.widget.Button[@content-desc='BUY']/android.view.ViewGroup/android.view.View");
    }

    private By getHomePageBackButtonLocator() {
        return By.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup");
       
    }

    public void openBestOffer() {
        try {
            Thread.sleep(10000);
            clickElementWithSwipe(getByContentDesc("Best Offer"), "Best Offer Card");
        } catch (Exception e) {
            logger.error("Failed during Best Offer flow: {}", e.getMessage());
            takeScreenshot("openBestOffer_error");
            Assert.fail("Failed during Best Offer flow: " + e.getMessage());
        }
    }

    public void bestoffer() {
        try {
            Thread.sleep(20000);
            clickElement(getBuyPackLocatorByIndex(1), "Buy Pack");
            clickElement(getByContentDesc("Pay By Balance"), "Pay By Balance");
            clickElement(getByContentDesc("Confirm"), "Confirm Payment");
            clickElement(getByContentDesc("NO"), "No Button to Close Confirmation");
            clickElement(getDetailLocatorByIndex(1), "Details");
            clickElement(getInnerBuyButtonXPath(), "Buy Button in Details");
            reuse();
        } catch (Exception e) {
            logger.error("Error in bestoffer flow: {}", e.getMessage());
            takeScreenshot("bestoffer_error");
            Assert.fail("Error in bestoffer flow: " + e.getMessage());
        }
    }

    public void reuse() {
        try {
            clickElement(getByContentDesc("Pay By Balance"), "Pay By Balance");
            clickElement(getByContentDesc("Confirm"), "Confirm Payment");
            clickElement(getByContentDesc("NO"), "No Button to Close Confirmation");
            clickElement(getHomePageBackButtonLocator(), "Home Page Back Button/Icon");
        } catch (Exception e) {
            logger.error("Error in reuse flow: {}", e.getMessage());
            takeScreenshot("reuse_error");
            Assert.fail("Error in reuse flow: " + e.getMessage());
        }
    }

    private void clickElement(By locator, String elementName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).click();
        logger.info("Clicked on: {}", elementName);
    }

    private void clickElementWithSwipe(By locator, String elementName) {
        int maxSwipe = 5;
        while (maxSwipe-- > 0) {
            try {
                MobileElement element = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                element.click();
                logger.info("Clicked on: {}", elementName);
                return;
            } catch (Exception e) {
                swipeUp();
            }
        }
        throw new RuntimeException("Element not found after swiping: " + elementName);
    }

    private void swipeUp() {
        int height = driver.manage().window().getSize().height;
        int width = driver.manage().window().getSize().width;

        int startY = (int) (height * 0.8);
        int endY = (int) (height * 0.2);
        int startX = width / 2;

        new TouchAction<>(driver)
                .press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                .moveTo(PointOption.point(startX, endY))
                .release()
                .perform();
    }

    private void takeScreenshot(String fileName) {
        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String path = "screenshots/" + fileName + "_" + timestamp + ".png";
            Files.createDirectories(Paths.get("screenshots"));
            Files.copy(srcFile.toPath(), Paths.get(path));
            logger.info("Screenshot saved: {}", path);
        } catch (IOException e) {
            logger.error("Failed to save screenshot: {}", e.getMessage());
        }
    }
}
