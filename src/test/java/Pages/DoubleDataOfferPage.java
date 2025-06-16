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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;

public class DoubleDataOfferPage {

    private static final Logger logger = LogManager.getLogger(DoubleDataOfferPage.class);

    private final AndroidDriver<MobileElement> driver;
    private final WebDriverWait wait;
    private final String screenshotPath;

    // STATIC locator remains as final By field
    private final By doubleDataOfferLocator = MobileBy.AccessibilityId("Double Data Offer");

    public DoubleDataOfferPage(AndroidDriver<MobileElement> driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 35);
        this.screenshotPath = System.getProperty("user.dir") + "/screenshots/";
    }

    // DYNAMIC XPath locators now as methods returning By

    private By renewalLocator() {
        return MobileBy.xpath("(//android.view.ViewGroup[contains(@content-desc, '30 Day Renewal')])[1]");
    }

    private By paymentMethodLocator() {
        return MobileBy.xpath("//android.view.ViewGroup[contains(@content-desc, 'Pay By Balance')]");
    }

    private By confirmLocator() {
        return MobileBy.xpath("//android.view.ViewGroup[contains(@content-desc, 'Confirm')]");
    }

    private By cancelLocator() {
        return MobileBy.xpath("//android.widget.Button[contains(@content-desc, 'Cancel')]/android.view.ViewGroup");
    }

    private By daysOneTimeLocator() {
        return MobileBy.xpath("(//android.view.ViewGroup[contains(@content-desc, '28 Day One Time')])[1]/android.view.ViewGroup");
    }

    private By buyPackLocator() {
        return MobileBy.xpath("//android.widget.Button[contains(@content-desc, 'Buy pack')]/android.view.ViewGroup/android.view.View");
    }

    public void doubleData() {
        try {
            clickElementWithSwipe(doubleDataOfferLocator, "Double Data Offer");

            MobileElement renewalBtn = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(renewalLocator()));
            renewalBtn.click();
            Thread.sleep(3000);

            MobileElement cancelBtn = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(cancelLocator()));
            cancelBtn.click();
            driver.navigate().back();

            MobileElement dayOneTimeBtn = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(daysOneTimeLocator()));
            dayOneTimeBtn.click();
            Thread.sleep(3000);

            MobileElement buyPackBtn = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(buyPackLocator()));
            buyPackBtn.click();

            MobileElement paymentBtn = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(paymentMethodLocator()));
            paymentBtn.click();

            MobileElement confirmBtn = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(confirmLocator()));
            confirmBtn.click();

            logger.info("Double Data Offer purchase flow completed successfully.");
        } catch (Exception e) {
            logger.error("Failed in doubleData flow: {}", e.getMessage(), e);
            takeScreenshot("DoubleDataRenewalError");
            Assert.fail("Failed in doubleData flow: " + e.getMessage());
        }
    }

    private void clickElementWithSwipe(By locator, String name) {
        int maxSwipes = 8;
        boolean found = false;

        for (int i = 0; i < maxSwipes; i++) {
            try {
                var elements = driver.findElements(locator);
                if (!elements.isEmpty()) {
                    MobileElement element = elements.get(0);
                    if (element.isDisplayed()) {
                        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
                        logger.info("Clicked on '{}'", name);
                        found = true;
                        break;
                    }
                }
                logger.debug("Swipe {}: '{}' not visible yet, performing swipe up.", i + 1, name);
                swipeUp();
                waitAfterSwipe();
            } catch (Exception e) {
                logger.warn("Swipe attempt {} for '{}' failed: {}", i + 1, name, e.getMessage(), e);
            }
        }

        if (!found) {
            logger.error("'{}' not found after {} swipes.", name, maxSwipes);
            takeScreenshot(name + "_NotFound");
            Assert.fail(name + " not found after " + maxSwipes + " swipes.");
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
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
                .moveTo(PointOption.point(startX, endY))
                .release()
                .perform();
    }

    private void waitAfterSwipe() {
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            logger.warn("Interrupted during waitAfterSwipe", e);
            Thread.currentThread().interrupt();
        }
    }

    private void takeScreenshot(String fileName) {
        try {
            File screenshot = driver.getScreenshotAs(OutputType.FILE);
            File destinationFile = new File(screenshotPath + fileName + ".png");
            Files.createDirectories(destinationFile.getParentFile().toPath());
            Files.copy(screenshot.toPath(), destinationFile.toPath());
            logger.info("Screenshot saved at: {}", destinationFile.getAbsolutePath());
        } catch (IOException e) {
            logger.error("Screenshot save failed: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Unexpected error while taking screenshot: {}", e.getMessage(), e);
        }
    }
}
