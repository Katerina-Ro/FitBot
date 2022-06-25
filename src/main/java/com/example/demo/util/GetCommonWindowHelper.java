package com.example.demo.util;

import com.example.demo.ui.ConfirmationController;
import com.example.demo.ui.SuccessWindowController;
import com.example.demo.ui.UnSuccessWindowController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class GetCommonWindowHelper {

    @FXML
    public void openWindowSuccess(Image image, String messageLabel) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/successWindow-view.fxml"));
        Parent root1;
        try {
            root1 = fxmlLoader.load();
            Stage stageSuccessWindow = new Stage();
            stageSuccessWindow.setResizable(false);
            stageSuccessWindow.getIcons().add(image);
            stageSuccessWindow.setTitle("Операция прошла успешно");
            stageSuccessWindow.setScene(new Scene(root1, 331, 133));

            SuccessWindowController successWindowController = fxmlLoader.getController();
            successWindowController.initialize(stageSuccessWindow, messageLabel);

            stageSuccessWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openWindowUnSuccess(Image image, EventHandler<ActionEvent> var1) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/unSuccessWindow-view.fxml"));
        Parent root1;
        try {
            root1 = fxmlLoader.load();
            Stage stageUnSuccessWindow = new Stage();
            stageUnSuccessWindow.setResizable(false);
            stageUnSuccessWindow.getIcons().add(image);
            stageUnSuccessWindow.setTitle("Операция не выполнена");
            stageUnSuccessWindow.setScene(new Scene(root1, 374, 133));

            UnSuccessWindowController unSuccessWindowController = fxmlLoader.getController();
            unSuccessWindowController.initialize(stageUnSuccessWindow, var1);

            stageUnSuccessWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openWindowConfirmation(Image image, EventHandler<ActionEvent> var1) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/confirmation-view.fxml"));
        Parent root1;
        try {
            root1 = fxmlLoader.load();
            Stage stageWindowConfirmation = new Stage();
            stageWindowConfirmation.setResizable(false);
            stageWindowConfirmation.getIcons().add(image);
            stageWindowConfirmation.setTitle("Подтверждение удаления");
            stageWindowConfirmation.setScene(new Scene(root1, 331, 133));

            ConfirmationController confirmationController = fxmlLoader.getController();
            confirmationController.initialize(stageWindowConfirmation, var1);

            stageWindowConfirmation.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
