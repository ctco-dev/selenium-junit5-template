package core.utils;

import core.config.ConfigProperty;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static core.GlobalWorld.getDriver;
import static core.config.Config.getValue;


public class PageUtils {

    public static final int ACTION_TIMEOUT = Integer.valueOf(getValue(ConfigProperty.GLOBAL_TIMEOUT));

    public static void openPage(String url) {
        getDriver().manage().timeouts().pageLoadTimeout(ACTION_TIMEOUT, TimeUnit.SECONDS);
        getDriver().manage().timeouts().setScriptTimeout(ACTION_TIMEOUT, TimeUnit.SECONDS);
        getDriver().manage().timeouts().implicitlyWait(ACTION_TIMEOUT, TimeUnit.SECONDS);

        String homepage = getValue(ConfigProperty.HOME_PAGE);
        if (url != null) {
            if (homepage.endsWith("/")) {
                homepage = homepage + url;
            } else {
                homepage = homepage + "/" + url;
            }
        }

        getDriver().switchTo().window(getDriver().getWindowHandle());
        waitForPageToBeFullyLoaded(getDriver());
        getDriver().get(homepage);
    }

    private static void waitForPageToBeFullyLoaded(WebDriver driver) {
        new WebDriverWait(driver, ACTION_TIMEOUT).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        });
    }

    public static void waitUntilVisible(By locator) {
        new WebDriverWait(getDriver(), ACTION_TIMEOUT).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static void waitUntilVisible(By locator, long timeout) {
        new WebDriverWait(getDriver(), timeout).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement getElementWhenVisible(By locator) {
        try {
            return new WebDriverWait(getDriver(), ACTION_TIMEOUT).until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            return null;
        }
    }

    public static List<WebElement> getAllElementsWhenVisible(By locator) {
        try {
            return new WebDriverWait(getDriver(), ACTION_TIMEOUT).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
        } catch (TimeoutException e) {
            return new ArrayList<>();
        }
    }

    public static void waitUntilVisible(WebElement element) {
        new WebDriverWait(getDriver(), ACTION_TIMEOUT).until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitUntilVisible(WebElement element, long timeout) {
        new WebDriverWait(getDriver(), timeout).until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement getElementWhenVisible(WebElement element) {
        return new WebDriverWait(getDriver(), ACTION_TIMEOUT).until(ExpectedConditions.visibilityOf(element));
    }

    public static void textToBePresentInElement(WebElement element, String text) {
        new WebDriverWait(getDriver(), ACTION_TIMEOUT).until(ExpectedConditions.textToBePresentInElement(element, text));
    }

    public static WebElement checkForClickable(By locator) {
        try {
            return new WebDriverWait(getDriver(), ACTION_TIMEOUT).until(ExpectedConditions.elementToBeClickable(locator));
        } catch (TimeoutException e) {
            return null;
        }
    }

    public static void waitForInvisibility(By locator, long timeout) {
        new WebDriverWait(getDriver(), timeout).until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static WebElement waitUntilClickable(WebElement webElement) {
        return new WebDriverWait(getDriver(), ACTION_TIMEOUT).until(ExpectedConditions.elementToBeClickable(webElement));
    }
}
