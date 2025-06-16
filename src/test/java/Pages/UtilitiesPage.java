package Pages;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UtilitiesPage {

    private static final Logger logger = LogManager.getLogger(UtilitiesPage.class);

    private final AndroidDriver<MobileElement> driver;
    private final String screenshotPath;

    public UtilitiesPage(AndroidDriver<MobileElement> driver) {
        this.driver = driver;
        this.screenshotPath = System.getProperty("user.dir") + "/screenshots/";
    }

    private final By nepaliNewsLocator = MobileBy.AccessibilityId("Nepali News");
    private final By horoscopeLocator = MobileBy.AccessibilityId("Horoscope");
    private final By goldSilverPriceLocator = MobileBy.AccessibilityId("Gold/Silver Price");
    private final By shareMarketLiveLocator = MobileBy.AccessibilityId("Share Market Live");
    private final By exchangeRateLocator = MobileBy.AccessibilityId("Exchange Rate");
    private final By eplLiveScoreLocator = MobileBy.AccessibilityId("EPL Live Score");
    private final By weatherLocator = MobileBy.AccessibilityId("Weather");


    private By weatherTodayLocator() {
        return getTabByResourceId("rc-tabs-1-tab-1");
    }

    private By weatherTonightLocator() {
        return getTabByResourceId("rc-tabs-1-tab-2");
    }

    private By horoscopeDailyTab() {
        return getTabByResourceId("rc-tabs-0-tab-1");
    }

    private By horoscopeMonthlyTab() {
        return getTabByResourceId("rc-tabs-0-tab-2");
    }

    private By horoscopeYearlyTab() {
        return getTabByResourceId("rc-tabs-0-tab-3");
    }

    private By getTabByResourceId(String resourceId) {
        return By.xpath(String.format("//android.view.View[@resource-id='%s']", resourceId));
    }

    public void openNepaliNews() {
        logger.info("Opening Nepali News");
        openUtility(nepaliNewsLocator, "Nepali News");
        sleep(8000);
        goBack();
    }

    public void openGoldSilverPrice() {
        logger.info("Opening Gold/Silver Price");
        openUtility(goldSilverPriceLocator, "Gold/Silver Price");
        sleep(12000);
        goBack();
    }

    public void openShareMarketLive() {
        logger.info("Opening Share Market Live");
        openUtility(shareMarketLiveLocator, "Share Market Live");
        sleep(12000);
        goBack();
    }

    public void openExchangeRate() {
        logger.info("Opening Exchange Rate");
        openUtility(exchangeRateLocator, "Exchange Rate");
        sleep(8000);
        goBack();
    }

    public void openEplLiveScore() {
        logger.info("Opening EPL Live Score");
        try {
            takeScreenshot("Before_Click_EPL");
            openUtility(eplLiveScoreLocator, "EPL Live Score");
            sleep(18000);
            takeScreenshot("After_Click_EPL");
            captureLog("EPL_Logcat_Output");
        } catch (Exception e) {
            logger.error("Exception during EPL Live Score: {}", e.getMessage(), e);
            takeScreenshot("EPL_Live_Score_Exception");
            Assert.fail("EPL Live Score failed: " + e.getMessage());
        } finally {
            goBack();
        }
    }

    public void openWeather() {
        logger.info("Opening Weather");
        try {
            clickElementWithSwipe(weatherLocator, "Weather");
            sleep(8000);
            clickElement(weatherTodayLocator(), "Weather Today");
            clickElement(weatherTonightLocator(), "Weather Tonight");
        } catch (Exception e) {
            logger.error("Failed in openWeather: {}", e.getMessage(), e);
            takeScreenshot("WeatherError");
            Assert.fail("Failed in openWeather: " + e.getMessage());
        } finally {
            goBack();
        }
    }

    public void openHoroscope() {
        logger.info("Opening Horoscope");
        try {
            clickElementWithSwipe(horoscopeLocator, "Horoscope");
            sleep(3000);
            clickElement(horoscopeDailyTab(), "Horoscope Daily");
            clickElement(horoscopeMonthlyTab(), "Horoscope Monthly");
            clickElement(horoscopeYearlyTab(), "Horoscope Yearly");
        } catch (Exception e) {
            logger.error("Failed in openHoroscope: {}", e.getMessage(), e);
            takeScreenshot("HoroscopeError");
            Assert.fail("Failed in openHoroscope: " + e.getMessage());
        } finally {
            goBack();
        }
    }

    private void openUtility(By locator, String name) {
        try {
            clickElementWithSwipe(locator, name);
        } catch (Exception e) {
            logger.error("Failed to open utility '{}': {}", name, e.getMessage(), e);
            takeScreenshot(name.replace(" ", "_") + "_Error");
            Assert.fail("Failed to open utility: " + name + " - " + e.getMessage());
        }
    }

    private void clickElementWithSwipe(By locator, String name) {
        int maxSwipes = 7;
        boolean found = false;

        for (int i = 0; i < maxSwipes; i++) {
            try {
                var elements = driver.findElements(locator);
                if (!elements.isEmpty() && elements.get(0).isDisplayed()) {
                    elements.get(0).click();
                    logger.info("Clicked on: {}", name);
                    found = true;
                    break;
                }
                logger.debug("Element '{}' not found, swiping up... (Attempt {})", name, i + 1);
                swipeUp();
                waitAfterSwipe();
            } catch (Exception swipeException) {
                logger.warn("Swipe attempt {} failed for '{}': {}", i + 1, name, swipeException.getMessage());
            }
        }

        if (!found) {
            logger.error("{} not found after {} swipes.", name, maxSwipes);
            takeScreenshot(name.replace(" ", "_") + "_NotFound");
            Assert.fail(name + " not found after " + maxSwipes + " swipes.");
        }
    }

    private void clickElement(By locator, String name) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 45);
            MobileElement element = (MobileElement) wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.click();
            logger.info("Clicked on: {}", name);
        } catch (Exception e) {
            logger.error("Failed to click on '{}': {}", name, e.getMessage(), e);
            takeScreenshot("ClickError_" + name.replace(" ", "_"));
            Assert.fail("Failed to click on: " + name + " - " + e.getMessage());
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
        sleep(12000);
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            logger.warn("Sleep interrupted", e);
            Thread.currentThread().interrupt();
        }
    }

    private void goBack() {
        try {
            sleep(8000);
            driver.pressKey(new KeyEvent(AndroidKey.BACK));
            logger.info("Navigated back");
        } catch (Exception e) {
            logger.warn("Back navigation failed: {}", e.getMessage());
        }
    }

    private void takeScreenshot(String fileName) {
        try {
            File screenshot = driver.getScreenshotAs(OutputType.FILE);
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            File destinationFile = new File(screenshotPath + fileName + "_" + timestamp + ".png");
            Files.createDirectories(destinationFile.getParentFile().toPath());
            Files.copy(screenshot.toPath(), destinationFile.toPath());
            logger.info("Screenshot saved: {}", destinationFile.getAbsolutePath());
        } catch (IOException e) {
            logger.error("Screenshot save error: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Unexpected screenshot error: {}", e.getMessage(), e);
        }
    }

    private void captureLog(String logName) {
        try {
            String logFilePath = screenshotPath + logName + ".txt";
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "adb logcat -d > \"" + logFilePath + "\"");
            builder.redirectErrorStream(true);
            Process process = builder.start();
            process.waitFor();
            logger.info("Log saved to: {}", logFilePath);
        } catch (IOException | InterruptedException e) {
            logger.error("Log capture failed: {}", e.getMessage(), e);
        }
    }
}


