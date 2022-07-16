package com.example.demo.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class DeleteVisitController {
    @FXML
    private Button backButton;
    @FXML
    private Button deleteVisitButton;

    @FXML
    private Label countVisitsInThisDayValueDB;
    @FXML
    private Label dateVisitValueDB;
    @FXML
    private Label passNumberValueDB;

    @FXML
    private TextField inputPhoneNumber;


    @FXML
    void initialize(Stage stageDeleteVisit, Image image) {

        backButton.setOnAction(event -> stageDeleteVisit.close());
    }
}
