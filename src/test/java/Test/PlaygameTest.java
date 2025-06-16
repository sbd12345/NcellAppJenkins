package Test;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.annotations.AfterSuite;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Base.BaseTest;
import Pages.PlaygamePage;
import Listeners.CustomTestListener;

@Listeners(CustomTestListener.class)
public class PlaygameTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(PlaygameTest.class);
    private PlaygamePage playgamePage;

    @BeforeClass
    public void setUpPage() {
        playgamePage = new PlaygamePage(driver);
    }

    @Test(priority = 7)
    public void testOpenPoliceGame() {
        try {
            logger.info("Starting test: testOpenPoliceGame");
            playgamePage.openPoliceGame();
            logger.info("testOpenPoliceGame completed successfully");
        } catch (Exception e) {
            logger.error("testOpenPoliceGame failed: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Test(priority = 2)
    public void testOpenChangaChaitGame() {
        try {
            logger.info("Starting test: testOpenChangaChaitGame");
            playgamePage.openChangaChaitGame();
            logger.info("testOpenChangaChaitGame completed successfully");
        } catch (Exception e) {
            logger.error("testOpenChangaChaitGame failed: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Test(priority = 3)
    public void testOpenCandyFiestaGame() {
        try {
            logger.info("Starting test: testOpenCandyFiestaGame");
            playgamePage.openCandyFiestaGame();
            logger.info("testOpenCandyFiestaGame completed successfully");
        } catch (Exception e) {
            logger.error("testOpenCandyFiestaGame failed: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Test(priority = 4)
    public void testOpenStickyGooGame() {
        try {
            logger.info("Starting test: testOpenStickyGooGame");
            playgamePage.openStickyGooGame();
            logger.info("testOpenStickyGooGame completed successfully");
        } catch (Exception e) {
            logger.error("testOpenStickyGooGame failed: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Test(priority = 5)
    public void testOpenLudoWithFriendsGame() {
        try {
            logger.info("Starting test: testOpenLudoWithFriendsGame");
            playgamePage.openLudoWithFriendsGame();
            logger.info("testOpenLudoWithFriendsGame completed successfully");
        } catch (Exception e) {
            logger.error("testOpenLudoWithFriendsGame failed: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Test(priority = 6)
    public void testOpenCarromHeroGame() {
        try {
            logger.info("Starting test: testOpenCarromHeroGame");
            playgamePage.openCarromHeroGame();
            logger.info("testOpenCarromHeroGame completed successfully");
        } catch (Exception e) {
            logger.error("testOpenCarromHeroGame failed: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Test(priority = 1)
    public void testOpenTimberGuyGame() {
        try {
            logger.info("Starting test: testOpenTimberGuyGame");
            playgamePage.openTimberGuyGame();
            logger.info("testOpenTimberGuyGame completed successfully");
        } catch (Exception e) {
            logger.error("testOpenTimberGuyGame failed: {}", e.getMessage(), e);
            throw e;
        }
    }
}


