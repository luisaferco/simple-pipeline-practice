package co.com.training.web.pageobject.table;

import org.openqa.selenium.WebElement;

public class SortStrategies {

    private SortStrategies(){

    }

    public static SortStrategy<WebElement> dataSortStrategy() {
        return new SortStrategy<>() {
            @Override
            public boolean isSortable(String className) {
                return className.contains("sortable") ||
                        className.contains("data-sort");
            }

            @Override
            public SortState getCurrentState(WebElement element) {
                String className = element.getAttribute("class");
                if (className.contains("sorting_asc")) {
                    return SortState.ASCENDING;
                } else if (className.contains("sorting_desc")) {
                    return SortState.DESCENDING;
                } else if (className.contains("sorting")) {
                    return SortState.SORTABLE;
                }
                return SortState.UNSORTABLE;
            }

            @Override
            public boolean test(WebElement header) {
                return isSortable(header.getAttribute("class"));
            }
        };
    }

}
