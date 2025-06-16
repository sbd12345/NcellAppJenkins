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

public class BuyDataPackPage {

    private final AndroidDriver<MobileElement> driver;
    private static final Logger logger = LogManager.getLogger(BuyDataPackPage.class);

    private long globalTimeoutSeconds = 90;
    private long startTime = 0;

    public BuyDataPackPage(AndroidDriver<MobileElement> driver) {
        this.driver = driver;
    }

    private final By DatapackLocator = MobileBy.AccessibilityId("Buy Data Packs");
    private final By renewelpackLocator = MobileBy.AccessibilityId("Renewal Packs");
    private final By DoubleDataOfferLocator = MobileBy.AccessibilityId("DOUBLE DATA");
    private final By sevendaysrenewelpackLocator = MobileBy.AccessibilityId("7 Day Renewal");
    private final By topsellersLocator = MobileBy.AccessibilityId("Top Sellers");
    private final By sidhaonLocator = MobileBy.AccessibilityId("Sadhain ON");
    private final By onetothreedayLocator = MobileBy.AccessibilityId("1 - 3 Days");
    private final By sevendayLocator = MobileBy.AccessibilityId("7 Days");
    private final By twentyeightdayLocator = MobileBy.AccessibilityId("28 Days");
    private final By eightyfourdayLocator = MobileBy.AccessibilityId("84 Days");
    private final By homePageBackButtonLocator = MobileBy.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup");



    public By getPartialDescButton(String partialDesc) {
        return By.xpath("//android.widget.Button[contains(@content-desc, '" + partialDesc + "')]/android.view.ViewGroup/android.view.View");
    }

    public By getPaymentMethodByDesc(String methodDesc) {
        return MobileBy.xpath("//android.view.ViewGroup[@content-desc='" + methodDesc + "']");
    }

    public By getButtonByContentDesc(String buttonDesc) {
        return MobileBy.xpath("//android.view.ViewGroup[@content-desc='" + buttonDesc + "']/android.view.ViewGroup");
    }

    public By getBuyPackByIndex(int index) {
        return MobileBy.xpath("(//android.view.ViewGroup[@content-desc='Buy Pack'])[" + index + "]/android.view.ViewGroup");
    }

    public By getBuyPackButtonInside() {
        return MobileBy.xpath("//android.widget.Button[@content-desc='Buy pack']/android.view.ViewGroup/android.view.View");
    }

    public By getDetailsByIndex(int index) {
        return MobileBy.xpath("(//android.view.ViewGroup[@content-desc='Details'])[" + index + "]");
    }

    public By getOneTimePackByLabel(String label) {
        return MobileBy.xpath("//android.view.ViewGroup[@content-desc='" + label + "']/android.view.ViewGroup");
    }

    public By getRenewalPackByLabel(String label) {
        return MobileBy.xpath("(//android.view.ViewGroup[@content-desc='" + label + "'])[1]");
    }

    public By getHomePageBackButton() {
        return MobileBy.xpath("//android.view.ViewGroup[contains(@content-desc, 'Back') or @content-desc='Navigate up']");
    }

    public void renewelpack() {
        ClickDataPack(DatapackLocator, "Datapack");
        try {
            clickElement(renewelpackLocator, "renewelpack");
            Thread.sleep(19000);
            clickElement(sevendaysrenewelpackLocator, "sevendaysrenewelpack");
            clickElement(getButtonByContentDesc("Cancel"), "Cancel Button");
            clickElement(getOneTimePackByLabel("7 Day One Time"), "sevendaysonetimerenewel");
            clickElement(getPartialDescButton("Continue with One-time"), "noo button");
            clickElement(getPaymentMethodByDesc("Pay By Balance"), "Pay By Balance");
            clickElement(getButtonByContentDesc("Confirm"), "Confirm Payment");
            clickElement(getButtonByContentDesc("NO"), "No Button");
        } catch (Exception e) {
            takeScreenshot("renewelpackError");
            Assert.fail("Failed in renewelpack: " + e.getMessage());
        }
    }

    public void Topsellers() {
        try {
            clickElement(topsellersLocator, "topsellers");
            Thread.sleep(8000);
            clickElement(getBuyPackByIndex(1), "Buy Pack");
            clickElement(getPaymentMethodByDesc("Pay By Balance"), "Pay By Balance");
            clickElement(getButtonByContentDesc("Confirm"), "Confirm");
            clickElement(getButtonByContentDesc("NO"), "No Button");
            clickElement(getDetailsByIndex(1), "Details");
            completePurchaseFlow1();
        } catch (Exception e) {
            takeScreenshot("Topsellers");
            Assert.fail("Failed in Topsellers: " + e.getMessage());
        }
    }

    public void sidhaon() {
        try {
            clickElement(sidhaonLocator, "sidhaon");
            Thread.sleep(8000);
            clickElement(sevendaysrenewelpackLocator, "sevendaysrenewelpack");
            clickElement(getButtonByContentDesc("Cancel"), "Cancel Button");
            clickElement(getOneTimePackByLabel("7 Day One Time"), "sevendaysonetimerenewel");
            clickElement(getPartialDescButton("Continue with One-time"), "noo button");
            clickElement(getPaymentMethodByDesc("Pay By Balance"), "Pay By Balance");
            clickElement(getButtonByContentDesc("Confirm"), "Confirm Payment");
            clickElement(getButtonByContentDesc("NO"), "No Button");
        } catch (Exception e) {
            takeScreenshot("sidhaon");
            Assert.fail("Failed in sidhaon: " + e.getMessage());
        }
    }

