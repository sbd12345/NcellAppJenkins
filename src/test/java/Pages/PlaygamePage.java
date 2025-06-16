package Pages;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class PlaygamePage {

    private AndroidDriver<MobileElement> driver;

    public PlaygamePage(AndroidDriver<MobileElement> driver) {
        this.driver = driver;
    }

    private final By policeGameTile = By.xpath("//*[contains(@content-desc,'Police')]");
    private final By changaChaitTile = By.xpath("//*[contains(@content-desc,'Changa Chait')]");
    private final By timberGuyTile = By.xpath("//*[contains(@content-desc,'Timber Guy')]");
    private final By stickyGooTile = By.xpath("//*[contains(@content-desc,'Sticky Goo')]");
    private final By ludoWithFriendsTile = By.xpath("//*[contains(@content-desc,'Ludo with Friends')]");
    private final By carromHeroTile = By.xpath("//*[contains(@content-desc,'Carrom Hero')]");
    private final By candyFiestaTile = By.xpath("//*[contains(@content-desc,'Candy Fiesta')]");
    private final By homePageBackButtonLocator = By.xpath("//android.view.ViewGroup[2]/android.view.ViewGroup");

    public void openPoliceGame() {
        openGame(policeGameTile, "Police", true);
    }

    public void openChangaChaitGame() {
        openGame(changaChaitTile, "Changa Chait", true);
    }

    public void openTimberGuyGame() {
        System.out.println("Opening Timber Guy game...");
        clickGame(timberGuyTile, "Timber Guy");
        try {
            WebDriverWait wait = new WebDriverWait(driver, 40);
            MobileElement gameElement = (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//*[contains(@content-desc,'Timber Guy')]")));
            if (gameElement.isDisplayed()) {
                gameElement.click();
                System.out.println("Clicked Timber Guy in-game element.");
                sleep(50000);
            }
        } catch (Exception e) {
            System.out.println("Error in Timber Guy Game: " + e.getMessage());
            Assert.fail("Timber Guy game failed: " + e.getMessage());
        } finally {
            clickElement(homePageBackButtonLocator, "Back from Timber Guy");
        }
    }

    public void openStickyGooGame() {
        openGame(stickyGooTile, "Sticky Goo", true);
    }

    public void openLudoWithFriendsGame() {
        openGame(ludoWithFriendsTile, "Ludo with Friends", true);
    }

    public void openCarromHeroGame() {
        openGame(carromHeroTile, "Carrom Hero", true);
    }

    public void openCandyFiestaGame() {
        openGame(candyFiestaTile, "Candy Fiesta", true);
    }

    private void openGame(By locator, String gameName, boolean shouldFail) {
        System.out.println("Opening " + gameName + " game...");
        clickGame(locator, gameName);
        try {
            sleep(20000);
            if (shouldFail) {
                Assert.fail(gameName + " game failed to load correctly.");
            }
        } catch (Exception e) {
            System.out.println("Error in " + gameName + ": " + e.getMessage());
        } finally {
            clickElement(homePageBackButtonLocator, "Back from " + gameName);
        }
    }

    private void clickGame(By locator, String gameName) {
        int maxSwipes = 7;
        boolean found = false;
        System.out.println("Attempting to find and click game: " + gameName);

        for (int i = 0; i < maxSwipes; i++) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, 10);
                MobileElement element = (MobileElement) wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                if (element.isDisplayed()) {
                    element.click();
                    System.out.println("Clicked on game: " + gameName);
                    found = true;
                    break;
                }
            } catch (Exception e) {
                swipeUp();
                System.out.println("Swiping to find: " + gameName + " (attempt " + (i + 1) + ")");
            }
        }

        if (!found) {
            String errorMsg = gameName + " not found after swiping.";
            Assert.fail(errorMsg);
        }
    }

    private void clickElement(By locator, String elementName) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 15);
            MobileElement element = (MobileElement) wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.click();
            System.out.println("Clicked on: " + elementName);
        } catch (Exception e) {
            System.out.println("Failed to click on " + elementName + ": " + e.getMessage());
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
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(100)))
                .moveTo(PointOption.point(startX, endY))
                .release()
                .perform();
        System.out.println("Performed swipe up");
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            System.out.println("Sleep interrupted: " + e.getMessage());
        }
    }
}

