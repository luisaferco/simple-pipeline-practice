package co.com.training.web.pageobject;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.openqa.selenium.support.PageFactory.initElements;

public abstract class BasePage {

    private static final int TIMEOUT = 10;
    protected ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    protected final WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver.set(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        initElements(driver, this);
    }

    public WebDriver getDriver() {
        return driver.get();
    }

    /**
     *  custom click
     * @param element : WebElement
     * @author luisaferco
     */
    public void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    public void type(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.sendKeys(text);
    }

    protected String getTitlePage() {
        return driver.get().getTitle();
    }

    public void scrollTo(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver.get();
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void switchToFrame(WebElement webElement) {
        driver.get().switchTo().frame(webElement);
    }

    public void switchToFrame(String name) {
        driver.get().switchTo().frame(name);
    }

    /**
     * Método genérico para realizar acciones hasta que se cumpla una condición
     * @param element Elemento sobre el cual realizar la acción
     * @param condition Condición que debe cumplirse
     * @param action Acción a realizar si la condición no se cumple
     */
    protected void waitAndActUntilCondition(
            WebElement element,
            Supplier<Boolean> condition,
            Consumer<WebElement> action) {
        wait.until(driver -> {
            if (condition.get()) {
                return true;
            }
            action.accept(element);
            return false;
        });
    }

    // Sobrecarga para casos donde no necesitamos el WebElement en la acción
    protected void waitAndActUntilCondition(
            Supplier<Boolean> condition,
            Runnable action) {
        wait.until(driver -> {
            if (condition.get()) {
                return true;
            }
            action.run();
            return false;
        });
    }
}
