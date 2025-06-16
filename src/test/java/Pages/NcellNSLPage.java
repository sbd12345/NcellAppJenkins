package Pages;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import utility.ConfigReader;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;

public class NcellNSLPage {

    private AndroidDriver<MobileElement> driver;
    private String screenshotPath;

    public NcellNSLPage(AndroidDriver<MobileElement> driver) {
        this.driver = driver;
        this.screenshotPath = System.getProperty("user.dir") + "/screenshots/";
        File screenshotDir = new File(this.screenshotPath);
        if (!screenshotDir.exists()) {
            screenshotDir.mkdirs();
        }
    }

    private By byContentDesc(String text) {
        return MobileBy.xpath("//android.view.ViewGroup[@content-desc=\"" + text + "\"]");
    }

    private By byContainsContentDesc(String partialText) {
        return MobileBy.xpath("//android.view.ViewGroup[contains(@content-desc, \"" + partialText + "\")]");
    }

    private By byResourceIdAndText(String resourceId, String text) {
        return MobileBy.xpath("//android.widget.Button[@resource-id=\"" + resourceId + "\" and @text=\"" + text + "\"]");
    }

    private By byResourceId(String resourceId) {
        return MobileBy.xpath("//*[@resource-id=\"" + resourceId + "\"]");
    }

    private By byText(String text) {
        return MobileBy.xpath("//*[@text=\"" + text + "\"]");
    }

    private final By clickLocator = MobileBy.xpath("//android.view.View[@resource-id=\"player-control-overlay\"]/android.view.View[1]/android.widget.TextView[2]");

  /*  public void NslLiveMatch() {
        int maxSwipes = 3;
        boolean found = false;

        for (int i = 0; i < maxSwipes; i++) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, 60);
                MobileElement element = (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(byContentDesc("NSL Live Match")));
                if (element.isDisplayed()) {
                    element.click();
                    MobileElement element2 = (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(clickLocator));
                    if (element2.isDisplayed()) {
                        element2.click();
                        Thread.sleep(2000);
                        driver.navigate().back();
                    }
                    Thread.sleep(1500);
                    found = true;
                    break;
                }
            } catch (Exception e) {
                swipeUp();
                System.out.println("Swiped up to look for NSL Live Match... [" + (i + 1) + "]");
            }
        }

        if (!found) {
            takeScreenshot("NSLLiveMatch_NotFound");
            Assert.fail("NSL Live Match not found after " + maxSwipes + " swipes.");
        }
    }      */

    public void Spinandwin() {
        int maxSwipes = 3;
        boolean found = false;

        for (int i = 0; i < maxSwipes; i++) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, 50);
                MobileElement element = (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(byContentDesc("Spin & Win")));
                if (element.isDisplayed()) {
                    element.click();

                    MobileElement element3 = (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(byResourceId("spinButton")));
                    if (element3.isDisplayed()) {
                    	Thread.sleep(5000);
                        element3.click();
                        Thread.sleep(4000);
                       driver.navigate().back();
                        Thread.sleep(2000);
                    }
                    Thread.sleep(1500);
                    found = true;
                    break;
                }
            } catch (Exception e) {
                swipeUp();
                System.out.println("Swiped up to look for Spin and Win... [" + (i + 1) + "]");
            }
        }

        if (!found) {
            takeScreenshot("SpinAndWin_NotFound");
            Assert.fail("Spin and Win not found after " + maxSwipes + " swipes.");
        }
    }

    public void FantasyLeague() {
        int maxSwipes = 3;
        boolean found = false;

        for (int i = 0; i < maxSwipes; i++) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, 40);
                MobileElement element = (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(byContentDesc("Fantasy League")));
                if (element.isDisplayed()) {
                    element.click();
                    Thread.sleep(20000);
                    takeScreenshot("FantasyLeague_Error_Page");
                    throw new AssertionError("Test failed: Error page appears after clicking Fantasy League.");
                }
            } catch (Exception e) {
                swipeUp();
            }
        }

        if (!found) {
            takeScreenshot("FantasyLeague_NotFound");
            throw new AssertionError("Fantasy League not found after " + maxSwipes + " swipes.");
        }
    }

    public void DigitalLeague() {
        int maxSwipes = 3;
        boolean found = false;

        for (int i = 0; i < maxSwipes; i++) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, 50);
                MobileElement element = (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(byContentDesc("Digital League")));
                if (element.isDisplayed()) {
                    element.click();

                    MobileElement plan = (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(
                            byResourceIdAndText("submit", "6 MONTHS PACK @ RS.756 / 6 MONTHS")));
                    if (plan.isDisplayed()) {
                        plan.click();
                    }

                    MobileElement phoneField = (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(
                            byResourceId("msisdnInput")));
                    if (phoneField.isDisplayed()) {
                        phoneField.sendKeys(ConfigReader.getProperty("number"));
                    }

                    MobileElement submitBtn = (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(
                            byResourceId("mySubmit")));
                    if (submitBtn.isDisplayed()) {
                        submitBtn.click();
                        driver.navigate().back();
                        Thread.sleep(2000);
                    }

                    Thread.sleep(1500);
                    found = true;
                    break;
                }
            } catch (Exception e) {
                swipeUp();
                System.out.println("Swiped up to look for DigitalLeague... [" + (i + 1) + "]");
            }
        }

        if (!found) {
            takeScreenshot("DigitalLeague_NotFound");
            Assert.fail("DigitalLeague not found after " + maxSwipes + " swipes.");
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
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(800)))
                .moveTo(PointOption.point(startX, endY))
                .release()
                .perform();
    }

    private void takeScreenshot(String fileName) {
        try {
            File screenshot = driver.getScreenshotAs(OutputType.FILE);
            File destinationFile = new File(screenshotPath + fileName + ".png");
            Files.copy(screenshot.toPath(), destinationFile.toPath());
            System.out.println("Screenshot saved: " + destinationFile.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Failed to save screenshot.");
            e.printStackTrace();
        }
    }
}


