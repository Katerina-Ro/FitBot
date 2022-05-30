package com.example.demo.util;

public enum PatternTemplate {
    INTEGER_LINE("(\\d+\\.?\\d*)?");

    private final String template;

    PatternTemplate(String template) {
        this.template = template;
    }

    public String getTemplate() {
        return template;
    }
}
