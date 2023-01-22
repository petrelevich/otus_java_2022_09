package ru.otus.mainpackage.configs;

public class AppConfigForBean {
    private final String paramName;

    public AppConfigForBean(String paramName) {
        this.paramName = paramName;
    }

    public String getParamName() {
        return paramName;
    }
}
