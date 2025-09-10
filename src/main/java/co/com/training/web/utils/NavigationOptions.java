package co.com.training.web.utils;

public enum NavigationOptions {

    REGISTRATION("RegistrationLogin", "AngularJS User Registration and Login Example"),
    SEARCH_FILTER("SearchFilter", "");

    private final String option;
    private final String titlePage;

    NavigationOptions(String option, String titlePage) {
        this.option = option;
        this.titlePage = titlePage;
    }

    public String getTitlePage() {
        return titlePage;
    }

    public String getOption() {
        return option;
    }
}
