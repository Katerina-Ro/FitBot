package com.example.demo.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class StartController {

    //@FXML
    //private ResourceBundle resources;

    //@FXML
    //private URL location;

    private InputPhoneNumberController children;

    @FXML
    private Button changeInfoAboutStudent;

    @FXML
    private Button getInfoAboutStudent;

    @FXML
    private Button turnOffBot;

    @FXML
    private Button turnOnBot;

    @FXML
    private Button whoComeToDay;

    @FXML
    private Button whoDontComeToDay;

    @FXML
    public void initialize() {
      whoComeToDay.setOnAction(event ->
              System.out.println("оралоплдов"));
      getInfoAboutStudent.setOnAction(event -> openWindowGetInfoAboutStudent());
    }

    @FXML
    public void openWindowGetInfoAboutStudent() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource("inputPhoneNumber-view.fxml");
        fxmlLoader.setLocation(url);
        Parent root1;
        try {
            root1 = fxmlLoader.load();
            Stage stage1 = new Stage();
            stage1.setScene(new Scene(root1, 700, 450));
            stage1.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

    /*
    @FXML
    private Button getInfoAboutPass;

    @FXML
    private Button getInfoAboutStudent;

    @FXML
    private Button whoComeToDay;

    @FXML
    void onGetInfoAboutPassButtonClick(ActionEvent event) {

    }

    @FXML
    void onGetInfoAboutStudentButtonClick(ActionEvent event) {

    }

    @FXML
    void onWhoIsComingToDayButtonClick(ActionEvent event) {
    }

    /*
    @PostConstruct
    public void init() {
        Optional<List<Pass>> contacts = passRepository.findPassByPhone("79502078309");
        contacts.ifPresent(passes -> data = FXCollections.observableArrayList(passes));

        // Столбцы таблицы
        TableColumn<Pass, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("numPass"));

        TableColumn<Pass, String> nameColumn = new TableColumn<>("Имя");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("dateStart"));

        TableColumn<Pass, String> phoneColumn = new TableColumn<>("Телефон");
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        table.getColumns().setAll(idColumn, nameColumn, phoneColumn);

        // Данные таблицы
        table.setItems(data);
    }

    @FXML
    protected void onWhoIsComingToDayButtonClick() {
        Label secondLabel = new Label("Отметили \"Да\"");

        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().add(secondLabel);

        Scene secondScene = new Scene(secondaryLayout, 800, 650);

        // New window (Stage)
        Stage comingToDayWindow = new Stage();
        comingToDayWindow.setTitle("Кто сегодня придет?");
        comingToDayWindow.setScene(secondScene);



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
    } */
