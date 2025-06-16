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

public class RoamingPage {

    private final AndroidDriver<MobileElement> driver;
    private final WebDriverWait wait;
    private static final Logger logger = LogManager.getLogger(RoamingPage.class);

    public RoamingPage(AndroidDriver<MobileElement> driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 40);
        logger.info("Initialized RoamingPage");
    }

    private final By downArrowLocator = MobileBy.AccessibilityId("card-container");
    private final By roamingLocator = MobileBy.AccessibilityId("Roaming");
    private final By closeLocator = MobileBy.AccessibilityId("Close");
    private final By indiaLocator = MobileBy.AccessibilityId("India");
    private final By sortByPriceLocator = MobileBy.AccessibilityId("Price");
    private final By lowToHighLocator = MobileBy.AccessibilityId("Low to high");
    private final By validityLocator = MobileBy.AccessibilityId("Validity");
    private final By daysLocator = MobileBy.AccessibilityId("4-7 days");
    private final By homePageBackButtonLocator = MobileBy.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup");

    private By paymentMethodLocator() {
        return MobileBy.AccessibilityId("Pay By Balance");
    }

    private By confirmLocator() {
        return MobileBy.AccessibilityId("Confirm");
    }

    private By buyPackLocator() {
        return MobileBy.xpath("//android.view.ViewGroup[contains(@content-desc, 'Buy Pack')]");
    }

    private By detailLocator() {
        return MobileBy.xpath("//android.view.ViewGroup[contains(@content-desc, 'Details')]");
    }

    private By buyPackFromDetailLocator() {
        return MobileBy.xpath("//android.widget.Button[contains(@content-desc, 'Buy pack')]");
    }

    private By noLocator() {
        return MobileBy.AccessibilityId("NO");
    }

    public void performRoamingPackFlow() {
        try {
            clickElementWithSwipe(roamingLocator, "Roaming Tab");
            Thread.sleep(3000);
            clickElement(closeLocator, "Close Button");

            Thread.sleep(5000);

            clickElement(indiaLocator, "India Button");

            Thread.sleep(20000);

            clickElement(sortByPriceLocator, "Sort by Price");
            clickElement(lowToHighLocator, "Low to High");
            clickElement(validityLocator, "Validity Filter");
            clickElement(daysLocator, "4-7 Days Filter");

            Thread.sleep(10000);

            clickElement(buyPackLocator(), "Buy Pack");
            clickElement(paymentMethodLocator(), "Pay By Balance");
            clickElement(confirmLocator(), "Confirm Payment");
            clickElement(noLocator(), "No Button to Close Confirmation");
            clickElement(detailLocator(), "Details");
            clickElement(buyPackFromDetailLocator(), "Buy Button in Details");

            reuse();
            clickElement(homePageBackButtonLocator, "Home Page Back Button/Icon");

        } catch (Exception e) {
            logger.error("Roaming pack flow failed: {}", e.getMessage(), e);
            takeScreenshot("RoamingPackFlow_Failure");
            Assert.fail("Roaming pack flow failed: " + e.getMessage());
        }
    }

    public void reuse() {
        clickElement(paymentMethodLocator(), "Pay By Balance");
        clickElement(confirmLocator(), "Confirm Payment");
        clickElement(noLocator(), "No Button to Close Confirmation");
        clickElement(homePageBackButtonLocator, "Home Page Back Button");
    }

    private void clickElementWithSwipe(By locator, String name) {
        int maxSwipes = 6;
        boolean found = false;

        for (int i = 0; i < maxSwipes; i++) {
            try {
                List<MobileElement> elements = driver.findElements(locator);
                if (!elements.isEmpty() && elements.get(0).isDisplayed()) {
                    wait.until(ExpectedConditions.elementToBeClickable(elements.get(0))).click();
                    logger.info("Clicked on: {}", name);
                    found = true;
                    break;
                }
                swipeUp();
                waitAfterSwipe();
            } catch (Exception e) {
                logger.warn("Swipe {} for {} failed: {}", i + 1, name, e.getMessage());
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
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void takeScreenshot(String name) {
        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String filename = "screenshots/" + name + "_" + timestamp + ".png";

            Files.createDirectories(Paths.get("screenshots"));
            Files.copy(srcFile.toPath(), Paths.get(filename));
            logger.info("Saved screenshot: {}", filename);
        } catch (IOException e) {
            logger.error("Failed to save screenshot: {}", e.getMessage());
        }
    }
}
