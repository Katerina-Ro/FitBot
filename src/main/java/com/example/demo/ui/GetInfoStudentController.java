package com.example.demo.ui;

import com.example.demo.dao.Pass;
import com.example.demo.dao.Visitors;
import com.example.demo.util.FillingFieldsHelper;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.LocalDateStringConverter;

import java.time.LocalDate;

public class GetInfoStudentController {
    private final ActionsWithStudentController actionsWithStudentController;
    private final ActionsWithPassController actionsWithPassController;
    private final FillingFieldsHelper fillingFieldsHelper;
    private ObservableList<Visitors> visitorsObservableList;
    private ObservableList<Pass> passObservableList;

    @FXML
    private Button backButton;
    @FXML
    private Button changeStudentButton;
    @FXML
    private Button getInfoPassButton;
    @FXML
    private Label dateStartFreezeValue;
    private final ObjectProperty<LocalDate> dateStartFreezeValueProperty = new SimpleObjectProperty<>();
    @FXML
    private Label existActualPassDB;
    private final StringProperty existActualPassDBProperty = new SimpleStringProperty("");
    @FXML
    private Label firstNameDB;
    private final StringProperty firstNameDBProperty = new SimpleStringProperty("");
    @FXML
    private TextField inputPhoneNumber;
    private final StringProperty phoneNumberProperty = new SimpleStringProperty("");
    @FXML
    private Label nameDB;
    private final StringProperty nameDBProperty = new SimpleStringProperty("");
    @FXML
    private Label passIsFreezeDB;
    private final StringProperty passIsFreezeDBProperty = new SimpleStringProperty("");
    @FXML
    private Label patronymicDB;
    private final StringProperty patronymicDBProperty = new SimpleStringProperty("");

    public GetInfoStudentController() {
        this.actionsWithPassController = new ActionsWithPassController();
        this.actionsWithStudentController = new ActionsWithStudentController();
        this.fillingFieldsHelper = new FillingFieldsHelper();
    }

    @FXML
    void initialize(Stage stageGetInfoStudent, Image image, TextField phoneNumber) {
        observeInputPhoneNumber();
        backButton.setOnAction(event -> stageGetInfoStudent.close());
        changeStudentButton.setOnAction(event -> actionsWithStudentController.openWindowChangeStudent(image, phoneNumber));
        getInfoPassButton.setOnAction(event -> actionsWithPassController.openWindowGetInfoPass(image, phoneNumber));
    }

    private void observeInputPhoneNumber() {
        FillingFieldsHelper.correctInputPhoneLine(inputPhoneNumber);
        inputPhoneNumber.textProperty().bindBidirectional(phoneNumberProperty, new DefaultStringConverter());
        phoneNumberProperty.addListener((observable, oldValue, newValue) -> {
            if (phoneNumberProperty.length().get() == 11) {
                visitorsObservableList = fillingFieldsHelper.getVisitorsObservableList(phoneNumberProperty);
                passObservableList = fillingFieldsHelper.getTablePass(phoneNumberProperty);
                if (!visitorsObservableList.isEmpty()) {
                    fillGetInfoStudent();
                } else {
                    fillGetInfoStudentIfEmpty();
                }
            } else {
                fillGetInfoStudentIfEmpty();
            }
        });
    }

    private void fillGetInfoStudent() {
        for (Visitors v : visitorsObservableList) {
            // Заполняем поле Фамилия
            firstNameDBProperty.setValue(v.getSurname());
            firstNameDBProperty.addListener((observable1, oldValue1, newValue1) -> {
                firstNameDBProperty.setValue(newValue1);
            });
            firstNameDB.textProperty().bindBidirectional(firstNameDBProperty, new DefaultStringConverter());

            // Заполняем поле Имя
            nameDBProperty.setValue(v.getName());
            nameDBProperty.addListener((observable2, oldValue2, newValue2) -> {
                nameDBProperty.setValue(newValue2);
            });
            nameDB.textProperty().bindBidirectional(nameDBProperty, new DefaultStringConverter());

            // Заполняем поле Отчество
            patronymicDBProperty.setValue(v.getPatronymic());
            patronymicDBProperty.addListener((observable3, oldValue3, newValue3) -> {
                patronymicDBProperty.setValue(newValue3);
            });
            patronymicDB.textProperty().bindBidirectional(patronymicDBProperty, new DefaultStringConverter());
        }
        if (passObservableList != null && !passObservableList.isEmpty()) {
            fillGetInfoPassForStudent();
        } else {
            fillGetInfoPassForStudentIfEmpty(nameDBProperty);
        }
    }

