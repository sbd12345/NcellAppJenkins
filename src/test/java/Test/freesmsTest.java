package Test;

import org.testng.annotations.Test;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Listeners;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Base.BaseTest;
import Pages.FreesmsPage;
import Listeners.CustomTestListener;

@Listeners(CustomTestListener.class)
public class freesmsTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(freesmsTest.class);

    @Test
    public void test() {
        try {
            logger.info("Starting test: freesmsTest");
            FreesmsPage Page = new FreesmsPage(driver);
            Page.freesms();
            Page.sms();
            logger.info("freesmsTest completed successfully");
        } catch (Exception e) {
            logger.error("freesmsTest failed: {}", e.getMessage(), e);
            throw e;
        }
    }

}

