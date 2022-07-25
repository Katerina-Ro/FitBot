package com.example.demo.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class SeveralController {
    private final ActionsWithStudentController actionsWithStudentController;

    @FXML
    private Button backButton;
    @FXML
    private Button getInfoStudentButton;

    public SeveralController() {
        this.actionsWithStudentController = new ActionsWithStudentController();
    }

    @FXML
    void initialize(Stage stageSeveral, Image image, String phoneNumber) {
        getInfoStudentButton.setOnAction(event -> actionsWithStudentController.openWindowGetInfoStudent(image, phoneNumber));
        backButton.setOnAction(event -> stageSeveral.close());
    }
}
