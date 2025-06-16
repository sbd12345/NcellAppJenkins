package Test;

import Base.BaseTest;
import Pages.creditmePage;
import Listeners.CustomTestListener;

import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(CustomTestListener.class)
public class creditmeTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(creditmeTest.class);

    @Test
    public void testCreditMeFlow() {
        logger.info("Starting test: creditmeTest");
        try {
            creditmePage page = new creditmePage(driver);
            page.openCreditMeSection();
            page.performCreditRequest();
            logger.info("creditmeTest completed successfully");
        } catch (Exception e) {
            logger.error("creditmeTest failed", e);
            throw e;
        }
    }

   
}

