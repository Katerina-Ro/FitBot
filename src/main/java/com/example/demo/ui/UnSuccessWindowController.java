package com.example.demo.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class UnSuccessWindowController {
    @FXML
    private Button backToMainMenuButton;
    @FXML
    private Button repeatButton;
    @FXML
    private Label textUnSuccessValueLabel;

    @FXML
    public void initialize(Stage stageUnSuccessWindow, EventHandler<ActionEvent> var1, String messageLabel) {
        textUnSuccessValueLabel.setText(messageLabel);
        backToMainMenuButton.setOnAction(event -> stageUnSuccessWindow.close());
        repeatButton.setOnAction(var1);
    }
}
