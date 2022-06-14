package com.example.demo.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ChangeStudentController {
    @FXML
    private Button backButton;

    @FXML
    private Button changeButton;

    @FXML
    private Label dateEndColumn;

    @FXML
    private Label dateStartColumn;

    @FXML
    private Label firstNameValueDB;

    @FXML
    private Label fullNameValue1;

    @FXML
    private TextField inputPhoneNumber;

    @FXML
    private Label nameValueDB;

    @FXML
    private TextField newFirstNameValue;

    @FXML
    private TextField newNameValue;

    @FXML
    private TextField newPatronymicValue;

    @FXML
    private TextField newPhoneNumberValue;

    @FXML
    private Label passIdValue;

    @FXML
    private Label patronymicValueDB;

    @FXML
    private Label phoneNumber;

    @FXML
    private Label phoneNumberValueDB;

    @FXML
    private Label visitLimitColumn;

    @FXML
    void initialize(Stage stageChangeStudent, Image image, TextField phoneNumber) {

        backButton.setOnAction(event -> stageChangeStudent.close());
    }
}
