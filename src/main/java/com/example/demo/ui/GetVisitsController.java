package com.example.demo.ui;

import com.example.demo.dao.Visits;
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
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;

import java.time.LocalDate;

public class GetVisitsController {
    private final FillingFieldsHelper fillingFieldsHelper;
    private ObservableList<Visits> visitsObservableList;

    @FXML
    private Button backButton;
    @FXML
    private Button changeVisitButton;

    @FXML
    private TextField inputPhoneNumber;
    private final StringProperty phoneNumberProperty = new SimpleStringProperty("");
    @FXML
    private TableColumn<Visits, ?> checkMarkButton;
    @FXML
    private TableColumn<Visits, Integer> countVisitsInThisDayColumn;
    @FXML
    private TableColumn<Visits, LocalDate> dateVisitColumn;
    @FXML
    private TableColumn<Visits, Integer> passNumberColumn;
    @FXML
    private TableView<Visits> tableVisits;

    public GetVisitsController() {
        this.fillingFieldsHelper = new FillingFieldsHelper();
    }


    @FXML
    void initialize(Stage stageGetVisits, Image image) {
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

        tableVisits = new TableView<>();
        tableVisits.setItems(visitsObservableList);
    }

    private void fillVisitsIfEmpty() {
        passNumberColumn.setCellValueFactory(null);
        dateVisitColumn.setCellValueFactory(null);
        countVisitsInThisDayColumn.setCellValueFactory(null);
    }
}
