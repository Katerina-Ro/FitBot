package com.example.demo.dao.supportTables;

import com.example.demo.dao.Pass;
import com.example.demo.ui.ActionsWithPassController;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

public class PassSupport extends Pass {
    private Button getPassButton;
    private Button deletePassButton;
    private ActionsWithPassController actionsWithPassController;
    private final Image image;

    public PassSupport() {
        super();
        image = new Image("file:src/main/java/com/example/demo/assets/iconic.png");
        actionsWithPassController = new ActionsWithPassController();
        getPassButton = new Button("Посмотреть");
        getPassButton.setOnAction(event -> actionsWithPassController.openWindowGetInfoPass(
                image, this.getPhoneNumber()));
        deletePassButton = new Button("Удалить");
        deletePassButton.setOnAction(event -> actionsWithPassController.openWindowDeletePass(
                image, this.getPhoneNumber()));
    }
}
