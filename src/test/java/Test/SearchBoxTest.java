package Test;

import org.testng.annotations.Test;
import org.testng.annotations.AfterSuite;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import Base.BaseTest;
import Pages.SearchBoxPage;


public class SearchBoxTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(SearchBoxTest.class);

    @Test
    public void searchBoxTest() throws Exception {
        logger.info("Test 'searchBoxTest' started.");
        try {
            SearchBoxPage searchPage = new SearchBoxPage(driver);
            logger.debug("SearchBoxPage initialized.");

            // searchPage.closeBanner();
            // logger.debug("closeBanner() executed.");

            searchPage.searchForHistory();
            logger.debug("searchForHistory() executed.");

            logger.info("Test 'searchBoxTest' completed successfully.");
        } catch (Exception e) {
            logger.error("Test 'searchBoxTest' failed due to exception: ", e);
            throw e;
        }
    }
}
