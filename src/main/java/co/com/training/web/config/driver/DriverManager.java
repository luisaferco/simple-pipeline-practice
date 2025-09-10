package co.com.training.web.config.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class DriverManager extends ConfigCapabilities {

    private WebDriver webDriver;

    private DriverManager(String browserName) {
        super();
        DesiredCapabilities desiredCapabilities = super.loadCapabilities();
        this.webDriver = BrowserType.valueOf(browserName).createDriver(desiredCapabilities);
    }

    public static DriverManager newDriver(String browserName){
        return new DriverManager(browserName);
    }

    public WebDriver getWebDriver(String url) {
        webDriver.manage().window().maximize();
        webDriver.get(url);
        return webDriver;
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }
}
