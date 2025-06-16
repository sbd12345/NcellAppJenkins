package Test;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.annotations.AfterSuite;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import Base.BaseTest;
import Pages.BuyDataPackPage;
import Listeners.CustomTestListener;


@Listeners(CustomTestListener.class)
public class BuyDataPackTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(BuyDataPackTest.class);
    private BuyDataPackPage buyDataPackPage;

    @BeforeClass
    public void setUpPage() {
        buyDataPackPage = new BuyDataPackPage(driver);
    }

    @Test(priority = 1)
    public void testRenewalPack() {
        try {
            logger.info("Starting test: testRenewalPack");
            buyDataPackPage.renewelpack();
            logger.info("Completed test: testRenewalPack");
        } catch (Exception e) {
            logger.error("testRenewalPack failed: {}", e.getMessage(), e);
            throw e;
        }
    }



    @Test(priority = 2)
    public void testTopSellers() {
        try {
            logger.info("Starting test: testTopSellers");
            buyDataPackPage.Topsellers();
            logger.info("Completed test: testTopSellers");
        } catch (Exception e) {
            logger.error("testTopSellers failed: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Test(priority = 3)
    public void sidhaon() {
        try {
            logger.info("Starting test: sidhaon");
            buyDataPackPage.sidhaon();
            logger.info("Completed test: sidhaon");
        } catch (Exception e) {
            logger.error("sidhaon failed: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Test(priority = 4)
    public void onethreedays() {
        try {
            logger.info("Starting test: onethreedays");
            buyDataPackPage.onethreedays();
            logger.info("Completed test: onethreedays");
        } catch (Exception e) {
            logger.error("onethreedays failed: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Test(priority = 5)
    public void testSevenDay() {
        try {
            logger.info("Starting test: testSevenDay");
            buyDataPackPage.sevenday();
            logger.info("Completed test: testSevenDay");
        } catch (Exception e) {
            logger.error("testSevenDay failed: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Test(priority = 6)
    public void testTwentyEightDays() {
        try {
            logger.info("Starting test: testTwentyEightDays");
            buyDataPackPage.twentyeightdays();
            logger.info("Completed test: testTwentyEightDays");
        } catch (Exception e) {
            logger.error("testTwentyEightDays failed: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Test(priority = 7)
    public void testEightyFourDays() {
        try {
            logger.info("Starting test: testEightyFourDays");
            buyDataPackPage.eightyfourdays();
            logger.info("Completed test: testEightyFourDays");
        } catch (Exception e) {
            logger.error("testEightyFourDays failed: {}", e.getMessage(), e);
            throw e;
        }
    }

}
