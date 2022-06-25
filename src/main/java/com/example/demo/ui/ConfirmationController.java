package com.example.demo.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ConfirmationController {
    @FXML
    private Button backButton;
    @FXML
    private Button yesButton;

    @FXML
    public void initialize(Stage stageConfirmation, EventHandler<ActionEvent> var1) {
        backButton.setOnAction(event -> stageConfirmation.close());
        yesButton.setOnAction(var1);
    }
}
