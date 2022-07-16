package com.example.demo.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class CreateVisitController {
    @FXML
    private Button backButton;

    @FXML
    private TextField createCountVisitsInThisDay;

    @FXML
    private TextField createDateVisit;

    @FXML
    private Button createVisitInDB;

    @FXML
    private Label dateEndColumn;

    @FXML
    private Label dateStartColumn;

    @FXML
    private Label passNumberLableDB;

    @FXML
    private Label phoneNumber;

    @FXML
    private Label phoneNumberLableDB;

    @FXML
    private Label visitLimitColumn;

    @FXML
    public void initialize(Stage createVisitStage, Image image) {

    }
}
