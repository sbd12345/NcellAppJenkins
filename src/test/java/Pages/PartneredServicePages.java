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

public class PartneredServicePages {

    private static final Logger logger = LogManager.getLogger(PartneredServicePages.class);
    private AndroidDriver<MobileElement> driver;

    public PartneredServicePages(AndroidDriver<MobileElement> driver) {
        this.driver = driver;
    }

  
    private By getByContentDesc(String contentDesc) {
        return MobileBy.xpath("//*[@content-desc='" + contentDesc + "']");
    }

    private By getByResourceId(String resourceId) {
        return MobileBy.xpath("//*[@resource-id='" + resourceId + "']");
    }

    private final By hungamaLocator = getByContentDesc("Hungama");
    private final By buyPlanLocator = getByResourceId("plan_218");
    private final By numberLocator = getByResourceId("consentform");
    private final By submitLocator = getByResourceId("mySubmit");
    private final By unlimitedContentsLocator = getByContentDesc("Unlimited Contents");
    private final By planLocator = getByContentDesc("Subscribe Now SMS Guru 6 months @648 NPR");
    private final By meroSchoolLocator = getByContentDesc("Mero School");
    private final By courseLocator = getByContentDesc("216");
    private final By homePageBackButtonLocator = MobileBy.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup");


    public void openHungama() {
        logger.info("Opening Hungama service...");
        clickPartnerService(hungamaLocator, "Hungama");

        WebDriverWait wait = new WebDriverWait(driver, 30);

        try {
            Thread.sleep(3000);

            try {
                MobileElement buyPlanElement = (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(buyPlanLocator));
                if (buyPlanElement.isDisplayed()) {
                    logger.info("Buy Plan found for Hungama, clicking Buy Plan.");
                    buyPlanElement.click();
                    Thread.sleep(5000);
                }
            } catch (Exception e) {
                logger.warn("Buy Plan not found, continuing to number entry...");
            }

            try {
                MobileElement numberElement = (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(numberLocator));
                if (numberElement.isDisplayed()) {
                    String number = ConfigReader.getProperty("number");
                    logger.info("Entering number: {}", number);
                    numberElement.sendKeys(number);
                } else {
                    throw new Exception("Number field not visible.");
                }
            } catch (Exception e) {
                logger.error("Error in number input field.", e);
                takeScreenshot("Hungama_NumberField_Error");
                Assert.fail("Hungama: Number input failed.");
            }

            try {
                MobileElement submitElement = (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(submitLocator));
                if (submitElement.isDisplayed()) {
                    logger.info("Clicking submit button.");
                    submitElement.click();
                } else {
                    throw new Exception("Submit button not visible.");
                }
            } catch (Exception e) {
                logger.error("Error in submit button.", e);
                takeScreenshot("Hungama_Submit_Error");
                Assert.fail("Hungama: Submit failed.");
            }

        } catch (Exception e) {
            logger.error("Unexpected exception in Hungama flow.", e);
            takeScreenshot("Hungama_Unexpected_Exception");
            Assert.fail("Hungama: Unexpected error.");
        }

        try {
            MobileElement abElement = (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(homePageBackButtonLocator));
            abElement.click();
            logger.info("Navigated back after Hungama.");
        } catch (Exception e) {
            logger.error("Failed to navigate back from Hungama.", e);
            takeScreenshot("Hungama_Back_Error");
            Assert.fail("Hungama: Back button click failed.");
        }
    }

    public void openUnlimitedContents() {
        logger.info("Opening Unlimited Contents service...");
        clickPartnerService(unlimitedContentsLocator, "Unlimited Contents");

        try {
            Thread.sleep(3000);
            WebDriverWait wait = new WebDriverWait(driver, 30);
            MobileElement buyPlanElement = (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(planLocator));
            if (buyPlanElement.isDisplayed()) {
                logger.info("Buy Plan found for Unlimited Contents, clicking it.");
                buyPlanElement.click();
                Thread.sleep(5000);
                MobileElement abElement = (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(homePageBackButtonLocator));
                abElement.click();
            }
        } catch (Exception e) {
            logger.error("Buy Plan not found or clickable for Unlimited Contents.", e);
            takeScreenshot("UnlimitedContents_BuyPlan_Error");
        }
    }

    public void openMeroSchool() {
        logger.info("Opening Mero School service...");
        clickPartnerService(meroSchoolLocator, "Mero School");

        try {
            Thread.sleep(3000);
            WebDriverWait wait = new WebDriverWait(driver, 30);
            MobileElement buyPlanElement = (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(courseLocator));
            if (buyPlanElement.isDisplayed()) {
                logger.info("Buy Plan found for Mero School, clicking it.");
                buyPlanElement.click();
                Thread.sleep(10000);
                MobileElement abElement = (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(homePageBackButtonLocator));
                abElement.click();
            }
        } catch (Exception e) {
            logger.error("Buy Plan not found or clickable for Mero School.", e);
            takeScreenshot("MeroSchool_BuyPlan_Error");
        }
    }

    private void clickPartnerService(By locator, String serviceName) {
        int maxSwipes = 6;
        boolean found = false;

        for (int i = 0; i < maxSwipes; i++) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, 60);
                MobileElement element = (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                if (element.isDisplayed()) {
                    element.click();
                    logger.info("Clicked on: {}", serviceName);
                    found = true;
                    break;
                }
            } catch (Exception e) {
                logger.warn("Swiping to find: {} (attempt {})", serviceName, (i + 1));
                swipeUp();
            }
        }

        if (!found) {
            logger.error("{} not found after {} swipes.", serviceName, maxSwipes);
            takeScreenshot(serviceName.replaceAll("\\s+", "") + "_NotFound");
            Assert.fail(serviceName + " not found after swiping.");
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
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(200)))
                .moveTo(PointOption.point(startX, endY))
                .release()
                .perform();
    }

    public void takeScreenshot(String screenshotName) {
        try {
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

            Files.copy(srcFile.toPath(), Paths.get(destinationPath));
            logger.info("Screenshot saved at: {}", destinationPath);
        } catch (IOException e) {
            logger.error("Failed to save screenshot: {}", e.getMessage(), e);
        }
    }
}


