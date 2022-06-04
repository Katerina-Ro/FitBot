package com.example.demo.ui;

import com.example.demo.dao.Visitors;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class WhoComeTodayController {
    @FXML
    private Button backButton;

    @FXML
    private TableColumn<Visitors, String> firstNameColumn;

    @FXML
    private TableColumn<Visitors, String> nameColumn;

    @FXML
    private TableColumn<Visitors, String> patronymicColumn;

    @FXML
    private TableColumn<Visitors, String> phoneNumberColumn;

    @FXML
    private TableView<Visitors> tableWhichPass;

    @FXML
    private Button writeOffVisitButton;

    @FXML
    void initialize() {

    }
}
