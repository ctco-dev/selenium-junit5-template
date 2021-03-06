package qa.pages;

import core.utils.PageUtils;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class SeleniumTemplatePage extends Page {

    @FindBy(xpath = "#")
    private WebElement sampleElement;

    public SeleniumTemplatePage(RemoteWebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Override
    protected Pages getPage() {
        return Pages.TEMPLATE_PAGE;
    }

    public void waitToBeLoaded() {
        PageUtils.waitUntilVisible(By.xpath("//h2[text()='Returning Customer']"));
    }

}
