package com.example.demo.util;

public enum PatternTemplate {
    INTEGER_LINE("(\\d+\\.?\\d*)?"),
    FIRST_IN_PHONE("^7+"),
    STRING_LINE("[а-яА-Я]+ || [a-zA-Z]+");

    private final String template;

    PatternTemplate(String template) {
        this.template = template;
    }

    public String getTemplate() {
        return template;
    }
}
