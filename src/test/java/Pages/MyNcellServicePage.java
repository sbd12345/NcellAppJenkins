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

public class MyNcellServicePage {

    private AndroidDriver<MobileElement> driver;
    private String screenshotPath;

    public MyNcellServicePage(AndroidDriver<MobileElement> driver) {
        this.driver = driver;
        this.screenshotPath = System.getProperty("user.dir") + "/screenshots/";
    }

  
    private By byAccessibilityId(String id) {
        return MobileBy.xpath("//*[@content-desc='" + id + "']");
    }

    private By byText(String text) {
        return MobileBy.xpath("//*[@text='" + text + "']");
    }

 
    private final By pinPukQueryLocator = byAccessibilityId("PIN/PUK Query");
    private final By esimLocator = byAccessibilityId("E-sim");
    private final By emergencyServicesLocator = byAccessibilityId("Emergency Services");
    private final By tollFreeNumbersLocator = byAccessibilityId("Toll Free Numbers");
    private final By noLocator = byText("No");
    private final By submitLocator = byText("Submit");
    private final By startnowLocator = byText("Start now");

    private final By homePageBackButtonLocator = MobileBy.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup");

    public void PinPukQuery() {
        int maxSwipes = 4;
        boolean found = false;

        for (int i = 0; i < maxSwipes; i++) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, 70);
                MobileElement element = (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(pinPukQueryLocator));
                if (element.isDisplayed()) {
                    element.click();
                    Thread.sleep(8000);
                    driver.navigate().back();
                    found = true;
                    break;
                }
            } catch (Exception e) {
                swipeUp();
                System.out.println("Swiped up to find PIN/PUK Query... [" + (i + 1) + "]");
            }
        }

        if (!found) {
            takeScreenshot("PinPukQuery_NotFound");
            Assert.fail("PIN/PUK Query not found after " + maxSwipes + " swipes.");
        }
    }

    public void Esim() {
        int maxSwipes = 3;
        boolean found = false;

        for (int i = 0; i < maxSwipes; i++) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, 30);
                MobileElement element = (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(esimLocator));
                if (element.isDisplayed()) {
                    element.click();
                    found = true;
                    Thread.sleep(17000);

                    try {
                        MobileElement startNowBtn = (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(startnowLocator));
                        if (startNowBtn.isDisplayed()) {
                            startNowBtn.click();
                            System.out.println("Clicked 'Start now' button.");
                        }

                        MobileElement noBtn = (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(noLocator));
                        if (noBtn.isDisplayed()) noBtn.click();

                        MobileElement submitBtn = (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(submitLocator));
                        if (submitBtn.isDisplayed()) submitBtn.click();

                        MobileElement Btn = (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(homePageBackButtonLocator));
                        Btn.click();
                        Btn.click();

                    } catch (Exception e) {
                        Assert.fail("Start now flow failed: " + e.getMessage());
                    }
                    break;
                }
            } catch (Exception e) {
                swipeUp();
                System.out.println("Swiped up to find E-sim... [" + (i + 1) + "]");
            }
        }

        if (!found) {
            Assert.fail("E-sim option not found after " + maxSwipes + " swipes.");
        }
    }

    public void EmergencyServices() {
        int maxSwipes = 3;
        boolean found = false;

        for (int i = 0; i < maxSwipes; i++) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, 30);
                MobileElement element = (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(emergencyServicesLocator));
                if (element.isDisplayed()) {
                    element.click();
                    Thread.sleep(2000);
                    driver.navigate().back();
                    found = true;
                    break;
                }
            } catch (Exception e) {
                swipeUp();
                System.out.println("Swiped up to find Emergency Services... [" + (i + 1) + "]");
            }
        }

        if (!found) {
            takeScreenshot("EmergencyServices_NotFound");
            Assert.fail("Emergency Services not found after " + maxSwipes + " swipes.");
        }
    }

    public void TollFreeNumbers() {
        int maxSwipes = 3;
        boolean found = false;

        for (int i = 0; i < maxSwipes; i++) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, 30);
                MobileElement element1 = (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(tollFreeNumbersLocator));
                if (element1.isDisplayed()) {
                    element1.click();
                    Thread.sleep(2000);
                    driver.navigate().back();
                    found = true;
                    break;
                }
            } catch (Exception e) {
                swipeUp();
                System.out.println("Swiped up to find Toll Free Numbers... [" + (i + 1) + "]");
            }
        }

        if (!found) {
            takeScreenshot("TollFreeNumbers_NotFound");
            Assert.fail("Toll Free Numbers not found after " + maxSwipes + " swipes.");
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
