package Test;

import org.testng.annotations.Test;
import org.testng.annotations.AfterSuite;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Base.BaseTest;
import Pages.HistoryandFavouritePage;

public class HistoryandFavouriteTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(HistoryandFavouriteTest.class);

    @Test
    public void HistoryandFavouriteTest() {
        try {
            logger.info("Starting test: HistoryandFavouriteTest");
            HistoryandFavouritePage hf = new HistoryandFavouritePage(driver); 
          //  hf.closeBanner();
            hf.History();
            hf.Favourite();  
            logger.info("HistoryandFavouriteTest completed successfully");
        } catch (Exception e) {
            logger.error("HistoryandFavouriteTest failed: {}", e.getMessage(), e);
            throw e;
        }
    }

}



   
