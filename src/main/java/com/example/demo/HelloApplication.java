package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("studentAttendance-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 450);
        stage.setResizable(false);
        stage.getIcons().add(new Image("file:src/main/java/com/example/demo/assets/iconic.png"));
        stage.setTitle("Учет посещаемости студентов");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}