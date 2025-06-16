package Pages;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RecommendedRenewelPackPage {

    private AndroidDriver<MobileElement> driver;
    private static final Logger logger = LogManager.getLogger(RecommendedRenewelPackPage.class);

    public RecommendedRenewelPackPage(AndroidDriver<MobileElement> driver) {
        this.driver = driver;
    }

    private By getViewGroupByContentDesc(String contentDesc) {
        return By.xpath("//android.view.ViewGroup[@content-desc='" + contentDesc + "']");
    }

    private By getButtonByContentDesc(String contentDesc) {
        return By.xpath("//android.widget.Button[@content-desc='" + contentDesc + "']/android.view.ViewGroup");
    }

    private MobileElement waitForElementVisibility(By locator, int timeoutInSeconds) {
        try {
            return (MobileElement) new WebDriverWait(driver, timeoutInSeconds)
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            logger.error("Element not visible: " + locator.toString(), e);
            takeScreenshot("ElementNotVisible_" + locator.toString().replaceAll("[^a-zA-Z0-9]", "_"));
            throw e;
        }
    }

    public void clickBuyPlanByText(String planText) {
        try {
            MobileElement buyPlan = waitForElementVisibility(getViewGroupByContentDesc(planText), 70);
            buyPlan.click();
            logger.info("Clicked on Buy Plan with text: " + planText);
        } catch (Exception e) {
            logger.error("Failed to click Buy Plan with text: " + planText, e);
            takeScreenshot("clickBuyPlanError_" + planText);
            throw e;
        }
    }

    public void selectRenewalTab(String tabName) {
        try {
            waitForElementVisibility(getViewGroupByContentDesc(tabName), 40).click();
            logger.info("Selected Renewal Tab: " + tabName);
        } catch (Exception e) {
            logger.error("Failed to select Renewal Tab: " + tabName, e);
            takeScreenshot("selectRenewalTabError_" + tabName);
            throw e;
        }
    }

    public void clickCancelButton(String cancelText) {
        try {
            waitForElementVisibility(getButtonByContentDesc(cancelText), 10).click();
            logger.info("Clicked Cancel button: " + cancelText);
        } catch (Exception e) {
            logger.error("Failed to click Cancel button: " + cancelText, e);
            takeScreenshot("clickCancelError_" + cancelText);
            throw e;
        }
    }


  public void clickDetailsAndBack(String detailsText, By backLocator) {
        try {
            waitForElementVisibility(getViewGroupByContentDesc(detailsText), 30).click();
            logger.info("Clicked Details: " + detailsText);
            waitForElementVisibility(backLocator, 30).click();
            logger.info("Clicked Back button after Details");
        } catch (Exception e) {
            logger.error("Failed to click Details or Back", e);
            takeScreenshot("clickDetailsError");
            throw e;
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
            logger.info("Screenshot saved at: " + destinationPath);
        } catch (IOException e) {
            logger.error("Failed to save screenshot: " + e.getMessage(), e);
        }
    }
}
