module com.example.student {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires lombok;
    requires java.validation;
    requires java.persistence;
    requires spring.data.jpa;
    requires spring.data.commons;
    requires spring.context;
    requires spring.beans;
    requires java.sql;
    requires spring.tx;
    requires telegrambots.meta;
    requires jsr305;
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires telegrambots;
    requires com.google.common;

    opens com.example.student to javafx.fxml;
    exports com.example.student;
}