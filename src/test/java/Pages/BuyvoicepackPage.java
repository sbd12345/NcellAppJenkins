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

public class BuyvoicepackPage {

    private final AndroidDriver<MobileElement> driver;
    private final WebDriverWait wait;
    private final String screenshotPath;
    private static final Logger logger = LoggerFactory.getLogger(BuyvoicepackPage.class);

    private long globalTimeoutSeconds = 90; 
    private long startTime = 0;

    public BuyvoicepackPage(AndroidDriver<MobileElement> driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 30);
        this.screenshotPath = System.getProperty("user.dir") + "/screenshots/";
    }


    private final String contentDescXPathTemplate = "//*[@content-desc='%s']";
    private final String contentDescIndexedXPathTemplate = "(//*[@content-desc='%s'])[%d]";

    private final By voiceLocator = By.xpath(String.format(contentDescXPathTemplate, "Buy Voice Packs"));
    private final By allProductLocator = By.xpath(String.format(contentDescXPathTemplate, "All Products"));
    private final By specialOfferLocator = By.xpath(String.format(contentDescXPathTemplate, "Special Offer"));
    private final By hourlyOneDayLocator = By.xpath(String.format(contentDescXPathTemplate, "Hourly - 1 Day"));
    private final By threeToSevenDayLocator = By.xpath(String.format(contentDescXPathTemplate, "3 - 7 Days"));
    private final By twentyEightDayLocator = By.xpath(String.format(contentDescXPathTemplate, "28 Days"));
    private final By eightyFourDayLocator = By.xpath(String.format(contentDescXPathTemplate, "84 Days"));
    private final By indiaIldLocator = By.xpath(String.format(contentDescXPathTemplate, "India + ILD"));
    private final By renewalLocator = By.xpath(String.format(contentDescIndexedXPathTemplate, "28 Day Renewal", 1));
    private final By paymentMethodLocator = By.xpath(String.format(contentDescXPathTemplate, "Pay By Balance"));
    private final By confirmLocator = By.xpath(String.format(contentDescXPathTemplate, "Confirm"));
    private final By cancelLocator = By.xpath(String.format(contentDescXPathTemplate, "Cancel") + "//android.view.ViewGroup");
    private final By twentyEightDayOneTimeLocator = By.xpath(String.format(contentDescIndexedXPathTemplate, "28 Day One Time", 1) + "//android.view.ViewGroup");
    private final By buyPackLocator = By.xpath(String.format(contentDescIndexedXPathTemplate, "Buy Pack", 1) + "//android.view.ViewGroup");
    private final By buyPackLocatorDetails = By.xpath(contentDescXPathTemplate.replace("%s", "Buy pack") + "//android.view.ViewGroup/android.view.View");
    private final By noLocator = By.xpath(contentDescXPathTemplate.replace("%s", "NO") + "//android.view.ViewGroup");
    private final By detailLocator = By.xpath(String.format(contentDescIndexedXPathTemplate, "Details", 1));
    private final By buyPackLocatorSimple = By.xpath(contentDescXPathTemplate.replace("%s", "Buy Pack") + "//android.view.ViewGroup");
    private final By detailSimpleLocator = By.xpath(contentDescXPathTemplate.replace("%s", "Details"));
    private final By nooLocator = By.xpath("//android.widget.Button[contains(@content-desc, 'Continue with One-time')]/android.view.ViewGroup/android.view.View");
    private final By homePageBackButtonLocator = MobileBy.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup");


    public void allProductFlow() {
        startTime = System.currentTimeMillis();
        clickElementWithSwipe(voiceLocator, "Buy Voice Packs");
        try {
        	Thread.sleep(25000);
            clickElement(allProductLocator, "All Products");
            clickElement(renewalLocator, "28 Day Renewal");
            Thread.sleep(3000);
            clickElement(cancelLocator, "Cancel");
            clickElement(twentyEightDayOneTimeLocator, "28 Day One Time");
            Thread.sleep(3000);
            clickElement(buyPackLocatorDetails, "Buy Button");
            clickElement(paymentMethodLocator, "Pay By Balance");
            clickElement(confirmLocator, "Confirm Payment");
            clickElement(noLocator, "No Button");
        } catch (Exception e) {
            takeScreenshot("allProductFlowError");
            Assert.fail("Failed in allProductFlow: " + e.getMessage());
        }
    }

    public void specialOfferFlow() {
        startTime = System.currentTimeMillis();
        try {
            clickElement(specialOfferLocator, "Special Offer");
            clickElement(buyPackLocator, "Buy Pack");
            clickElement(paymentMethodLocator, "Pay By Balance");
            clickElement(confirmLocator, "Confirm");
            clickElement(noLocator, "No Button");
            clickElement(detailLocator, "Details");
            completePurchaseFlow();
        } catch (Exception e) {
            takeScreenshot("specialOfferFlowError");
            Assert.fail("Failed in specialOfferFlow: " + e.getMessage());
        }
    }

    public void hourlyOneDayFlow() {
        startTime = System.currentTimeMillis();
        try {
            Thread.sleep(5000);
            clickElement(hourlyOneDayLocator, "Hourly - 1 Day");
            Thread.sleep(7000);
            clickElement(buyPackLocator, "Buy Pack");
            clickElement(paymentMethodLocator, "Pay By Balance");
            clickElement(confirmLocator, "Confirm");
            clickElement(noLocator, "No Button");
            clickElement(detailLocator, "Details");
            completePurchaseFlow();
        } catch (Exception e) {
            takeScreenshot("hourlyOneDayFlowError");
            Assert.fail("Failed in hourlyOneDayFlow: " + e.getMessage());
        }
    }

    public void threeToSevenDayFlow() {
        startTime = System.currentTimeMillis();
        try {
            clickElement(threeToSevenDayLocator, "3 - 7 Days");
            Thread.sleep(8000);
            clickElement(buyPackLocator, "Buy Pack");
            clickElement(paymentMethodLocator, "Pay By Balance");
            clickElement(confirmLocator, "Confirm");
            clickElement(noLocator, "No Button");
            clickElement(detailLocator, "Details");
            completePurchaseFlow();
        } catch (Exception e) {
            takeScreenshot("threeToSevenDayFlowError");
            Assert.fail("Failed in threeToSevenDayFlow: " + e.getMessage());
        }
    }

    public void twentyEightDaysFlow() {
        startTime = System.currentTimeMillis();
        try {
            clickElement(twentyEightDayLocator, "28 Days");
            Thread.sleep(8000);
            clickElement(renewalLocator, "28 Day Renewal");
            Thread.sleep(3000);
            clickElement(cancelLocator, "Cancel");
            clickElement(twentyEightDayOneTimeLocator, "28 Day One Time");
            Thread.sleep(3000);
            clickElement(nooLocator, "Cancel");
            clickElement(paymentMethodLocator, "Pay By Balance");
            clickElement(confirmLocator, "Confirm");
            clickElement(noLocator, "No Button");
        } catch (Exception e) {
            takeScreenshot("twentyEightDaysFlowError");
            Assert.fail("Failed in twentyEightDaysFlow: " + e.getMessage());
        }
    }

    public void eightyFourDaysFlow() {
        startTime = System.currentTimeMillis();
        try {
            clickElement(eightyFourDayLocator, "84 Days");
            Thread.sleep(8000);
            clickElement(buyPackLocatorSimple, "Buy Pack");
            clickElement(paymentMethodLocator, "Pay By Balance");
            clickElement(confirmLocator, "Confirm");
            clickElement(noLocator, "No Button");
            clickElement(detailSimpleLocator, "Details");
            completePurchaseFlow();
        } catch (Exception e) {
            takeScreenshot("eightyFourDaysFlowError");
            Assert.fail("Failed in eightyFourDaysFlow: " + e.getMessage());
        }
    }

    public void indiaIldFlow() {
        startTime = System.currentTimeMillis();
        try {
            clickElement(indiaIldLocator, "India + ILD");
            Thread.sleep(8000);
            clickElement(buyPackLocator, "Buy Pack");
            clickElement(paymentMethodLocator, "Pay By Balance");
            clickElement(confirmLocator, "Confirm");
            clickElement(noLocator, "No Button");
            clickElement(detailLocator, "Details");
            completePurchaseFlow();
            clickElement(homePageBackButtonLocator, "Home Page Back Button/Icon");
        } catch (Exception e) {
            takeScreenshot("indiaIldFlowError");
            Assert.fail("Failed in indiaIldFlow: " + e.getMessage());
        }
    }

    private void completePurchaseFlow() throws InterruptedException {
        clickElement(buyPackLocatorDetails, "Buy Button in Details");
        clickElement(paymentMethodLocator, "Pay By Balance");
        clickElement(confirmLocator, "Confirm");
        clickElement(noLocator, "No Button");
    }

    private void clickElementWithSwipe(By locator, String name) {
        int maxSwipes = 8;
        boolean found = false;

        for (int i = 0; i < maxSwipes; i++) {
            if (isTimedOut()) {
                takeScreenshot("Timeout_" + name.replace(" ", "_"));
                Assert.fail("Test timed out while trying to find: " + name);
            }

            try {
                var elements = driver.findElements(locator);
                if (!elements.isEmpty()) {
                    MobileElement element = elements.get(0);
                    if (element.isDisplayed()) {
                        element.click();
                        logger.info("Clicked on {}", name);
                        found = true;
                        break;
                    }
                }
                logger.info("Swipe {}: '{}' not visible yet.", (i + 1), name);
                swipeUp();
                waitAfterSwipe();
            } catch (Exception e) {
                logger.warn("Swipe failed at attempt {}: {}", (i + 1), e.getMessage());
            }
        }

        if (!found) {
            takeScreenshot(name + "_NotFound");
            Assert.fail(name + " not found after " + maxSwipes + " swipes.");
        }
    }

    private void swipeUp() {
        int height = driver.manage().window().getSize().height;
        int width = driver.manage().window().getSize().width;
        int startX = width / 2;
        int startY = (int) (height * 0.8);
        int endY = (int) (height * 0.1);

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
            Thread.currentThread().interrupt();
        }
    }

    public void takeScreenshot(String screenshotName) {
        try {
            File srcFile = driver.getScreenshotAs(OutputType.FILE);
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String destinationPath = screenshotPath + screenshotName + "_" + timestamp + ".png";

            File screenshotDirectory = new File(screenshotPath);
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
        } catch (Exception e) {
            logger.error("Unexpected error while taking screenshot: {}", e.getMessage());
        }
    }

    private void clickElement(By locator, String elementName) {
        if (isTimedOut()) {
            takeScreenshot("Timeout_" + elementName.replace(" ", "_"));
            Assert.fail("Test timed out while waiting for: " + elementName);
        }

        try {
            MobileElement element = (MobileElement) wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.click();
            logger.info("Clicked: {}", elementName);
        } catch (Exception e) {
            takeScreenshot("Error_" + elementName.replace(" ", "_"));
            Assert.fail("Failed to click on: " + elementName + " - " + e.getMessage());
        }
    }

    private boolean isTimedOut() {
        long elapsedSeconds = (System.currentTimeMillis() - startTime) / 1000;
        return elapsedSeconds > globalTimeoutSeconds;
    }
}   