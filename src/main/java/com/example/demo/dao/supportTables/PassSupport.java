package com.example.demo.dao.supportTables;

import com.example.demo.dao.Pass;
import com.example.demo.ui.ActionsWithPassController;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PassSupport extends Pass {
    private Button getPassButton;
    private Button unFreezeButton;
    private ActionsWithPassController actionsWithPassController;
    private final Image image;

    public PassSupport(String phoneNumber) {
        image = new Image("file:src/main/java/com/example/demo/assets/iconic.png");
        actionsWithPassController = new ActionsWithPassController();
        getPassButton = new Button("Посмотреть");
        getPassButton.setOnAction(event -> actionsWithPassController.openWindowGetInfoPass(
                image, phoneNumber));
        unFreezeButton = new Button("Разморозить");
        unFreezeButton.setOnAction(event -> actionsWithPassController.openWindowUnFreezePass2(
                image, phoneNumber));
    }
}
