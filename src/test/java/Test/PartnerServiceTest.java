package Test;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.Listeners;
import org.testng.annotations.AfterSuite;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Base.BaseTest;
import Pages.PartneredServicePages;
import Listeners.CustomTestListener;

@Listeners(CustomTestListener.class)
public class PartnerServiceTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(PartnerServiceTest.class);
    private PartneredServicePages partneredServicePages;

    @BeforeClass
    public void setUpPage() {
        partneredServicePages = new PartneredServicePages(driver);
    }

    @Test(priority = 3)
    public void openHungamaTest() {
        try {
            logger.info("Starting test: openHungamaTest");
            partneredServicePages.openHungama();
            logger.info("openHungamaTest completed successfully");
        } catch (Exception e) {
            logger.error("openHungamaTest failed: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Test(priority = 1)
    public void openUnlimitedContentsTest() {
        try {
            logger.info("Starting test: openUnlimitedContentsTest");
            partneredServicePages.openUnlimitedContents();
            logger.info("openUnlimitedContentsTest completed successfully");
        } catch (Exception e) {
            logger.error("openUnlimitedContentsTest failed: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Test(priority = 2)
    public void openMeroSchoolTest() {
        try {
            logger.info("Starting test: openMeroSchoolTest");
            partneredServicePages.openMeroSchool();
            logger.info("openMeroSchoolTest completed successfully");
        } catch (Exception e) {
            logger.error("openMeroSchoolTest failed: {}", e.getMessage(), e);
            throw e;
        }
    }

 
}
