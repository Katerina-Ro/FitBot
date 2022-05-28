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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/inputPhoneNumber-view.fxml"));
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
        }
    }
}