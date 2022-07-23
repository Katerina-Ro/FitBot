package com.example.demo.ui;

import com.example.demo.dao.supportTables.Visit;
import com.example.demo.util.FillingFieldsHelper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;

import java.time.LocalDate;

public class GetVisitsController {
    private final FillingFieldsHelper fillingFieldsHelper;
    private ObservableList<Visit> visitsObservableList;

    @FXML
    private Button backButton;

    @FXML
    private TextField inputPhoneNumber;
    private final StringProperty phoneNumberProperty = new SimpleStringProperty("");
    @FXML
    private TableColumn<Visit, Button> changeVisitButton;
    @FXML
    private TableColumn<Visit, Button> deleteVisitButton;
    @FXML
    private TableColumn<Visit, Integer> countVisitsInThisDayColumn;
    @FXML
    private TableColumn<Visit, LocalDate> dateVisitColumn;
    @FXML
    private TableColumn<Visit, Integer> passNumberColumn;
    @FXML
    private TableView<Visit> tableVisits;

    public GetVisitsController() {
        this.fillingFieldsHelper = new FillingFieldsHelper();
    }


    @FXML
    void initialize(Stage stageGetVisits) {
        observeInputPhoneNumber();
        backButton.setOnAction(event -> stageGetVisits.close());
    }

    private void observeInputPhoneNumber() {
        FillingFieldsHelper.correctInputPhoneLine(inputPhoneNumber);
        inputPhoneNumber.textProperty().bindBidirectional(phoneNumberProperty, new DefaultStringConverter());
        phoneNumberProperty.addListener((observable, oldValue, newValue) -> {
            if (phoneNumberProperty.length().get() == 11) {
                visitsObservableList = fillingFieldsHelper.getAllVisits(phoneNumberProperty);
                if (!visitsObservableList.isEmpty()) {
                    initDataToTable();
                } else {
                    fillVisitsIfEmpty();
                }
            } else {
                fillVisitsIfEmpty();
            }
        });
    }

    private void initDataToTable() {
        passNumberColumn.setCellValueFactory(new PropertyValueFactory<>("pass"));
        dateVisitColumn.setCellValueFactory(new PropertyValueFactory<>("dateVisit"));
        countVisitsInThisDayColumn.setCellValueFactory(new PropertyValueFactory<>("countVisit"));
        changeVisitButton.setCellValueFactory(new PropertyValueFactory<>("changeVisitButton"));
        deleteVisitButton.setCellValueFactory(new PropertyValueFactory<>("deleteVisitButton"));

        tableVisits.setItems(visitsObservableList);
    }

    private void fillVisitsIfEmpty() {
        passNumberColumn.setCellValueFactory(null);
        dateVisitColumn.setCellValueFactory(null);
        countVisitsInThisDayColumn.setCellValueFactory(null);
    }
}
