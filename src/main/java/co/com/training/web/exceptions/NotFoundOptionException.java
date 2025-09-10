package co.com.training.web.exceptions;

public class NotFoundOptionException extends AssertionError{

    private static final String NOT_FOUND_ELEMENT = "navigation option not found in the page: %s";

    public NotFoundOptionException(String opt) {
        super(String.format(NOT_FOUND_ELEMENT,opt));
    }
}
