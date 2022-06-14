package com.example.demo.util;

public enum PatternTemplate {
    INTEGER_LINE("(\\d+\\.?\\d*)?"),
    FIRST_IN_PHONE("7+"),
    STRING_LINE("^([а-яА-Я])$"),
    DATE("(0?[1-9]|[12][0-9]|3[01]).(0?[1-9]|1[012]).((19|20)\\d\\d)");

    private final String template;

    PatternTemplate(String template) {
        this.template = template;
    }

    public String getTemplate() {
        return template;
    }
}
