package qa;

import core.utils.PageUtils;
import lombok.Getter;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class ErrorHandler {

    protected RemoteWebDriver driver;

    @FindBy(xpath = "//span[@class='Exceptions__error Exceptions__error_small']")
    private WebElement uiError;

    @FindBy(xpath = "//div[@class='toastify toastify--bottom-left']//span")
    private WebElement toastyErrorMessage;

    public ErrorHandler(RemoteWebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isMessagePresent(String message) {
        LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
        for (LogEntry entry : logEntries) {
            if (entry.getMessage().contains(message)) {
                return true;
            }
        }
        return false;
    }

    public void dropClientErrorWithMessage(String message) {
        ((JavascriptExecutor) driver).executeScript(String.format("window.onerror(\"%s\")", message));
    }

    public List<WebElement> getToastyErrorMessages() {
        PageUtils.waitUntilVisible(toastyErrorMessage);
        return driver.findElementsByClassName("hyphenate");
    }
}
