package co.com.training.web.tests;

import co.com.training.web.config.driver.DriverManager;
import co.com.training.web.pageobject.NavigationPage;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.testng.annotations.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public abstract class BaseTest {

    private static DriverManager driverManager;
    public NavigationPage navigationPage;

    @BeforeSuite(alwaysRun = true)
    public void setDriverManager(){
        String browserType = System.getProperty("browserName");
        driverManager = DriverManager.newDriver(browserType == null ? "CHROME" : browserType);
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({"url"})
    @Step("user navigates to {url}")
    public void openPage(String url) {
        navigationPage = new NavigationPage(driverManager.getWebDriver(url));
    }

    @AfterSuite(alwaysRun = true)
    public void closeDriver() {
        driverManager.getWebDriver().close();
    }

    public NavigationPage getNavigationPage() {
        return navigationPage;
    }
    @Attachment(value = "screenshot", type = "image/png", fileExtension = ".png")
    public byte[] attachScreenshotPNG(String feature) throws IOException {
        return Files.readAllBytes(Paths.get(String.format("/%s/to/image.png", feature)));
    }

}
