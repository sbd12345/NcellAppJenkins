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

public class NcellPurpleLeaguePage {

    private static final Logger logger = LogManager.getLogger(NcellPurpleLeaguePage.class);

    private AndroidDriver<MobileElement> driver;
    private WebDriverWait wait;

    public NcellPurpleLeaguePage(AndroidDriver<MobileElement> driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 60);
    }

    private By byAccessibilityId(String id) {
        return MobileBy.xpath("//*[@content-desc='" + id + "']");
    }

    private By byText(String text) {
        return MobileBy.xpath("//*[@text='" + text + "']");
    }

    private final By ncellPurpleLeagueLocator = byAccessibilityId("Ncell Purple League");
    private final By scoreboardLocator = byText("SCOREBOARD");
    private final By top10Locator = byAccessibilityId("Top 10");
    private final By rulesAndPrizeLocator = byAccessibilityId("Rules & Prize");

    public void NcellPurpleLeague() {
        int maxSwipes = 3;
        boolean found = false;

        for (int i = 0; i < maxSwipes; i++) {
            try {
                logger.info("Attempting to find and click 'Ncell Purple League', swipe attempt: {}", i + 1);
                MobileElement element = (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(ncellPurpleLeagueLocator));
                if (element.isDisplayed()) {
                    element.click();
                    logger.info("'Ncell Purple League' clicked successfully.");
                    Thread.sleep(10000);

                    interactWithElement(scoreboardLocator, "SCOREBOARD", 5000);
                    interactWithElement(top10Locator, "Top 10", 5000);
                    interactWithElement(rulesAndPrizeLocator, "Rules & Prize", 5000);

                    Thread.sleep(1500);
                    found = true;
                    break;
                }
            } catch (Exception e) {
                logger.warn("Could not find 'Ncell Purple League' on swipe attempt {}. Swiping up...", i + 1);
                takeScreenshot("NcellPurpleLeague_NotFound_Swipe" + (i + 1));
                swipeUp();
            }
        }

        if (!found) {
            logger.error("'Ncell Purple League' not found after {} swipe attempts.", maxSwipes);
            Assert.fail("'Ncell Purple League' not found after " + maxSwipes + " swipe attempts.");
        }
    }

    private void interactWithElement(By locator, String name, int waitMillis) {
        try {
            MobileElement element = (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            if (element.isDisplayed()) {
                logger.info("Clicking on '{}'", name);
                element.click();
                Thread.sleep(waitMillis);
            }
        } catch (Exception e) {
            logger.error("Error interacting with '{}': {}", name, e.getMessage());
            takeScreenshot("Error_" + name.replace(" ", "_"));
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
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(400)))
                .moveTo(PointOption.point(startX, endY))
                .release()
                .perform();
    }

    public void takeScreenshot(String screenshotName) {
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
