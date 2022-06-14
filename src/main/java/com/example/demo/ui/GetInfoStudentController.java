package com.example.demo.ui;

import com.example.demo.util.FillingFieldsHelper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;

public class GetInfoStudentController {
    private final ActionsWithStudentController actionsWithStudentController;
    private final ActionsWithPassController actionsWithPassController;
    private ObservableList<String> visitorsObservableList;

    @FXML
    private Button backButton;

    @FXML
    private Button changeStudentButton;

    @FXML
    private Button getInfoPassButton;

    @FXML
    private Label countFreezeColumn;

    @FXML
    private Label dateEndColumn;

    @FXML
    private Label dateStartColumn;

    @FXML
    private Label dateStartFreezeColumn;

    @FXML
    private Label dateStartFreezeValue;

    @FXML
    private Label existActualPassDB;

    @FXML
    private Label firstNameDB;

    @FXML
    private TextField inputPhoneNumber;
    private final StringProperty phoneNumberProperty = new SimpleStringProperty("");

    @FXML
    private Label nameDB;

    @FXML
    private Label passIsFreezeDB;

    @FXML
    private Label patronymicDB;

    @FXML
    private Label phoneNumber;

    @FXML
    private Label visitLimitColumn;

    public GetInfoStudentController() {
        this.actionsWithPassController = new ActionsWithPassController();
        this.actionsWithStudentController = new ActionsWithStudentController();
    }

    @FXML
    void initialize(Stage stageGetInfoStudent, Image image, TextField phoneNumber) {
        if (phoneNumber != null && FillingFieldsHelper.isPhoneNumber(phoneNumber.getText())) inputPhoneNumber = phoneNumber;
        FillingFieldsHelper.correctInputPhoneLine(inputPhoneNumber);
        inputPhoneNumber.textProperty().bindBidirectional(phoneNumberProperty, new DefaultStringConverter());
        phoneNumberProperty.addListener((observable, oldValue, newValue) -> {
            if (phoneNumberProperty.length().get() == 11) {
                visitorsObservableList = getFullNameStudent(phoneNumberProperty, image);

            }
        });
        backButton.setOnAction(event -> stageGetInfoStudent.close());
        changeStudentButton.setOnAction(event -> actionsWithStudentController.openWindowChangeStudent(image, phoneNumber));
        getInfoPassButton.setOnAction(event -> actionsWithPassController.openWindowGetInfoPass(image, phoneNumber));
    }
}
