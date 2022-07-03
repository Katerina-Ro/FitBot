package com.example.demo.ui;

import com.example.demo.dao.Pass;
import com.example.demo.util.FillingFieldsHelper;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class UnFreezePassController {
    private final FillingFieldsHelper fillingFieldsHelper;
    private ObservableList<Pass> passObservableList;
    private AtomicReference<String> phoneNumberForSearch = new AtomicReference<>();
    private AtomicInteger newCountFreezeForDB = new AtomicInteger();
    private AtomicReference<String> newDateStartFreezeForDB = new AtomicReference<>();
    private AtomicInteger passIdForDB = new AtomicInteger();

    @FXML
    private Button backButton;
    @FXML
    private Button unFreezeButton;

    @FXML
    private Label countFreezeDB;
    private final IntegerProperty countFreezeDBProperty = new SimpleIntegerProperty();
    @FXML
    private TextField dateEndFreezeValue;
    @FXML
    private Label dateStartFreezeDB;
    private final ObjectProperty<LocalDate> dateStartFreezeDBProperty = new SimpleObjectProperty<>();
    @FXML
    private TextField inputPhoneNumber;
    private final StringProperty phoneNumberProperty = new SimpleStringProperty("");
    @FXML
    private Label passIdValue;
    private final IntegerProperty passIdValueProperty = new SimpleIntegerProperty();

    public UnFreezePassController() {
        this.fillingFieldsHelper = new FillingFieldsHelper();
    }

    @FXML
    void initialize(Stage stageUnFreezePass, Image image) {

        backButton.setOnAction(event -> stageUnFreezePass.close());
    }
}
