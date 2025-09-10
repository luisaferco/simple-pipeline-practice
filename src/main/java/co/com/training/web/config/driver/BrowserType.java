package co.com.training.web.config.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public enum BrowserType {

    CHROME {
        @Override
        public WebDriver createDriver(Capabilities capabilities) {
            ChromeOptions chromeOptions = new ChromeOptions();
            boolean isRemote = Boolean.parseBoolean(capabilities.getCapability("remote").toString());
            chromeOptions.setHeadless(isRemote);
            chromeOptions.addArguments("--remote-allow-origins=*");
            chromeOptions.merge(capabilities);
            WebDriverManager.chromedriver().setup();
            return new ChromeDriver(chromeOptions);

        }
    },
    EDGE {
        @Override
        public WebDriver createDriver(Capabilities capabilities) {
            EdgeOptions options = new EdgeOptions();
            boolean isRemote = Boolean.parseBoolean(capabilities.getCapability("remote").toString());
            options.setHeadless(isRemote);
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.merge(capabilities);
            WebDriverManager.edgedriver().setup();
            return new EdgeDriver(options);
        }
    },
    FIREFOX {
        @Override
        public WebDriver createDriver(Capabilities capabilities) {
            FirefoxOptions options = new FirefoxOptions();
            boolean isRemote = Boolean.parseBoolean(capabilities.getCapability("remote").toString());
            options.setHeadless(isRemote);
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.merge(capabilities);
            WebDriverManager.firefoxdriver().setup();
            return new FirefoxDriver(options);
        }
    },
    REMOTE {
        @Override
        public WebDriver createDriver(Capabilities capabilities) {

            return new RemoteWebDriver(capabilities);
        }

    };



    public abstract WebDriver createDriver(Capabilities capabilities);
}
