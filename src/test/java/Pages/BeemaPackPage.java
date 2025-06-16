package Pages;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

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

public class BeemaPackPage {

    private static final Logger logger = LogManager.getLogger(BeemaPackPage.class);

    private final AndroidDriver<MobileElement> driver;
    private final WebDriverWait wait;

    public BeemaPackPage(AndroidDriver<MobileElement> driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 80);
    }

    private final By beemapackLocator = MobileBy.AccessibilityId("Beema Packs");
    private final By buyPackLocator = getDynamicXpath("(//android.view.ViewGroup[@content-desc='Buy Pack'])[1]/android.view.ViewGroup");
    private final By paymentMethodLocator = getDynamicXpath("//android.view.ViewGroup[@content-desc='Pay By Balance']");
    private final By confirmLocator = getDynamicXpath("//android.view.ViewGroup[@content-desc='Confirm']");
    private final By noLocator = getDynamicXpath("//android.view.ViewGroup[@content-desc='NO']/android.view.ViewGroup");
    private final By detailLocator = getDynamicXpath("(//android.view.ViewGroup[@content-desc='Details'])[1]");
    private final By buyPackLocator1 = getDynamicXpath("//android.widget.Button[@content-desc='Buy pack']/android.view.ViewGroup/android.view.View");

    public void beemapack() {
        try {
            logger.info("Attempting to locate and click Beema Pack using swipe.");
            clickElementWithSwipe(beemapackLocator, "Beema Pack");
        } catch (Exception e) {
            logger.error("Error in beemapack(): {}", e.getMessage(), e);
        }
    }

    public void beema() throws InterruptedException {
        logger.info("Starting beema() pack purchase flow.");
        clickElement(beemapackLocator, "Beema Pack");
        clickElement(buyPackLocator, "Buy Pack");
        Thread.sleep(19000);
        clickElement(buyPackLocator, "Buy Pack");
        clickElement(paymentMethodLocator, "Pay By Balance");
        clickElement(confirmLocator, "Confirm Payment");
        clickElement(noLocator, "No Button to Close Confirmation");
        clickElement(detailLocator, "Details");
        clickElement(buyPackLocator1, "Buy Button in Details");

        reuse();
    }

    public void reuse() {
        logger.info("Reusing payment flow in reuse() method.");
        clickElement(paymentMethodLocator, "Pay By Balance");
        clickElement(confirmLocator, "Confirm Payment");
        clickElement(noLocator, "No Button to Close Confirmation");
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
                        logger.info("Clicked on: {}", name);
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
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            logger.warn("Interrupted during swipe wait", e);
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

    private By getDynamicXpath(String xpath) {
        return MobileBy.xpath(xpath);
    }
}

