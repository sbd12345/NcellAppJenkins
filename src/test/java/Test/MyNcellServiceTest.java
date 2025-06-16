package Test;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Listeners;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Base.BaseTest;
import Pages.MyNcellServicePage;

import Listeners.CustomTestListener;

@Listeners(CustomTestListener.class)
public class MyNcellServiceTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(MyNcellServiceTest.class);
    private MyNcellServicePage servicePage;

    @BeforeClass
    public void setUpPage() {
        servicePage = new MyNcellServicePage(driver);
    }

    @Test(priority = 1)
    public void testPinPukQuery() {
        try {
            logger.info("Starting test: testPinPukQuery");
            servicePage.PinPukQuery();
            logger.info("testPinPukQuery completed successfully");
        } catch (Exception e) {
            logger.error("testPinPukQuery failed: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Test(priority = 3)
    public void testEmergencyServices() {
        try {
            logger.info("Starting test: testEmergencyServices");
            servicePage.EmergencyServices();
            logger.info("testEmergencyServices completed successfully");
        } catch (Exception e) {
            logger.error("testEmergencyServices failed: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Test(priority = 4)
    public void testTollFreeNumbers() {
        try {
            logger.info("Starting test: testTollFreeNumbers");
            servicePage.TollFreeNumbers();
            logger.info("testTollFreeNumbers completed successfully");
        } catch (Exception e) {
            logger.error("testTollFreeNumbers failed: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Test(priority = 2)
    public void testEsim() {
        try {
            logger.info("Starting test: testEsim");
            servicePage.Esim();
            logger.info("testEsim completed successfully");
        } catch (Exception e) {
            logger.error("testEsim failed: {}", e.getMessage(), e);
            throw e;
        }
    }
}
