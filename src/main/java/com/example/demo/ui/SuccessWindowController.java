package com.example.demo.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class SuccessWindowController {

    @FXML
    private Button backToMainMenuButton;

    @FXML
    private Label textSuccessValueLable;

    @FXML
    public void initialize(Stage stageSuccessWindow, String messageLabel) {
        textSuccessValueLable.setText(messageLabel);
        backToMainMenuButton.setOnAction(event -> stageSuccessWindow.close());
    }
}
