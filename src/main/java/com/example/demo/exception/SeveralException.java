package com.example.demo.exception;

public class SeveralException extends Exception {
    private static final String MESSAGE = "Несколько абонементов";
    public SeveralException() {
        super(MESSAGE);
    }
}
