package Test;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Listeners;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Base.BaseTest;
import Pages.NcellNSLPage;
import Listeners.CustomTestListener;

@Listeners(CustomTestListener.class)
public class NcellNSLTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(NcellNSLTest.class);
    private NcellNSLPage nslPage;

    @BeforeClass
    public void setUpPage() {
        nslPage = new NcellNSLPage(driver);
    }

    @Test(priority = 1)
    public void Spinandwin() {
        try {
            logger.info("Starting test: Spinandwin");
            nslPage.Spinandwin();
            logger.info("Spinandwin completed successfully");
        } catch (Exception e) {
            logger.error("Spinandwin test failed: {}", e.getMessage(), e);
            throw e;
        }
    }

  /*  @Test(priority = 2)
    public void NSLLiveMatch() {
        try {
            logger.info("Starting test: NSLLiveMatch");
            nslPage.NslLiveMatch();
            logger.info("NSLLiveMatch completed successfully");
        } catch (Exception e) {
            logger.error("NSLLiveMatch test failed: {}", e.getMessage(), e);
            throw e;
        }
    }      */

    @Test(priority = 3)
    public void DigitalLeague() {
        try {
            logger.info("Starting test: DigitalLeague");
            nslPage.DigitalLeague();
            logger.info("DigitalLeague completed successfully");
        } catch (Exception e) {
            logger.error("DigitalLeague test failed: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Test(priority = 4)
    public void FantasyLeague() {
        try {
            logger.info("Starting test: FantasyLeague");
            nslPage.FantasyLeague();
            logger.info("FantasyLeague completed successfully");
        } catch (Exception e) {
            logger.error("FantasyLeague test failed: {}", e.getMessage(), e);
            throw e;
        }
    }

}

