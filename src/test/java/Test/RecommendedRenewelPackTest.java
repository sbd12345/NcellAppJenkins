package Test;

import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Base.BaseTest;
import Pages.RecommendedRenewelPackPage;

import org.openqa.selenium.By;

public class RecommendedRenewelPackTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(RecommendedRenewelPackTest.class);

    @Test
    public void recommendedRenewalPackTest() {
        logger.info("Starting test: recommendedRenewalPackTest");
        try {
            RecommendedRenewelPackPage packsPage = new RecommendedRenewelPackPage(driver);

            String buyPlanText = "Rs 200 |Unlimited GB";
            String renewalTabText = "7 Day Renewal";
            String cancelButtonText = "Cancel";
            String detailsText = "Details";


            By backLocator = By.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup");

            packsPage.clickBuyPlanByText(buyPlanText);
            packsPage.selectRenewalTab(renewalTabText);
            Thread.sleep(10000);
            packsPage.clickCancelButton(cancelButtonText);
            packsPage.clickDetailsAndBack(detailsText, backLocator);

            logger.info("Recommended Renewal Pack test completed successfully");

        } catch (Exception e) {
            logger.error("Recommended Renewal Pack test failed: {}", e.getMessage(), e);
        }
    }
}