    private void fillGetInfoPassForStudent() {
        for (Pass p: passObservableList) {
            LocalDate dateStartFreeze = p.getDateStartFreeze();
            // Заполняем поле Есть действующий абонемент?
            existActualPassDBProperty.setValue("Есть");
            existActualPassDBProperty.addListener((observable4, oldValue4, newValue4) -> {
                existActualPassDBProperty.setValue(newValue4);
            });
            existActualPassDB.textProperty().bindBidirectional(existActualPassDBProperty, new DefaultStringConverter());

            // Заполняем поле Абонемент заморожен?
            passIsFreezeDBProperty.setValue(dateStartFreeze != null ? "Да" : "Нет");
            passIsFreezeDBProperty.addListener((observable5, oldValue5, newValue5) -> {
                passIsFreezeDBProperty.setValue(newValue5);
            });
            passIsFreezeDB.textProperty().bindBidirectional(passIsFreezeDBProperty, new DefaultStringConverter());

            // Заполняем поле Дата начала заморозки
            dateStartFreezeValueProperty.setValue(dateStartFreeze);
            dateStartFreezeValueProperty.addListener((observable6, oldValue6, newValue6) -> {
                dateStartFreezeValueProperty.setValue(newValue6);
            });
            dateStartFreezeValue.textProperty().bindBidirectional(dateStartFreezeValueProperty, new LocalDateStringConverter());
        }
    }

    private void fillGetInfoStudentIfEmpty() {
        // Заполняем поле Фамилия
        firstNameDBProperty.setValue(null);
        firstNameDBProperty.addListener((observable1, oldValue1, newValue1) -> {
            firstNameDBProperty.setValue(newValue1);
        });
        firstNameDB.textProperty().bindBidirectional(firstNameDBProperty, new DefaultStringConverter());

        // Заполняем поле Имя
        nameDBProperty.setValue(null);
        nameDBProperty.addListener((observable2, oldValue2, newValue2) -> {
            nameDBProperty.setValue(newValue2);
        });
        nameDB.textProperty().bindBidirectional(nameDBProperty, new DefaultStringConverter());

        // Заполняем поле Отчество
        patronymicDBProperty.setValue(null);
        patronymicDBProperty.addListener((observable3, oldValue3, newValue3) -> {
            patronymicDBProperty.setValue(newValue3);
        });
        patronymicDB.textProperty().bindBidirectional(patronymicDBProperty, new DefaultStringConverter());

        fillGetInfoPassForStudentIfEmpty(nameDBProperty);
    }

    private void fillGetInfoPassForStudentIfEmpty(StringProperty nameDBProperty) {
        // Заполняем поле Есть действующий абонмент?
        existActualPassDBProperty.setValue(nameDBProperty.get() != null && !nameDBProperty.get().isBlank() ? "Нет" : null);
        existActualPassDBProperty.addListener((observable4, oldValue4, newValue4) -> {
            existActualPassDBProperty.setValue(newValue4);
        });
        existActualPassDB.textProperty().bindBidirectional(existActualPassDBProperty, new DefaultStringConverter());

        // Заполняем поле Абонемент заморожен?
        passIsFreezeDBProperty.setValue(null);
        passIsFreezeDBProperty.addListener((observable5, oldValue5, newValue5) -> {
            passIsFreezeDBProperty.setValue(newValue5);
        });
        passIsFreezeDB.textProperty().bindBidirectional(passIsFreezeDBProperty, new DefaultStringConverter());

        // Заполняем поле Дата начала заморозки
        dateStartFreezeValueProperty.setValue(null);
        dateStartFreezeValueProperty.addListener((observable6, oldValue6, newValue6) -> {
            dateStartFreezeValueProperty.setValue(newValue6);
        });
        dateStartFreezeValue.textProperty().bindBidirectional(dateStartFreezeValueProperty, new LocalDateStringConverter());
    }
}
