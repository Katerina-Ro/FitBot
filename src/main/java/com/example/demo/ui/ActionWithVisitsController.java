package com.example.demo.ui;

import com.example.demo.util.GetCommonWindowHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class ActionWithVisitsController {
    @FXML
    private Button backToMainMenuButton;
    @FXML
    private Button changeVisitButton;
    @FXML
    private Button createVisitButton;
    @FXML
    private Button deleteVisitButton;
    @FXML
    private Button getInfoAllVisitsButton;

    @FXML
    public void initialize(Stage stageActionWithVisits, Image image) {
        changeVisitButton.setOnAction(event -> openWindowChangeVisit(image));
        createVisitButton.setOnAction(event -> openWindowCreateVisit(image));
        deleteVisitButton.setOnAction(event -> openWindowDeleteVisit(image));
        getInfoAllVisitsButton.setOnAction(event -> openWindowGetInfoAllVisits(image));
        backToMainMenuButton.setOnAction(event -> stageActionWithVisits.close());
    }

    @FXML
    public void openWindowChangeVisit(Image image) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/changeVisits-view.fxml"));
        Parent root1;
        try {
            root1 = fxmlLoader.load();
            Stage stageChangeVisit = new Stage();
            stageChangeVisit.setResizable(false);
            stageChangeVisit.getIcons().add(image);
            stageChangeVisit.setTitle("Внесение изменений в посещение студента");
            stageChangeVisit.setScene(new Scene(root1, 700, 438));

            ChangeVisitsController changeVisitsController = fxmlLoader.getController();
            changeVisitsController.initialize(stageChangeVisit, image);

            stageChangeVisit.show();
        } catch (IOException e) {
            String message = "Произошла ошибка во время открытия окна. Обратитесь к разработчику";
            new GetCommonWindowHelper().openWindowUnSuccess(image, event -> openWindowChangeVisit(image), message);
        }
    }

    @FXML
    public void openWindowCreateVisit(Image image) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/createVisit-view.fxml"));
        Parent root1;
        try {
            root1 = fxmlLoader.load();
            Stage stageCreateVisit = new Stage();
            stageCreateVisit.setResizable(false);
            stageCreateVisit.getIcons().add(image);
            stageCreateVisit.setTitle("Внесение посещения студента в базу данных");
            stageCreateVisit.setScene(new Scene(root1, 474, 300));

            CreateVisitController createVisitController = fxmlLoader.getController();
            createVisitController.initialize(stageCreateVisit, image);

            stageCreateVisit.show();
        } catch (IOException e) {
            String message = "Произошла ошибка во время открытия окна. Обратитесь к разработчику";
            new GetCommonWindowHelper().openWindowUnSuccess(image, event -> openWindowCreateVisit(image), message);
        }
    }

    @FXML
    public void openWindowDeleteVisit(Image image) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/deleteVisit-view.fxml"));
        Parent root1;
        try {
            root1 = fxmlLoader.load();
            Stage stageDeleteVisit = new Stage();
            stageDeleteVisit.setResizable(false);
            stageDeleteVisit.getIcons().add(image);
            stageDeleteVisit.setTitle("Удаление посещения студента из базы данных");
            stageDeleteVisit.setScene(new Scene(root1, 504, 315));

            DeleteVisitController deleteVisitController = fxmlLoader.getController();
            deleteVisitController.initialize(stageDeleteVisit, image);

            stageDeleteVisit.show();
        } catch (IOException e) {
            String message = "Произошла ошибка во время открытия окна. Обратитесь к разработчику";
            new GetCommonWindowHelper().openWindowUnSuccess(image, event -> openWindowDeleteVisit(image), message);
        }
    }

    @FXML
    public void openWindowGetInfoAllVisits(Image image) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/getVisits-view.fxml"));
        Parent root1;
        try {
            root1 = fxmlLoader.load();
            Stage stageDeleteVisit = new Stage();
            stageDeleteVisit.setResizable(false);
            stageDeleteVisit.getIcons().add(image);
            stageDeleteVisit.setTitle("Информация о посещениях студента по абонементу");
            stageDeleteVisit.setScene(new Scene(root1, 700, 469));

            GetVisitsController getVisitsController = fxmlLoader.getController();
            getVisitsController.initialize(stageDeleteVisit, image);

            stageDeleteVisit.show();
        } catch (IOException e) {
            String message = "Произошла ошибка во время открытия окна. Обратитесь к разработчику";
            new GetCommonWindowHelper().openWindowUnSuccess(image, event -> openWindowGetInfoAllVisits(image), message);
        }
    }
}
