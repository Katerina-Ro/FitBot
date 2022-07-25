package com.example.demo.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ExistPassController {
    private final ActionsWithPassController actionsWithPassController;

    @FXML
    private Button backButton;
    @FXML
    private Button getInfoPassButton;

    public ExistPassController() {
        this.actionsWithPassController = new ActionsWithPassController();
    }

    @FXML
    void initialize(Stage stageExistPass, Image image, String phoneNumber) {
        backButton.setOnAction(event -> stageExistPass.close());
        getInfoPassButton.setOnAction(event -> actionsWithPassController.openWindowGetInfoPass(image, phoneNumber));
    }
}
