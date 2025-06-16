package Test;

import org.testng.annotations.Test;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Listeners;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Base.BaseTest;
import Pages.UtilitiesPage;
import Listeners.CustomTestListener;

@Listeners(CustomTestListener.class)
public class UtilityTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(UtilityTest.class);
    private UtilitiesPage utilitiesPage;

    @Test(priority = 1)
    public void openNepaliNewsTest() {
        logger.info("Starting test: openNepaliNewsTest");
        try {
            utilitiesPage = new UtilitiesPage(driver);
            utilitiesPage.openNepaliNews();
            logger.info("openNepaliNewsTest completed successfully");
        } catch (Exception e) {
            logger.error("openNepaliNewsTest failed: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Test(priority = 2)
    public void openHoroscopeTest() {
        logger.info("Starting test: openHoroscopeTest");
        try {
            utilitiesPage = new UtilitiesPage(driver);
            utilitiesPage.openHoroscope();
            logger.info("openHoroscopeTest completed successfully");
        } catch (Exception e) {
            logger.error("openHoroscopeTest failed: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Test(priority = 3)
    public void openGoldSilverPriceTest() {
        logger.info("Starting test: openGoldSilverPriceTest");
        try {
            utilitiesPage = new UtilitiesPage(driver);
            utilitiesPage.openGoldSilverPrice();
            logger.info("openGoldSilverPriceTest completed successfully");
        } catch (Exception e) {
            logger.error("openGoldSilverPriceTest failed: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Test(priority = 4)
    public void openShareMarketLiveTest() {
        logger.info("Starting test: openShareMarketLiveTest");
        try {
            utilitiesPage = new UtilitiesPage(driver);
            utilitiesPage.openShareMarketLive();
            logger.info("openShareMarketLiveTest completed successfully");
        } catch (Exception e) {
            logger.error("openShareMarketLiveTest failed: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Test(priority = 7)
    public void openEplLiveScoreTest() {
        logger.info("Starting test: openEplLiveScoreTest");
        try {
            utilitiesPage = new UtilitiesPage(driver);
            utilitiesPage.openEplLiveScore();
            logger.info("openEplLiveScoreTest completed successfully");
        } catch (Exception e) {
            logger.error("openEplLiveScoreTest failed: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Test(priority = 5)
    public void openWeatherTest() {
        logger.info("Starting test: openWeatherTest");
        try {
            utilitiesPage = new UtilitiesPage(driver);
            utilitiesPage.openWeather();
            logger.info("openWeatherTest completed successfully");
        } catch (Exception e) {
            logger.error("openWeatherTest failed: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Test(priority = 6)
    public void openExchangeRateTest() {
        logger.info("Starting test: openExchangeRateTest");
        try {
            utilitiesPage = new UtilitiesPage(driver);
            utilitiesPage.openExchangeRate();
            logger.info("openExchangeRateTest completed successfully");
        } catch (Exception e) {
            logger.error("openExchangeRateTest failed: {}", e.getMessage(), e);
            throw e;
        }
    }

}
