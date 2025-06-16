package Pages;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MyPackPage {

    private static final Logger logger = LogManager.getLogger(MyPackPage.class);

    private AndroidDriver<MobileElement> driver;

    private final String xpathCrossIcon = "//android.widget.TextView[@text='']";
    private final String xpathMyPack = "//android.widget.TextView[@text=\"MY PACKS \"]";
    private final String xpathData = "//android.widget.TextView[@text=\"Data\"]";
    private final String xpathVoice = "//android.widget.TextView[@text=\"Voice\"]";

    private By crossIconLocator;
    private By myPackLocator;
    private By dataLocator;
    private By voiceLocator;

    public MyPackPage(AndroidDriver<MobileElement> driver) {
        this.driver = driver;

        
        crossIconLocator = By.xpath(xpathCrossIcon);
        myPackLocator = By.xpath(xpathMyPack);
        dataLocator = By.xpath(xpathData);
        voiceLocator = By.xpath(xpathVoice);
    }

    private MobileElement waitForElement(By locator, int timeoutInSeconds) {
        return (MobileElement) new WebDriverWait(driver, timeoutInSeconds)
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public void closeBanner() {
        try {
            logger.info("Attempting to close banner.");
            MobileElement crossIcon = waitForElement(crossIconLocator, 55);
            crossIcon.click();
            logger.info("Banner closed successfully.");
        } catch (Exception e) {
            logger.error("Failed to close banner: {}", e.getMessage());
            takeScreenshot("closeBanner_Failure");
            throw e;
        }
    }

    public void openMyPack() {
        try {
            logger.info("Opening 'My Packs' page.");
            MobileElement pack = waitForElement(myPackLocator, 15);
            pack.click();
            logger.info("'My Packs' page opened.");
        } catch (Exception e) {
            logger.error("Failed to open 'My Packs' page: {}", e.getMessage());
            takeScreenshot("openMyPack_Failure");
            throw e;
        }
    }

    public void openDataSection() {
        try {
            logger.info("Opening Data section.");
            MobileElement data = waitForElement(dataLocator, 5);
            data.click();
            logger.info("Data section opened.");
            driver.navigate().back();
            waitForElement(myPackLocator, 30);
            logger.info("Navigated back to 'My Packs' page.");
        } catch (Exception e) {
            logger.error("Failed to open Data section: {}", e.getMessage());
            takeScreenshot("openDataSection_Failure");
            throw e;
        }
    }

    public void openVoiceAndSMSSection() {
        try {
            logger.info("Opening Voice and SMS section.");
            MobileElement voice = waitForElement(voiceLocator, 20);
            voice.click();
            logger.info("Voice and SMS section opened.");
            driver.navigate().back();
            waitForElement(myPackLocator, 20);
            logger.info("Navigated back to 'My Packs' page.");
        } catch (Exception e) {
            logger.error("Failed to open Voice and SMS section: {}", e.getMessage());
            takeScreenshot("openVoiceAndSMSSection_Failure");
            throw e;
        }
    }

    private void takeScreenshot(String screenshotName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File srcFile = ts.getScreenshotAs(OutputType.FILE);

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String destinationPath = "screenshots/" + screenshotName + "_" + timestamp + ".png";

            File screenshotDirectory = new File("screenshots");
            if (!screenshotDirectory.exists()) {
                boolean created = screenshotDirectory.mkdirs();
                if (created) {
                    logger.info("Screenshots directory created.");
                } else {
                    logger.warn("Failed to create screenshots directory.");
                }
            }

            Files.copy(srcFile.toPath(), Paths.get(destinationPath));
            logger.info("Screenshot saved at: {}", destinationPath);
        } catch (IOException e) {
            logger.error("Failed to save screenshot: {}", e.getMessage());
        }
    }
}
