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

public class StartController {
    @FXML
    private Button actionWithPassButton;
    @FXML
    private Button actionWithStudentButton;
    @FXML
    private Button actionWithVisitsStudentButton;
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
    public void initialize(Stage primaryStage, Image image) {
        whoComeToDayButton.setOnAction(event -> openWindowWhoComeToDay(image));
        whoDontComeToDayButton.setOnAction(event -> openWindowWhoDontComeToDay(image));
        //whoNothingSayButton.setOnAction(event -> openWindowWhoNothingSay(image));
        actionWithPassButton.setOnAction(event -> openWindowActionWithPass(image));
        actionWithStudentButton.setOnAction(event -> openWindowActionWithStudent(image));
        actionWithVisitsStudentButton.setOnAction(event -> openWindowActionWithVisits(image));
        closeApplicationButton.setOnAction(event -> primaryStage.close());
        settingsBotButton.setOnAction(actionEvent -> openWindowSettingsBot(image));
    }

    @FXML
    public void openWindowActionWithStudent(Image image) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/actionsWithStudent-view.fxml"));
        Parent root1;
        try {
            root1 = fxmlLoader.load();
            Stage stageActionWithStudent = new Stage();
            stageActionWithStudent.setResizable(false);
            stageActionWithStudent.getIcons().add(image);
            stageActionWithStudent.setTitle("Действия с карточкой студента");
            stageActionWithStudent.setScene(new Scene(root1, 672, 375));

            ActionsWithStudentController actionsWithStudentController = fxmlLoader.getController();
            actionsWithStudentController.initialize(stageActionWithStudent, image);

            stageActionWithStudent.show();
        } catch (IOException e) {
            String message = "Произошла ошибка во время открытия окна. Обратитесь к разработчику";
            new GetCommonWindowHelper().openWindowUnSuccess(image, event -> openWindowActionWithStudent(image), message);
        }
    }

    public void openWindowActionWithVisits(Image image) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/actionsWithVisits-view.fxml"));
        Parent root1;
        try {
            root1 = fxmlLoader.load();
            Stage stageActionWithVisits = new Stage();
            stageActionWithVisits.setResizable(false);
            stageActionWithVisits.getIcons().add(image);
            stageActionWithVisits.setTitle("Посещения студента");
            stageActionWithVisits.setScene(new Scene(root1, 516, 406));

            ActionWithVisitsController actionWithVisitsController = fxmlLoader.getController();
            actionWithVisitsController.initialize(stageActionWithVisits, image);

            stageActionWithVisits.show();
        } catch (IOException e) {
            String message = "Произошла ошибка во время открытия окна. Обратитесь к разработчику";
            new GetCommonWindowHelper().openWindowUnSuccess(image, event -> openWindowActionWithVisits(image), message);
        }
    }

    @FXML
    public void openWindowActionWithPass(Image image) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/actionsWithPass-view.fxml"));
        Parent root1;
        try {
            root1 = fxmlLoader.load();
            Stage stageActionWithPass = new Stage();
            stageActionWithPass.setResizable(false);
            stageActionWithPass.getIcons().add(image);
            stageActionWithPass.setTitle("Действия с абонементом");
            stageActionWithPass.setScene(new Scene(root1, 672, 413));

            ActionsWithPassController actionsWithPassController = fxmlLoader.getController();
            actionsWithPassController.initialize(stageActionWithPass, image);

            stageActionWithPass.show();
        } catch (IOException e) {
            String message = "Произошла ошибка во время открытия окна. Обратитесь к разработчику";
            new GetCommonWindowHelper().openWindowUnSuccess(image, event -> openWindowActionWithPass(image), message);
        }
    }

    @FXML
    public void openWindowWhoNothingSay(Image image) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/whoNothingSays-view.fxml"));
        Parent root1;
        try {
            root1 = fxmlLoader.load();
            Stage stageWhoNothingSay = new Stage();
            stageWhoNothingSay.setResizable(false);
            stageWhoNothingSay.getIcons().add(image);
            stageWhoNothingSay.setTitle("Кто ничего не ответил");
            stageWhoNothingSay.setScene(new Scene(root1, 700, 480));

            WhoNothingSaysController whoNothingSaysController = fxmlLoader.getController();
            whoNothingSaysController.initialize(stageWhoNothingSay, image);

            stageWhoNothingSay.show();
        } catch (IOException e) {
            String message = "Произошла ошибка во время открытия окна. Обратитесь к разработчику";
            new GetCommonWindowHelper().openWindowUnSuccess(image, event -> openWindowWhoNothingSay(image), message);
        }
    }

    @FXML
    public void  openWindowWhoDontComeToDay(Image image) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/whoSaysNoToday-view.fxml"));
        Parent root1;
        try {
            root1 = fxmlLoader.load();
            Stage stageWhoDontComeToDay = new Stage();
            stageWhoDontComeToDay.setResizable(false);
            stageWhoDontComeToDay.getIcons().add(image);
            stageWhoDontComeToDay.setTitle("Кто сегодня не придет");
            stageWhoDontComeToDay.setScene(new Scene(root1, 700, 480));

            WhoSaysNoTodayController whoSaysNoTodayController = fxmlLoader.getController();
            whoSaysNoTodayController.initialize(stageWhoDontComeToDay);

            stageWhoDontComeToDay.show();
        } catch (IOException e) {
            String message = "Произошла ошибка во время открытия окна. Обратитесь к разработчику";
            new GetCommonWindowHelper().openWindowUnSuccess(image, event -> openWindowWhoDontComeToDay(image), message);
        }
    }

    @FXML
    public void openWindowWhoComeToDay(Image image) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/whoComeToday-view.fxml"));
        Parent root1;
        try {
            root1 = fxmlLoader.load();
            Stage stageWhoComeToDay = new Stage();
            stageWhoComeToDay.setResizable(false);
            stageWhoComeToDay.getIcons().add(image);
            stageWhoComeToDay.setTitle("Кто сегодня придет");
            stageWhoComeToDay.setScene(new Scene(root1, 700, 480));

            WhoComeTodayController whoComeTodayController = fxmlLoader.getController();
            whoComeTodayController.initialize(stageWhoComeToDay, image);

            stageWhoComeToDay.show();
        } catch (IOException e) {
            String message = "Произошла ошибка во время открытия окна. Обратитесь к разработчику";
            new GetCommonWindowHelper().openWindowUnSuccess(image, event -> openWindowWhoComeToDay(image), message);
        }
    }

    @FXML
    public void openWindowSettingsBot(Image image) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/settingsBot-view.fxml"));
        Parent root1;
        try {
            root1 = fxmlLoader.load();
            Stage stageSettingsBot = new Stage();
            stageSettingsBot.setResizable(false);
            stageSettingsBot.getIcons().add(image);
            stageSettingsBot.setTitle("Настройки бота");
            stageSettingsBot.setScene(new Scene(root1, 479, 253));

            SettingsBotController settingsBotController = fxmlLoader.getController();
            settingsBotController.initialize(stageSettingsBot, image);

            stageSettingsBot.show();
        } catch (IOException e) {
            String message = "Произошла ошибка во время открытия окна. Обратитесь к разработчику";
            new GetCommonWindowHelper().openWindowUnSuccess(image, event -> openWindowSettingsBot(image), message);
        }
    }
}