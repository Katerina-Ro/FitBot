package com.example.demo.dao.supportTables;

import com.example.demo.dao.Pass;
import com.example.demo.ui.ActionsWithPassController;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

public class PassSupport extends Pass {
    private Button getInfoPassButton;
    private Button deleteVisitButton;
    private ActionsWithPassController actionsWithPassController;
    private Image image;

    public PassSupport() {
        super();
        image = new Image("file:src/main/java/com/example/demo/assets/iconic.png");
        getInfoPassButton = new Button("Посмотреть");
        getInfoPassButton.setOnAction(event -> actionsWithPassController.openWindowGetInfoPass(
                image, this.getPhoneNumber());
        deleteVisitButton = new Button("Удалить");
        deleteVisitButton.setOnAction(event -> actionsWithPassController.openWindowDeleteVisit(
                image, this.getPass(), this.getDateVisit(), this.getCountVisit(), this.inputPhoneNumber));
    }
}
