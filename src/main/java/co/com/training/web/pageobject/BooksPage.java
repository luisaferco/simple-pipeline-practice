package co.com.training.web.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BooksPage extends BasePage{

    @FindBy(id = "d_table")
    private WebElement table;

    private String titlePage;

    public BooksPage(WebDriver driver) {
        super(driver);
    }
}
