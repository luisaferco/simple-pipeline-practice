package co.com.training.web.config.custom;

import co.com.training.web.exceptions.NotFoundOptionException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.List;

public class CustomConditions {

    private CustomConditions() {
    }

    public static ExpectedCondition<WebElement> itemIsIncludedIn(List<WebElement> elements, final String text) {
        return new ExpectedCondition<WebElement>() {
            private boolean exist = false;
            public WebElement apply(WebDriver webDriver) {
                this.exist = elements.stream().anyMatch(item -> item.getText().contains(text));
                return elements.stream()
                        .filter(item -> item.getText().contains(text))
                        .findFirst()
                        .orElseThrow(() -> new NotFoundOptionException(text));
            }

            public String toString() {
                return String.format("element %s to be include in %s. Item included: \"%s\"", text, elements, this.exist);
            }
        };
    }
}
