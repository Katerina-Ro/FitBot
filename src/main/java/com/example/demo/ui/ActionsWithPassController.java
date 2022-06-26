package com.example.demo.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class ActionsWithPassController {
    @FXML
    private Button backToMainMenuButton;

    @FXML
    private Button changePassButton;

    @FXML
    private Button createPassButton;

    @FXML
    private Button deletePassButton;

    @FXML
    private Button freezePassButton;

    @FXML
    private Button getInfoPassButton;

    @FXML
    private Button unFreezePassButton;

    @FXML
    void initialize(Stage stageActionWithPass, Image image) {
        createPassButton.setOnAction(event -> openWindowCreatePass(image, null));
        changePassButton.setOnAction(event -> openWindowChangePass(image));
        deletePassButton.setOnAction(event -> openWindowDeletePass(image));
        getInfoPassButton.setOnAction(event -> openWindowGetInfoPass(image, null));
        freezePassButton.setOnAction(actionEvent -> openWindowFreezePass(image));
        unFreezePassButton.setOnAction(event -> openWindowUnFreezePass(image));
        backToMainMenuButton.setOnAction(event -> stageActionWithPass.close());
    }

    @FXML
    public void openWindowUnFreezePass(Image image) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/unFreezePass-view.fxml"));
        Parent root1;
        try {
            root1 = fxmlLoader.load();
            Stage stageUnFreezePass = new Stage();
            stageUnFreezePass.setResizable(false);
            stageUnFreezePass.getIcons().add(image);
            stageUnFreezePass.setTitle("Разморозка абонемента");
            stageUnFreezePass.setScene(new Scene(root1, 700, 288));

            UnFreezePassController unFreezePassController = fxmlLoader.getController();
            unFreezePassController.initialize(stageUnFreezePass, image);

            stageUnFreezePass.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openWindowFreezePass(Image image) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/freezePass-view.fxml"));
        Parent root1;
        try {
            root1 = fxmlLoader.load();
            Stage stageFreezePass = new Stage();
            stageFreezePass.setResizable(false);
            stageFreezePass.getIcons().add(image);
            stageFreezePass.setTitle("Заморозка абонемента");
            stageFreezePass.setScene(new Scene(root1, 700, 288));

            FreezePassController freezePassController = fxmlLoader.getController();
            freezePassController.initialize(stageFreezePass, image);

            stageFreezePass.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openWindowCreatePass(Image image, String phoneNumber) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/createPass-view.fxml"));
        Parent root1;
        try {
            root1 = fxmlLoader.load();
            Stage stageCreatePass = new Stage();
            stageCreatePass.setResizable(false);
            stageCreatePass.getIcons().add(image);
            stageCreatePass.setTitle("Внесение абонемента в базу данных");
            stageCreatePass.setScene(new Scene(root1, 700, 495));

            CreatePassController createPassController = fxmlLoader.getController();
            createPassController.initialize(stageCreatePass, image, phoneNumber);

            stageCreatePass.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openWindowChangePass(Image image) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/changePass-view.fxml"));
        Parent root1;
        try {
            root1 = fxmlLoader.load();
            Stage stageChangePass = new Stage();
            stageChangePass.setResizable(false);
            stageChangePass.getIcons().add(image);
            stageChangePass.setTitle("Изменение данных абонемента");
            stageChangePass.setScene(new Scene(root1, 700, 500));

            ChangePassController changePassController = fxmlLoader.getController();
            changePassController.initialize(stageChangePass, image);

            stageChangePass.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openWindowDeletePass(Image image) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/deletePass-view.fxml"));
        Parent root1;
        try {
            root1 = fxmlLoader.load();
            Stage stageDeletePass = new Stage();
            stageDeletePass.setResizable(false);
            stageDeletePass.getIcons().add(image);
            stageDeletePass.setTitle("Удаление абонемента из базы данных");
            stageDeletePass.setScene(new Scene(root1, 700, 500));

            DeletePassController deletePassController = fxmlLoader.getController();
            deletePassController.initialize(stageDeletePass, image);

            stageDeletePass.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openWindowGetInfoPass(Image image, TextField phoneNumber) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/inputPhoneNumber-view.fxml"));
        Parent root1;
        try {
            root1 = fxmlLoader.load();
            Stage stageGetInfoPass = new Stage();
            stageGetInfoPass.setResizable(false);
            stageGetInfoPass.getIcons().add(image);
            stageGetInfoPass.setTitle("Данные абонемента");
            stageGetInfoPass.setScene(new Scene(root1, 700, 535));

            InputPhoneNumberController inputPhoneNumberController = fxmlLoader.getController();
            inputPhoneNumberController.initialize(stageGetInfoPass, image, phoneNumber);

            stageGetInfoPass.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
