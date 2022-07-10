module com.example.demo {
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
    requires java.sql;
    requires spring.context;
    requires spring.jdbc;
    requires spring.beans;
    requires commons.dbcp;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires java.annotation;

    //opens com.example.demo5.repositories to javafx.fxml;
    exports com.example.demo.dao to javafx.fxml;
    exports com.example.demo.ui to javafx.fxml;
    opens com.example.demo.ui to javafx.fxml;
    opens com.example.demo.dao.repositories.impl to lombok;
    opens com.example.demo.dao.supportTables to javafx.base;
    opens com.example.demo;
    exports com.example.demo.dao.supportTables to javafx.fxml;
    //exports com.example.demo5.config to javafx.fxml;
    //exports com.example.demo5.model to javafx.fxml;
    //exports com.example.demo5.supportTables to javafx.fxml;
    exports com.example.demo;

    //exports com.example.demo5.ui;
}