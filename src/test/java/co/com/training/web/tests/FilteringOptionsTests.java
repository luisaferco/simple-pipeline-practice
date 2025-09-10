package co.com.training.web.tests;

import co.com.training.web.pageobject.NavigationPage;
import co.com.training.web.utils.NavigationOptions;
import io.qameta.allure.AllureId;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.testng.annotations.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

@Epic("Filtering table")
@Feature("Filter by criteria")
public class FilteringOptionsTests extends BaseTest{

    @BeforeTest
    public void before() {
        System.out.println("Before test");
    }

    @AfterTest
    public void after() {
        System.out.println("After test");
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("Before method");
    }

    @DataProvider(name = "dataFilteringOptions")
    public static Object[] dataProvider() {
        return Arrays.stream(NavigationOptions.values())
                .toArray();
    }

    @DataProvider (name = "dataFilterTable")
    public Object[][] dpMethod(){
        return new Object[][] {{"Cash","EXPENDITURE"}};
    }

    @Description("Validate navigation to multiple options")
    @AllureId("TMS-123")
    @Test(dataProvider = "dataFilteringOptions", groups = {"mainGroup", "filteringGroup"})
    public void filterBy(NavigationOptions option) {
        NavigationPage navigationPage = getNavigationPage();
        navigationPage.navigateTo(option.getOption());
        Assertions.assertThat(navigationPage.getTitle()).as(String.format("should navigate to %s", option.getOption()))
                .isEqualTo(option.getTitlePage());
    }

    @Test(dataProvider = "dataFilterTable", groups = {"filteringGroup"})
    public void navigateToTableAndSearchBy(String account, String type) {
        NavigationPage navigationPage = getNavigationPage();
        List<Map<String, String>> searchResults = navigationPage
                .navigateToSearchFilter()
                      .searchByAccount(account)
                      .searchByType(type)
                      .getSearchResults();

        Assertions.assertThat(searchResults)
                .as(format("Expected all search results filtered by type '%s' and account '%s' ",type, account))
                        .extracting("Type","Account")
                                .contains(Tuple.tuple(type,account));
    }
}
