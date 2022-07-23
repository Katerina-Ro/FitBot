package com.example.demo.dao.supportTables;

import com.example.demo.dao.Visits;
import com.example.demo.ui.ActionWithVisitsController;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Visit extends Visits {
    private Button changeVisitButton;
    private Button deleteVisitButton;
    private ActionWithVisitsController actionWithVisitsController;
    private Image image;
    private String inputPhoneNumber;

    public Visit() {
        super();
        image = new Image("file:src/main/java/com/example/demo/assets/iconic.png");
        actionWithVisitsController = new ActionWithVisitsController();
        changeVisitButton = new Button("Изменить");
        changeVisitButton.setOnAction(event -> actionWithVisitsController.openWindowChangeVisits(
                image, this.getPass(), this.getDateVisit(), this.getCountVisit(), this.inputPhoneNumber));
        deleteVisitButton = new Button("Удалить");
        deleteVisitButton.setOnAction(event -> actionWithVisitsController.openWindowDeleteVisit(
                image, this.getPass(), this.getDateVisit(), this.getCountVisit(), this.inputPhoneNumber));
    }
}