    public void onethreedays() {
        try {
            clickElement(onetothreedayLocator, "onetothreeday");
            Thread.sleep(8000);
            clickElement(getBuyPackByIndex(1), "Buy Pack");
            clickElement(getPaymentMethodByDesc("Pay By Balance"), "Pay By Balance");
            clickElement(getButtonByContentDesc("Confirm"), "Confirm");
            clickElement(getButtonByContentDesc("NO"), "No Button");
            clickElement(getDetailsByIndex(1), "Details");
            completePurchaseFlow1();
        } catch (Exception e) {
            takeScreenshot("onethreedays");
            Assert.fail("Failed in onethreedays: " + e.getMessage());
        }
    }

    public void sevenday() {
        try {
            clickElement(sevendayLocator, "seven");
            Thread.sleep(8000);
            clickElement(getBuyPackByIndex(1), "Buy Pack");
            clickElement(getPaymentMethodByDesc("Pay By Balance"), "Pay By Balance");
            clickElement(getButtonByContentDesc("Confirm"), "Confirm");
            clickElement(getButtonByContentDesc("NO"), "No Button");
            clickElement(getDetailsByIndex(1), "Details");
            completePurchaseFlow1();
        } catch (Exception e) {
            takeScreenshot("threetosevenError");
            Assert.fail("Failed in threetosevenday: " + e.getMessage());
        }
    }

    public void twentyeightdays() {
        try {
            clickElement(twentyeightdayLocator, "28 Days");
            Thread.sleep(8000);
            clickElement(getBuyPackByIndex(1), "Buy Pack");
            clickElement(getPaymentMethodByDesc("Pay By Balance"), "Pay By Balance");
            clickElement(getButtonByContentDesc("Confirm"), "Confirm");
            clickElement(getButtonByContentDesc("NO"), "No Button");
            clickElement(getDetailsByIndex(1), "Details");
            completePurchaseFlow1();
        } catch (Exception e) {
            takeScreenshot("voice28daysError");
            Assert.fail("Failed in voice28days: " + e.getMessage());
        }
    }

    public void eightyfourdays() {
        try {
            clickElement(eightyfourdayLocator, "84 Days");
            Thread.sleep(8000);
            clickElement(getBuyPackByIndex(1), "Buy Pack");
            clickElement(getPaymentMethodByDesc("Pay By Balance"), "Pay By Balance");
            clickElement(getButtonByContentDesc("Confirm"), "Confirm");
            clickElement(getButtonByContentDesc("NO"), "No Button");
            clickElement(getDetailsByIndex(1), "Details");
            completePurchaseFlow1();
            clickElement(homePageBackButtonLocator, "Home Page Back Button/Icon");
            clickElement(getHomePageBackButton(), "Home Page Back Button/Icon");
        } catch (Exception e) {
            takeScreenshot("eightyfourError");
            Assert.fail("Failed in eightyfourday: " + e.getMessage());
        }
    }

    private void completePurchaseFlow1() throws InterruptedException {
        clickElement(getBuyPackButtonInside(), "Buy Button in Details");
        clickElement(getPaymentMethodByDesc("Pay By Balance"), "Pay By Balance");
        clickElement(getButtonByContentDesc("Confirm"), "Confirm");
        clickElement(getButtonByContentDesc("NO"), "No Button");
    }

    private void ClickDataPack(By locator, String name) {
        int maxSwipes = 8;
        boolean found = false;

        for (int i = 0; i < maxSwipes; i++) {
            try {
                var elements = driver.findElements(locator);
                if (!elements.isEmpty()) {
                    MobileElement element = elements.get(0);
                    if (element.isDisplayed()) {
                        element.click();
                        found = true;
                        break;
                    }
                }
                logger.info("Swipe {}: '{}' not visible yet.", i + 1, name);
                swipeUp();
                waitAfterSwipe();
            } catch (Exception e) {
                logger.warn("Swipe failed at attempt {}: {}", i + 1, e.getMessage());
            }
        }

        if (!found) {
            takeScreenshot(name + "_NotFound");
            Assert.fail(name + " not found after " + maxSwipes + " swipes.");
        }
    }

    private void swipeUp() {
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        int startX = width / 2;
        int startY = (int) (height * 0.75);
        int endY = (int) (height * 0.25);

        new TouchAction<>(driver)
                .press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(800)))
                .moveTo(PointOption.point(startX, endY))
                .release()
                .perform();
    }

    private void waitAfterSwipe() {
        try {
            Thread.sleep(2500);
        } catch (InterruptedException ignored) {}
    }

    private void clickElement(By locator, String name) {
        startTime = System.currentTimeMillis();
        boolean clicked = false;
        Exception lastException = null;

        while ((System.currentTimeMillis() - startTime) < globalTimeoutSeconds * 1000) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, 5);
                wait.until(ExpectedConditions.elementToBeClickable(locator));
                driver.findElement(locator).click();
                logger.info("Clicked on element: {}", name);
                clicked = true;
                break;
            } catch (Exception e) {
                lastException = e;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {}
            }
        }

        if (!clicked) {
            takeScreenshot(name + "_clickFailed");
            Assert.fail("Failed to click on " + name + " after waiting " + globalTimeoutSeconds + " seconds. Last error: " + (lastException != null ? lastException.getMessage() : ""));
        }
    }

    private void takeScreenshot(String name) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String path = "screenshots/" + name + "_" + timestamp + ".png";
            Files.createDirectories(Paths.get("screenshots"));
            Files.copy(src.toPath(), Paths.get(path));
            logger.info("Screenshot taken: {}", path);
        } catch (IOException e) {
            logger.error("Failed to take screenshot: {}", e.getMessage());
        }
    }
}

