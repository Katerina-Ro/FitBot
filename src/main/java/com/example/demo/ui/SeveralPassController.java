package com.example.demo.ui;

import com.example.demo.dao.Pass;
import com.example.demo.util.FillingFieldsHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class SeveralPassController {
    private final FillingFieldsHelper fillingFieldsHelper;

    @FXML
    private Button backButton;

    @FXML
    private Label phoneLabel;

    @FXML
    private TableColumn<Pass, ?> changeVisitButton;

    @FXML
    private TableColumn<Pass, ?> countVisitsInThisDayColumn;

    @FXML
    private TableColumn<Pass, ?> dateVisitColumn;

    @FXML
    private TableColumn<Pass, ?> deleteVisitButton;

    @FXML
    private TableColumn<Pass, ?> deleteVisitButton1;

    @FXML
    private TableColumn<Pass, ?> passNumberColumn;

    @FXML
    private TableView<?> tableVisits;

    public SeveralPassController() {
        this.fillingFieldsHelper = new FillingFieldsHelper();
    }
}
