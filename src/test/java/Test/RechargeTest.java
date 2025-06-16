package Test;

import org.testng.annotations.Test;
import org.testng.annotations.AfterSuite;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import Base.BaseTest;

import Pages.RechargePage;

public class RechargeTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(RechargeTest.class);

    @Test
    public void rechargeTest() {
        logger.info("Starting test: rechargeTest");
        try {
         
            RechargePage recharge = new RechargePage(driver);

            recharge.rechargeClick();
            recharge.payOnline();
            recharge.rechargeCard();
   

            logger.info("Recharge test completed successfully");
        } catch (Exception e) {
            logger.error("Recharge test failed: {}", e.getMessage(), e);
            throw e;
        }
    }

   
}


