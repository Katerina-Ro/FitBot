module com.mainwindow.studentattendancerecords {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires spring.data.jpa;
    requires spring.data.commons;
    requires spring.context;
    requires java.persistence;
    requires lombok;
    requires spring.beans;
    requires org.jetbrains.annotations;
    requires java.sql;
    requires commons.dbcp;
    requires spring.jdbc;

    opens com.mainwindow.studentattendancerecords to javafx.fxml;
    exports com.mainwindow.studentattendancerecords;
}