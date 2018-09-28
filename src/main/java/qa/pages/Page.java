package qa.pages;

import core.utils.PageUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.time.Duration;

public abstract class Page {

    protected RemoteWebDriver driver;

    public Page(RemoteWebDriver driver) {
        this.driver = driver;
    }

    protected Pages getPage() {
        return null;
    }

    protected RemoteWebDriver getDriver() {
        return driver;
    }

    public abstract void waitToBeLoaded();

    public void open() {
        if (getPage() != null) {
            PageUtils.openPage(getPage().getPostfix());
        }
        waitToBeLoaded();
    }

    public void smartClick(WebElement element) {
        Actions actions = new Actions(driver);
        PageUtils.waitUntilVisible(element);
        actions.moveToElement(element).click().perform();
    }

    public void pause(Long seconds) {
        Actions actions = new Actions(driver);
        actions.pause(Duration.ofSeconds(seconds)).build().perform();
    }

}