package Pages;

import org.openqa.selenium.By;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.Logger;
import utility.ConfigReader;
import org.apache.logging.log4j.LogManager;

public class SearchBoxPage {
    private AndroidDriver<MobileElement> driver;
    private static final Logger logger = LogManager.getLogger(SearchBoxPage.class);

    public SearchBoxPage(AndroidDriver<MobileElement> driver) {
        this.driver = driver;
        logger.info("Initialized SearchBoxPage.");
    }
    
    private final By homePageBackButtonLocator = MobileBy.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup");

    private By getCrossIconLocator() {
        return By.xpath("//android.widget.TextView[contains(@text,'')]");
    }

    private By searchBoxLocator() {
        return By.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]" +
                "/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup" +
                "/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout" +
                "/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup" +
                "/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]" +
                "/android.view.ViewGroup/android.view.ViewGroup[3]/android.widget.ImageView");
    }

    private By getSearchInputLocator() {
        return By.xpath("//android.widget.EditText[contains(@text,'Search')]");
    }

    private By getTextViewByText(String visibleText) {
        return By.xpath("//android.widget.TextView[@text='" + visibleText + "']");
    }

    private MobileElement waitForElement(By locator, int timeoutInSeconds) {
        logger.debug("Waiting for element: " + locator.toString() + " with timeout: " + timeoutInSeconds + " seconds");
        return (MobileElement) new org.openqa.selenium.support.ui.WebDriverWait(driver, timeoutInSeconds)
                .until(org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated(locator));
    }

    /*
    public void closeBanner() {
        logger.info("Attempting to close banner.");
        MobileElement crossIcon = waitForElement(getCrossIconLocator(), 55);
        crossIcon.click();
        logger.info("Banner closed successfully.");
    }
    */

    public void searchForHistory() throws InterruptedException {
        logger.info("Starting search for history.");

        MobileElement searchBox = waitForElement(searchBoxLocator(), 50);
        searchBox.click();
        logger.debug("Search box clicked.");

        MobileElement searchInput = waitForElement(getSearchInputLocator(), 55);
        String searchText = ConfigReader.getProperty("name");
        logger.debug("Entering text into search input: " + searchText);
        searchInput.sendKeys(searchText);

        MobileElement historyButton = waitForElement(getTextViewByText("History"), 15);
        historyButton.click();
        
        Thread.sleep(55000);
        MobileElement back = waitForElement(homePageBackButtonLocator, 30);
        back.click();

        logger.info("Clicked on history button.");
    }
}
