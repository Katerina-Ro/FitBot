package com.example.student;

import dto.PlanToComeToDay;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import telegramBot.service.entetiesService.PassService;

import java.util.List;
import java.util.Map;

public class StartController {
    private final PassService passService;

    @FXML
    Button btnOK;

    @Autowired
    public StartController(PassService passService) {
        this.passService = passService;
    }

    @FXML
    protected void onWhoIsComingToDayButtonClick() {
        Label secondLabel = new Label("Отметили \"Да\"");

        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().add(secondLabel);

        Scene secondScene = new Scene(secondaryLayout, 800, 650);

        Map<String, PlanToComeToDay> mapPlanToComeToDay = passService.getMapVisitorsToDay();
        List<PlanToComeToDay> planToComeToDay = (List<PlanToComeToDay>) mapPlanToComeToDay.values();
        ObservableList<PlanToComeToDay> studets = FXCollections.observableList(planToComeToDay);

        Label lbl = new Label();
        TableView<PlanToComeToDay> table = new TableView<>(studets);
        table.setPrefWidth(250);
        table.setPrefHeight(200);

        // столбец для вывода имени
        TableColumn<PlanToComeToDay, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        table.getColumns().add(nameColumn);

        // столбец для вывода возраста
        TableColumn<PlanToComeToDay, String> phoneColumn = new TableColumn<>("Age");
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        table.getColumns().add(phoneColumn);

        TableView.TableViewSelectionModel<PlanToComeToDay> selectionModel = table.getSelectionModel();
        selectionModel.selectedItemProperty().addListener(new ChangeListener<PlanToComeToDay>(){

            public void changed(ObservableValue<? extends PlanToComeToDay> val, PlanToComeToDay oldVal, PlanToComeToDay newVal){
                if(newVal != null) lbl.setText("Selected: " + newVal.getName());
            }
        });





        // New window (Stage)
        Stage comingToDayWindow = new Stage();
        comingToDayWindow.setTitle("Кто сегодня придет?");
        comingToDayWindow.setScene(secondScene);

        // Set position of second window, related to primary window.


    }

    @FXML
    protected void onWhoWontComeToDayButtonClick() {
        Label secondLabel = new Label("Отметили \"Нет\"");

        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().add(secondLabel);

        Scene secondScene = new Scene(secondaryLayout, 800, 650);

        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("Кто сегодня не придет?");
        newWindow.setScene(secondScene);

        // Set position of second window, related to primary window.

        newWindow.show();
    }

    @FXML
    protected void onGetInfoAboutPassButtonClick() {
        Label secondLabel = new Label("Информация об абонементе");

        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().add(secondLabel);

        Scene secondScene = new Scene(secondaryLayout, 800, 650);

        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("Получить информацию об абонементе");
        newWindow.setScene(secondScene);

        // Set position of second window, related to primary window.

        newWindow.show();
    }

    @FXML
    protected void onGetInfoAboutStudentButtonClick() {
        Label secondLabel = new Label("Информация о студенте");

        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().add(secondLabel);

        Scene secondScene = new Scene(secondaryLayout, 800, 650);

        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("Получить информацию о студенте");
        newWindow.setScene(secondScene);

        // Set position of second window, related to primary window.

        newWindow.show();
    }

    @FXML
    protected void onCloseWindowButtonClick() {
        Stage stage = (Stage) btnOK.getScene().getWindow();
        stage.close();
    }
}