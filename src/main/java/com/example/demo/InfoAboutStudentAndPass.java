package com.example.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class InfoAboutStudentAndPass extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("inputPhoneNumber-view.fxml"));
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 664, 804);
        stage.setTitle ("Информация о студенте/ абонементе");
        stage.setScene(scene);
        stage.show();
    }
}
