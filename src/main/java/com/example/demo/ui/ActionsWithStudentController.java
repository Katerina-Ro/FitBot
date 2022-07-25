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

public class ActionsWithStudentController {
    @FXML
    private Button backToMainMenuButton;
    @FXML
    private Button changeStudentButton;
    @FXML
    private Button createStudentButton;
    @FXML
    private Button deleteStudentButton;
    @FXML
    private Button getInfoStudentButton;

    @FXML
    void initialize(Stage stageActionsWithStudent, Image image) {
        changeStudentButton.setOnAction(event -> openWindowChangeStudent(image, null));
        createStudentButton.setOnAction(event -> openWindowCreateStudent(image));
        getInfoStudentButton.setOnAction(event -> openWindowGetInfoStudent(image, null));
        deleteStudentButton.setOnAction(event -> openWindowDeleteStudent(image));
        backToMainMenuButton.setOnAction(event -> stageActionsWithStudent.close());
    }

    @FXML
    public void openWindowDeleteStudent(Image image) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/deleteStudent-view.fxml"));
        Parent root1;
        try {
            root1 = fxmlLoader.load();
            Stage stageDeleteStudent = new Stage();
            stageDeleteStudent.setResizable(false);
            stageDeleteStudent.getIcons().add(image);
            stageDeleteStudent.setTitle("Удаление карточки студента из базы данных");
            stageDeleteStudent.setScene(new Scene(root1, 613, 458));

            DeleteStudentController deleteStudentController = fxmlLoader.getController();
            deleteStudentController.initialize(stageDeleteStudent, image);

            stageDeleteStudent.show();
        } catch (IOException e) {
            String message = "Произошла ошибка во время открытия окна. Обратитесь к разработчику";
            new GetCommonWindowHelper().openWindowUnSuccess(image, event -> openWindowDeleteStudent(image), message);
        }
    }

    @FXML
    public void openWindowGetInfoStudent(Image image, String phoneNumber) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/getInfoStudent-view.fxml"));
        Parent root1;
        try {
            root1 = fxmlLoader.load();
            Stage stageGetInfoStudent = new Stage();
            stageGetInfoStudent.setResizable(false);
            stageGetInfoStudent.getIcons().add(image);
            stageGetInfoStudent.setTitle("Информация о студенте");
            stageGetInfoStudent.setScene(new Scene(root1, 700, 500));

            GetInfoStudentController getInfoStudentController = fxmlLoader.getController();
            getInfoStudentController.initialize(stageGetInfoStudent, image, phoneNumber);

            stageGetInfoStudent.show();
        } catch (IOException e) {
            String message = "Произошла ошибка во время открытия окна. Обратитесь к разработчику";
            new GetCommonWindowHelper().openWindowUnSuccess(image, event -> openWindowGetInfoStudent(image, phoneNumber), message);
        }
    }

    @FXML
    public void openWindowCreateStudent(Image image) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/createStudent-view.fxml"));
        Parent root1;
        try {
            root1 = fxmlLoader.load();
            Stage stageCreateStudent = new Stage();
            stageCreateStudent.setResizable(false);
            stageCreateStudent.getIcons().add(image);
            stageCreateStudent.setTitle("Внесение карточки студента в базу данных");
            stageCreateStudent.setScene(new Scene(root1, 700, 373));

            CreateStudentController createStudentController = fxmlLoader.getController();
            createStudentController.initialize(stageCreateStudent, image);

            stageCreateStudent.show();
        } catch (IOException e) {
            String message = "Произошла ошибка во время открытия окна. Обратитесь к разработчику";
            new GetCommonWindowHelper().openWindowUnSuccess(image, event -> openWindowCreateStudent(image), message);
        }
    }

    @FXML
    public void openWindowChangeStudent(Image image, String phoneNumber) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/changeStudent-view.fxml"));
        Parent root1;
        try {
            root1 = fxmlLoader.load();
            Stage stageChangeStudent = new Stage();
            stageChangeStudent.setResizable(false);
            stageChangeStudent.getIcons().add(image);
            stageChangeStudent.setTitle("Внесение изменений в карточку студента");
            stageChangeStudent.setScene(new Scene(root1, 700, 438));

            ChangeStudentController changeStudentController = fxmlLoader.getController();
            changeStudentController.initialize(stageChangeStudent, image, phoneNumber);

            stageChangeStudent.show();
        } catch (IOException e) {
            String message = "Произошла ошибка во время открытия окна. Обратитесь к разработчику";
            new GetCommonWindowHelper().openWindowUnSuccess(image, event -> openWindowChangeStudent(image, phoneNumber), message);
        }
    }
}
