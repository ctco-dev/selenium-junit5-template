package core.Extensions.selenium;

import core.config.Config;
import core.remote.BrowserUtils;
import lombok.Getter;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SeleniumExtension implements AfterEachCallback, BeforeEachCallback, BeforeAllCallback {

    public static RemoteWebDriver driver;

    public static RemoteWebDriver getDriver() {
        return driver;
    }

    private static final Logger logger = Logger.getLogger("SeleniumLogger");

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        Config.readConfigFile();
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        driver = BrowserUtils.configureBrowser();
        logger.log(Level.INFO, "\nStarting Test: \"" + extensionContext.getDisplayName() + "\"\nRemote Viewer: " + BrowserUtils.videoURL);
        driver.manage().window().maximize();
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) {
        driver.quit();
    }


}
