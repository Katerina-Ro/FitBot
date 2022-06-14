package com.example.demo.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class CreatePassController {
    @FXML
    private Button backButton;

    @FXML
    private Label countFreezeColumn;

    @FXML
    private TextField createCountFreeze;

    @FXML
    private TextField createCountVisits;

    @FXML
    private TextField createDateEndPass;

    @FXML
    private TextField createDateStartFreeze;

    @FXML
    private TextField createDateStartPass;

    @FXML
    private Button createPassButton;

    @FXML
    private TextField createPhoneNumber;

    @FXML
    private Button createVisitToThisPassButton;

    @FXML
    private Label dateEndColumn;

    @FXML
    private Label dateStartColumn;

    @FXML
    private Label dateStartFreezeColumn;

    @FXML
    private Label phoneNumber;

    @FXML
    private Label visitLimitColumn;

    @FXML
    void initialize(Stage stageCreatePass, Image image) {

    }

    @FXML
    void initialize(Stage stageCreatePass, Image image, String phoneNumber) {
        //createPhoneNumber = phoneNumber;
    }
}
