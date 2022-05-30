package com.example.demo;

import com.example.demo.ui.StartController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("start-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 672, 453);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("file:src/main/java/com/example/demo/assets/iconic.png"));
        primaryStage.setTitle("Учет посещаемости студентов");
        primaryStage.setScene(scene);

        StartController startController = fxmlLoader.getController();
        startController.initialize(primaryStage);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}