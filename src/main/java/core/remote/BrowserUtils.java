package core.remote;

import core.config.ConfigProperty;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import static core.config.Config.getValue;
import static java.lang.Boolean.TRUE;

public class BrowserUtils {

    private static final Logger logger = Logger.getLogger("BrowserUtilsLogger");
    public static String videoURL = "None";

    public static RemoteWebDriver configureBrowser() {
        DesiredCapabilities browser = configureIE();

        setDefaultCapabilities(browser);
        try {
            String serverUrl = getValue(ConfigProperty.WEBDRIVER_URL);
            RemoteWebDriver driver = null;

            int maxRetries = Integer.parseInt(getValue(ConfigProperty.MAX_RETRIES));
            for (int i=0; i<maxRetries; i++){
                try {
                    driver = new RemoteWebDriver(new URL(serverUrl + "/wd/hub"), browser);
                    i = maxRetries;
                }
                catch (UnreachableBrowserException | SessionNotCreatedException e){
                    sleep(Integer.parseInt(getValue(ConfigProperty.RETRY_TIMEOUT)));
                    logger.log(Level.INFO, String.format("Connection retry " + (i+1) + " on '%s'", e.getClass().getSimpleName()));
                }
            }
            if (driver == null) {
                throw new SeleniumException("Unable to connect to Server after " + maxRetries + " retires");
            }

            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
            if (TRUE.equals(Boolean.valueOf(getValue(ConfigProperty.SELENIUM_GRID)))) {
                videoURL = serverUrl + "/grid/resources/remote?session=" + driver.getSessionId().toString();
            }

            return driver;
        } catch (MalformedURLException e) {
            throw new SeleniumException("Please check the default Browser URL to be opened");
        }
    }

    private static void setDefaultCapabilities(DesiredCapabilities browser) {
        if (Boolean.valueOf(getValue(ConfigProperty.SELENIUM_GRID))) {
            browser.setCapability("project", getValue(ConfigProperty.SELENIUM_GRID_PROJECT));

            String username = getValue(ConfigProperty.TEST_USER_NAME);
            String password = getValue(ConfigProperty.TEST_USER_PWD);
            if (username != null && password != null) {
                browser.setCapability("user", username);
                browser.setCapability("password", password);
            }
            browser.setCapability("video", Boolean.valueOf(getValue(ConfigProperty.SELENIUM_GRID_VIDEO)));
            browser.setCapability("screen-resolution", getValue(ConfigProperty.BROWSER_RESOLUTION));
            browser.setCapability("takesScreenshot", false);
        }
    }

    private static DesiredCapabilities configureIE() {
        DesiredCapabilities browser = DesiredCapabilities.internetExplorer();
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.ALL);
        browser.setCapability("ie.enableFullPageScreenshot", false);
        browser.setCapability("ie.ensureCleanSession", true);
        browser.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        browser.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        return browser;
    }

    private static void sleep(int sleepTimeout) {
        try {
            Thread.sleep(sleepTimeout);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
