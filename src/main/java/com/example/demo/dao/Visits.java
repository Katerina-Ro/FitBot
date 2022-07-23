package com.example.demo.dao;

import com.example.demo.ui.ActionWithVisitsController;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Visits {

    private Integer pass;
    private LocalDate dateVisit;
    private Integer countVisit;
    private Button changeVisitButton;
    private Button deleteVisitButton;

    private ActionWithVisitsController actionWithVisitsController;

    public Visits() {
        changeVisitButton = new Button("Изменить");
        changeVisitButton.setOnAction(event -> actionWithVisitsController.openWindowChangeVisits(
                new Image("file:src/main/java/com/example/demo/assets/iconic.png"), this.pass,
                this.dateVisit, this.countVisit));
        deleteVisitButton = new Button("Удалить");
    }

    @Override
    public String toString() {
        return "Номер абонемента " + pass + '\'' +
                ", Дата посещения " + dateVisit + '\'' +
                ", Количество посещений " + countVisit;
    }
}