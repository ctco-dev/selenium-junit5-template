package com;

import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import qa.pages.iOSTemplatePage;

import java.io.File;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class iOSTest {

    private WebDriver driver;
    private iOSTemplatePage iOSApp;

    @BeforeEach
    public void setUp() throws Exception {
        File appDir = new File(System.getProperty("user.dir"),
                "../sample/ios-test-app/build/Release-iphonesimulator");
        File app = new File(appDir, "TestApp-iphonesimulator.app");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "");
        capabilities.setCapability(MobileCapabilityType.APPIUM_VERSION, "1.7.1");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "12.0");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone SE");
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());

        iOSApp = new iOSTemplatePage();
        driver = new IOSDriver<MobileElement>(new URL("http://localhost:4723/wd/hub"), capabilities);

        System.out.println(driver.getPageSource());
        PageFactory.initElements(new AppiumFieldDecorator(driver), iOSApp);
    }

    @AfterEach
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    public void findByElementsTest() {
        assertEquals(0, iOSApp.mobileButtons.size());
    }




}
