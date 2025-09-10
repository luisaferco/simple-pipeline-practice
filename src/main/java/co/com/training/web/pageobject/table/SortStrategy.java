package co.com.training.web.pageobject.table;

import org.openqa.selenium.WebElement;
import java.util.function.Predicate;

public interface SortStrategy<T> extends Predicate<WebElement> {

    boolean isSortable(String className);
    SortState getCurrentState(T element);  // Movido a método abstracto

    enum SortState {
        ASCENDING, DESCENDING, SORTABLE, UNSORTABLE
    }
    default int getClicksNeeded(T element, SortOrder targetOrder) {
        switch (getCurrentState(element)) {
            case ASCENDING:
                return targetOrder == SortOrder.ASCENDING ? 0 : 1;
            case DESCENDING:
                return targetOrder == SortOrder.DESCENDING ? 0 : 1;
            case SORTABLE:
                return targetOrder == SortOrder.ASCENDING ? 1 : 2;
            case UNSORTABLE:
                throw new UnsupportedOperationException("Column is not sortable");
            default:
                throw new IllegalStateException("Unexpected value: " + getCurrentState(element));
        }
    }

}
