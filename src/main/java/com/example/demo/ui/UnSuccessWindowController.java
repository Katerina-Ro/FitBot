package com.example.demo.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class UnSuccessWindowController {
    @FXML
    private Button backToMainMenuButton;

    @FXML
    private Button repeatButton;

    @FXML
    public void initialize(Stage stageUnSuccessWindow, EventHandler<ActionEvent> var1) {
        backToMainMenuButton.setOnAction(event -> stageUnSuccessWindow.close());
        //repeatButton.setOnAction(event -> var1);
    }

    @FXML
    public void repeat(EventHandler<ActionEvent> var1) {

    }
}
