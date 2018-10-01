package qa.pages;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.iOSFindBy;

import java.util.List;

public class iOSTemplatePage {

    @iOSFindBy(uiAutomator = ".elements()[0]")
    public List<MobileElement> mobileButtons;

}
