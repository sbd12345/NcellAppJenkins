package Pages;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

import utility.ConfigReader;

public class RechargePage {
    private AndroidDriver<MobileElement> driver;
    private static final Logger logger = LogManager.getLogger(RechargePage.class);

    public RechargePage(AndroidDriver<MobileElement> driver) {
        this.driver = driver;
    }

    private void waitFor(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            logger.error("Interrupted during wait", e);
            Thread.currentThread().interrupt();
        }
    }

    public void swipeUp() {
        try {
            int height = driver.manage().window().getSize().height;
            int width = driver.manage().window().getSize().width;
            int startY = (int) (height * 0.8);
            int endY = (int) (height * 0.2);
            int startX = width / 2;

            new TouchAction<>(driver)
                .press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(800)))
                .moveTo(PointOption.point(startX, endY))
                .release()
                .perform();

            logger.info("swipeUp() performed successfully");
        } catch (Exception e) {
            logger.error("swipeUp() failed", e);
            takeScreenshot("swipeUpError");
        }
    }

    private By rechargeButton() {
        return By.xpath("(//android.widget.TextView[contains(@text, 'Recharge')])[1]");
    }

    private By payOnlineButton() {
        return By.xpath("//android.view.ViewGroup[contains(@content-desc,'Pay Online')]/android.view.ViewGroup");
    }

    private By amountField() {
        return By.xpath("//android.view.ViewGroup[contains(@content-desc,'Rs.')]");
    }

    private By confirmRechargeButton() {
        return By.xpath("//android.widget.Button[contains(@content-desc,'Recharge')]/android.view.ViewGroup/android.view.View");
    }

    private By confirmationDialog() {
        return By.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]//android.view.ViewGroup[2]/android.view.ViewGroup");
    }

    private By yesAlertButton() {
        return By.xpath("//android.view.ViewGroup[@content-desc=\"YES\"]");
    }

    private By rechargeCardButton() {
        return By.xpath("//android.view.ViewGroup[contains(@content-desc,'Recharge Card')]/android.view.ViewGroup");
    }

    private By pinInputField() {
        return By.xpath("//android.widget.EditText[contains(@text,'16 digit PIN')]");
    }

    private By proceedButton() {
        return By.xpath("//android.view.ViewGroup[contains(@content-desc,'Proceed')]/android.view.ViewGroup");
    }

    public void rechargeClick() {
        try {
            driver.findElement(rechargeButton()).click();
            logger.info("rechargeClick() - Recharge button clicked");
        } catch (Exception e) {
            logger.error("rechargeClick() failed", e);
            takeScreenshot("rechargeClickError");
        }
    }

    public void payOnline() {
        waitFor(9000);
        try {
            driver.findElement(payOnlineButton()).click();
            logger.info("payOnline() - Pay Online button clicked");
        } catch (Exception e) {
            logger.error("payOnline() - Pay Online button click failed", e);
            takeScreenshot("payOnlineBtnError");
        }

        waitFor(7000);
        try {
            driver.findElement(amountField()).click();
            logger.info("payOnline() - Selected amount");
        } catch (Exception e) {
            logger.error("payOnline() - Amount field failed", e);
            takeScreenshot("payOnlineAmountError");
        }

        waitFor(4000);
        try {
            driver.findElement(confirmRechargeButton()).click();
            Thread.sleep(10000);

            driver.findElement(confirmationDialog()).click();
            Thread.sleep(5000);
            driver.findElement(yesAlertButton()).click();

            logger.info("payOnline() - Recharge process completed");
        } catch (Exception e) {
            logger.error("payOnline() - Recharge confirmation failed", e);
            takeScreenshot("payOnlineConfirmError");
        }
    }

    public void rechargeCard() {
        waitFor(7000);
        try {
            driver.findElement(rechargeCardButton()).click();
            logger.info("rechargeCard() - Recharge Card clicked");

            waitFor(5000);
            driver.findElement(pinInputField()).sendKeys(ConfigReader.getProperty("PIN"));
            logger.info("rechargeCard() - PIN entered");
            
            
        
          //  driver.findElement(proceedButton()).click();
          //  waitFor(5000);
          //  driver.findElement(confirmationDialog()).click();
            
            driver.findElement(confirmationDialog()).click();
        } catch (Exception e) {
            logger.error("rechargeCard() failed", e);
            takeScreenshot("rechargeCardError");
        }
    }

    public void takeScreenshot(String screenshotName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File src = ts.getScreenshotAs(OutputType.FILE);

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String dest = "screenshots/" + screenshotName + "_" + timestamp + ".png";

            File screenshotsDir = new File("screenshots");
            if (!screenshotsDir.exists()) {
                screenshotsDir.mkdirs();
            }

            Files.copy(src.toPath(), Paths.get(dest));
            logger.info("Screenshot saved: " + dest);
        } catch (IOException e) {
            logger.error("Failed to take screenshot: " + e.getMessage(), e);
        }
    }
}
