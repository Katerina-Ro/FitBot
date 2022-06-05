package com.example.demo.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class SettingsBotController {
    @FXML
    private Button backButton;

    @FXML
    private Button changeTimeAskButton;

    @FXML
    private Button turnOffAskInChatButton;

    @FXML
    private Button turnOffBotButton;

    @FXML
    private Button turnOnAskInChatButton;

    @FXML
    private Button turnOnBotButton;

    @FXML
    void initialize(Stage stageSettings, Image image) {
        backButton.setOnAction(event -> {stageSettings.close();
        });

    }


    public void openWindowExceptionLoad() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/exceptionLoadWindow-view.fxml"));
        Parent root1;
        try {
            root1 = fxmlLoader.load();
            Stage stage1 = new Stage();
            stage1.setResizable(false);
            stage1.getIcons().add(new Image("file:src/main/java/com/example/demo/assets/iconic.png"));
            stage1.setTitle("Данные абонемента");
            stage1.setScene(new Scene(root1, 700, 500));
            stage1.show();
        } catch (IOException e) {
            e.printStackTrace();
            openWindowExceptionLoad();
        }
    }
}
