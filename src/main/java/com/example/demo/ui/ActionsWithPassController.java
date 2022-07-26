package com.example.demo.ui;

import com.example.demo.dao.Pass;
import com.example.demo.util.GetCommonWindowHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
        createPassButton.setOnAction(event -> openWindowCreatePass(image));
        changePassButton.setOnAction(event -> openWindowChangePass(image));
        deletePassButton.setOnAction(event -> openWindowDeletePass(image, null));
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
            stageUnFreezePass.setScene(new Scene(root1, 578, 292));

            UnFreezePassController unFreezePassController = fxmlLoader.getController();
            unFreezePassController.initialize(stageUnFreezePass, image);

            stageUnFreezePass.show();
        } catch (IOException e) {
            String message = "Произошла ошибка во время открытия окна. Обратитесь к разработчику";
            new GetCommonWindowHelper().openWindowUnSuccess(image, event -> openWindowUnFreezePass(image), message);
        }
    }

    @FXML
    public void openWindowUnFreezePass2(Image image, String phoneNumber, Pass pass) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/unFreezePass-view2.fxml"));
        Parent root1;
        try {
            root1 = fxmlLoader.load();
            Stage stageUnFreezePass = new Stage();
            stageUnFreezePass.setResizable(false);
            stageUnFreezePass.getIcons().add(image);
            stageUnFreezePass.setTitle("Разморозка абонемента");
            stageUnFreezePass.setScene(new Scene(root1, 578, 292));

            UnFreezePassController unFreezePassController = fxmlLoader.getController();
            unFreezePassController.initialize2(stageUnFreezePass, image, phoneNumber, pass);

            stageUnFreezePass.show();
        } catch (IOException e) {
            String message = "Произошла ошибка во время открытия окна. Обратитесь к разработчику";
            new GetCommonWindowHelper().openWindowUnSuccess(image, event -> openWindowUnFreezePass(image), message);
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
            String message = "Произошла ошибка во время открытия окна. Обратитесь к разработчику";
            new GetCommonWindowHelper().openWindowUnSuccess(image, event -> openWindowFreezePass(image), message);
        }
    }

    @FXML
    public void openWindowCreatePass(Image image) {
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
            createPassController.initialize(stageCreatePass, image);

            stageCreatePass.show();
        } catch (IOException e) {
            String message = "Произошла ошибка во время открытия окна. Обратитесь к разработчику";
            new GetCommonWindowHelper().openWindowUnSuccess(image, event -> openWindowCreatePass(image), message);
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
            String message = "Произошла ошибка во время открытия окна. Обратитесь к разработчику";
            new GetCommonWindowHelper().openWindowUnSuccess(image, event -> openWindowChangePass(image), message);
        }
    }

    @FXML
    public void openWindowDeletePass(Image image, String phoneNumber) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/deletePass-view.fxml"));
        Parent root1;
        try {
            root1 = fxmlLoader.load();
            Stage stageDeletePass = new Stage();
            stageDeletePass.setResizable(false);
            stageDeletePass.getIcons().add(image);
            stageDeletePass.setTitle("Удаление абонемента из базы данных");
            stageDeletePass.setScene(new Scene(root1, 700, 527));

            DeletePassController deletePassController = fxmlLoader.getController();
            deletePassController.initialize(stageDeletePass, image, phoneNumber);

            stageDeletePass.show();
        } catch (IOException e) {
            String message = "Произошла ошибка во время открытия окна. Обратитесь к разработчику";
            new GetCommonWindowHelper().openWindowUnSuccess(image, event -> openWindowDeletePass(image, phoneNumber), message);
        }
    }

    @FXML
    public void openWindowGetInfoPass(Image image, String phoneNumber) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/inputPhoneNumber-view.fxml"));
        Parent root1;
        try {
            root1 = fxmlLoader.load();
            Stage stageGetInfoPass = new Stage();
            stageGetInfoPass.setResizable(false);
            stageGetInfoPass.getIcons().add(image);
            stageGetInfoPass.setTitle("Данные абонемента");
            stageGetInfoPass.setScene(new Scene(root1, 700, 507));

            InputPhoneNumberController inputPhoneNumberController = fxmlLoader.getController();
            inputPhoneNumberController.initialize(stageGetInfoPass, image, phoneNumber);

            stageGetInfoPass.show();
        } catch (IOException e) {
            String message = "Произошла ошибка во время открытия окна. Обратитесь к разработчику";
            new GetCommonWindowHelper().openWindowUnSuccess(image, event -> openWindowGetInfoPass(image, phoneNumber), message);
        }
    }
}
