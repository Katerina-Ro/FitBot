package com.example.demo.dao.supportTables;

import com.example.demo.dao.Pass;
import com.example.demo.ui.ActionsWithPassController;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

public class PassSupport extends Pass {
    private final ActionsWithPassController actionsWithPassController;
    private final Image image;

    public PassSupport(String phoneNumber) {
        image = new Image("file:src/main/java/com/example/demo/assets/iconic.png");
        actionsWithPassController = new ActionsWithPassController();
        Button getPassButton = new Button("Посмотреть");
        getPassButton.setOnAction(event -> actionsWithPassController.openWindowGetInfoPass(
                image, phoneNumber));
        Button deletePassButton = new Button("Удалить");
        deletePassButton.setOnAction(event -> actionsWithPassController.openWindowDeletePass(
                image, phoneNumber));
    }
}
