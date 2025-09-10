package co.com.training.web.pageobject.table;

import co.com.training.web.pageobject.BasePage;
import io.qameta.allure.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TablePage extends BasePage {


    @FindBy(id = "input1")
    private WebElement payeeInput;

    @FindBy(id = "input2")
    private WebElement dropDownByAccount;

    @FindBy(id = "input3")
    private WebElement dropDownByType;

    @FindBy(id = "input4")
    private  WebElement ExpenditurePayeesInput;

    @FindBy(css = "thead tr th")
    private List<WebElement> headersTable;

    @FindBy(css = "tbody tr")
    private  List<WebElement> rowsTable;

    private final WebElement table;

    private final SortStrategy<WebElement> sortStrategy;

    public TablePage(WebDriver driver, WebElement table, SortStrategy<WebElement> sortStrategy) {
        super(driver);
        this.table = table.findElement(By.tagName("table"));
        this.sortStrategy = sortStrategy;
    }
    public TablePage(WebDriver driver, WebElement table) {
        super(driver);
        this.table = table.findElement(By.tagName("table"));
        this.sortStrategy = SortStrategies.dataSortStrategy();

    }

    private void selectByType(String typeName) {
        Select select = new Select(dropDownByType);
        select.selectByVisibleText(typeName);
    }
    private void selectByAccount(String accountName) {
        Select select = new Select(dropDownByAccount);
        select.selectByVisibleText(accountName);
    }

    public TablePage searchByAccount(String account) {
        if(isNotNullOrEmpty(account)){
            selectByAccount(account);
        }
        return this;
    }

    @Description("user searchs by {0}")
    public TablePage searchByType(String type) {
        if(isNotNullOrEmpty(type)){
            selectByType(type);
        }
        return this;
    }

    public TablePage searchByPayeeName(String name) {
        if(isNotNullOrEmpty(name)){
            type(payeeInput, name);
        }
        return this;
    }

    public List<Map<String, String>> getSearchResults() {
        List<Map<String, String>> table = new ArrayList<>();
        List<String> headers = headersTable.stream().map(WebElement::getText).collect(Collectors.toList());
        for(int row = 1; row <= rowsTable.size(); row++) {
            Iterator<String> iterator = headers.iterator();
            Map<String, String> register = new HashMap<>();
            for (int column = 1 ; column <= headers.size(); column++) {
                WebElement item = rowsTable.get(row - 1).findElement(By.cssSelector(String.format("tr:nth-child(%s) td:nth-child(%s)", row, column)));
                register.put(iterator.next(),item.getText());
            }
            table.add(register);
        }
        return table;
    }

    public TablePage sorTableBy(String headerName, SortOrder targetOrder) {
        WebElement headerFound = headersTable.stream()
                .filter(header -> header.getText().contains(headerName))
                        .findFirst().orElseThrow(() -> new NotFoundException(String.format("header with name '%s' does not exist", headerName)));
        if (!sortStrategy.test(headerFound)) {
            throw new UnsupportedOperationException("Column " + headerName + " is not sortable");
        }
        IntStream.iterate(0,click -> click < sortStrategy.getClicksNeeded(headerFound,targetOrder), click -> click +1)
                        .forEach(click -> click(headerFound));
        return this;
    }


    private boolean isNotNullOrEmpty(String str){
       return str != null && !str.trim().isEmpty();
    }
}
