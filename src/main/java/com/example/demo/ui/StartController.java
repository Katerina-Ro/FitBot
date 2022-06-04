package com.example.demo.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class StartController {
    @FXML
    private Button actionWithPassButton;

    @FXML
    private Button actionWithStudentButton;

    @FXML
    private Button closeApplicationButton;

    @FXML
    private Button settingsBotButton;

    @FXML
    private Button whoComeToDayButton;

    @FXML
    private Button whoDontComeToDayButton;

    @FXML
    private Button whoNothingSayButton;

    @FXML
    public void initialize(Stage primaryStage) {
        whoComeToDayButton.setOnAction(event -> openWindowWhoComeToDay());
        whoDontComeToDayButton.setOnAction(event -> openWindowWhoDontComeToDay());
        whoNothingSayButton.setOnAction(event -> );
        // getInfoAboutPassButton.setOnAction(event -> openWindowGetInfoAboutStudent());
        closeApplicationButton.setOnAction(event -> primaryStage.close());
        settingsBotButton.setOnAction(actionEvent -> openWindowSettingsBot());
    }

    @FXML
    public void  openWindowWhoDontComeToDay() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/whoSaysNoToday-view.fxml"));
        Parent root1;
        try {
            root1 = fxmlLoader.load();
            Stage stage1 = new Stage();
            stage1.setResizable(false);
            stage1.getIcons().add(new Image("file:src/main/java/com/example/demo/assets/iconic.png"));
            stage1.setTitle("Кто сегодня не придет");
            stage1.setScene(new Scene(root1, 700, 480));

            WhoSaysNoTodayController whoSaysNoTodayController = fxmlLoader.getController();
            whoSaysNoTodayController.initialize();

            stage1.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openWindowWhoComeToDay() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/whoComeToday-view.fxml"));
        Parent root1;
        try {
            root1 = fxmlLoader.load();
            Stage stage1 = new Stage();
            stage1.setResizable(false);
            stage1.getIcons().add(new Image("file:src/main/java/com/example/demo/assets/iconic.png"));
            stage1.setTitle("Кто сегодня придет");
            stage1.setScene(new Scene(root1, 700, 480));

            WhoComeTodayController whoComeTodayController = fxmlLoader.getController();
            whoComeTodayController.initialize();

            stage1.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openWindowGetInfoAboutStudent() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/inputPhoneNumber-view.fxml"));
        Parent root1;
        try {
            root1 = fxmlLoader.load();
            Stage stage1 = new Stage();
            stage1.setResizable(false);
            stage1.getIcons().add(new Image("file:src/main/java/com/example/demo/assets/iconic.png"));
            stage1.setTitle("Данные абонемента");
            stage1.setScene(new Scene(root1, 700, 500));

            InputPhoneNumberController inputPhoneNumberController = fxmlLoader.getController();
            inputPhoneNumberController.initialize(stage1);

            stage1.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openWindowSettingsBot() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/settingsBot-view.fxml"));
        Parent root1;
        try {
            root1 = fxmlLoader.load();
            Stage stage1 = new Stage();
            stage1.setResizable(false);
            stage1.getIcons().add(new Image("file:src/main/java/com/example/demo/assets/iconic.png"));
            stage1.setTitle("Настройки бота");
            stage1.setScene(new Scene(root1, 590, 337));

            SettingsBotController settingsBotController = fxmlLoader.getController();
            settingsBotController.initialize(stage1);

            stage1.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}