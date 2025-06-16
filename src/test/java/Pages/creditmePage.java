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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.util.List;

public class creditmePage {

    private static final Logger logger = LogManager.getLogger(creditmePage.class);

    private final AndroidDriver<MobileElement> driver;
    private final WebDriverWait wait;
    private final String screenshotPath;

    public creditmePage(AndroidDriver<MobileElement> driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 40);
        this.screenshotPath = System.getProperty("user.dir") + "/screenshots/";
    }

    // Dynamic XPath Locators using methods
    private By getCardContainerLocator() {
        return By.xpath("//android.view.ViewGroup[contains(@resource-id, 'card-container')]");
    }

    private By getCreditMeButtonLocator() {
        return MobileBy.AccessibilityId("Credit Me");
    }

    private By getNumberFieldLocator() {
        return By.xpath("//android.widget.EditText[contains(@content-desc,'Request To') or @index='0']");
    }

    private By getAmountFieldLocator() {
        return By.xpath("//*[contains(@content-desc, 'Rs')]");
    }

    private By getRequestButtonLocator() {
        return By.xpath("//*[translate(@text,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')='request']");
    }

    private By getOkButtonLocator() {
        return MobileBy.AccessibilityId("OK");
    }

    // Static locator for Home Page Back Button (keep as-is)
    private final By homePageBackButtonLocator = MobileBy.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup");

    public void openCreditMeSection() {
        try {
            clickElementWithSwipe(getCardContainerLocator(), "Down Arrow");
            logger.info("Opened Credit Me section by clicking down arrow.");
        } catch (Exception e) {
            logger.error("Failed to open Credit Me section: {}", e.getMessage(), e);
            takeScreenshot("CreditMe_OpenSectionError");
            Assert.fail("Failed to open Credit Me section: " + e.getMessage());
        }
    }

    public void performCreditRequest() {
        try {
            clickElement(getCreditMeButtonLocator(), "Credit Me");
            enterMobileNumber(ConfigReader.getProperty("number"));
            clickElement(getAmountFieldLocator(), "Enter Amount");
            clickElement(getRequestButtonLocator(), "Request");
            clickElement(getOkButtonLocator(), "OK");
            clickElement(homePageBackButtonLocator, "Home Page Back Button/Icon");
            logger.info("Performed credit request successfully.");
        } catch (Exception e) {
            logger.error("Credit request failed: {}", e.getMessage(), e);
            takeScreenshot("CreditMe_RequestError");
            Assert.fail("Credit request failed: " + e.getMessage());
        }
    }

    private void enterMobileNumber(String phoneNumber) {
        MobileElement numberField = (MobileElement) wait.until(ExpectedConditions.elementToBeClickable(getNumberFieldLocator()));
        numberField.clear();
        numberField.sendKeys(phoneNumber);
        logger.info("Entered mobile number: {}", phoneNumber);
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

    private void clickElement(By locator, String name) {
        try {
            MobileElement element = (MobileElement) wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.click();
            logger.info("Clicked on '{}'", name);
        } catch (Exception e) {
            logger.error("Failed to click on '{}': {}", name, e.getMessage(), e);
            takeScreenshot(name + "_ClickError");
            Assert.fail("Failed to click on " + name + ": " + e.getMessage());
        }
    }

    private void swipeUp() {
        int height = driver.manage().window().getSize().height;
        int width = driver.manage().window().getSize().width;
        int startX = width / 2;
        int startY = (int) (height * 0.8);
        int endY = (int) (height * 0.1);

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
            Thread.sleep(2000); // Reduced to 2 seconds for practical use
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
