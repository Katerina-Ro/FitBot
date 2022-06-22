package com.example.demo.ui;

import com.example.demo.dao.Visitors;
import com.example.demo.util.FillingFieldsHelper;
import com.example.demo.util.GetCommonWindowHelper;
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

public class ChangeStudentController {
    private final FillingFieldsHelper fillingFieldsHelper;
    private ObservableList<Visitors> visitorsObservableList;

    @FXML
    private Button backButton;

    @FXML
    private Button changeButton;

    @FXML
    private Label firstNameValueDB;
    private final StringProperty firstNameValueDBProperty = new SimpleStringProperty("");

    @FXML
    private TextField inputPhoneNumber;
    private final StringProperty phoneNumberProperty = new SimpleStringProperty("");

    @FXML
    private Label nameValueDB;
    private final StringProperty nameValueDBProperty = new SimpleStringProperty("");

    @FXML
    private TextField newFirstNameValue;

    @FXML
    private TextField newNameValue;

    @FXML
    private TextField newPatronymicValue;

    @FXML
    private TextField newPhoneNumberValue;

    @FXML
    private Label patronymicValueDB;
    private final StringProperty patronymicValueDBProperty = new SimpleStringProperty("");

    @FXML
    private Label phoneNumberValueDB;
    private final StringProperty phoneNumberValueDBProperty = new SimpleStringProperty("");

    public ChangeStudentController() {
        this.fillingFieldsHelper = new FillingFieldsHelper();
    }

    @FXML
    void initialize(Stage stageChangeStudent, Image image, TextField phoneNumber) {
        FillingFieldsHelper.correctInputPhoneLine(newPhoneNumberValue);
        FillingFieldsHelper.correctInputStringLine(newFirstNameValue);
        FillingFieldsHelper.correctInputStringLine(newNameValue);
        FillingFieldsHelper.correctInputStringLine(newPatronymicValue);
        observeInputPhoneNumber();
        backButton.setOnAction(event -> stageChangeStudent.close());
    }

    private void observeInputPhoneNumber() {
        changeButton.setDisable(true);
        FillingFieldsHelper.correctInputPhoneLine(inputPhoneNumber);
        inputPhoneNumber.textProperty().bindBidirectional(phoneNumberProperty, new DefaultStringConverter());
        phoneNumberProperty.addListener((observable, oldValue, newValue) -> {
            if (phoneNumberProperty.length().get() == 11) {
                if (FillingFieldsHelper.isPhoneNumber(inputPhoneNumber.getText())) {
                    visitorsObservableList = fillingFieldsHelper.getVisitorsObservableList(phoneNumberProperty);
                    if (!visitorsObservableList.isEmpty()) {
                        fillGetInfoStudent();
                    } else {
                        fillGetInfoStudentIfEmpty();
                    }
                }
            } else {
                fillGetInfoStudentIfEmpty();
            }
        });
    }

    private void fillGetInfoStudent() {
        for (Visitors v : visitorsObservableList) {
            // Заполняем поле Номер телефона
            phoneNumberValueDBProperty.setValue(v.getTelephoneNum());
            phoneNumberValueDBProperty.addListener((observable61, oldValue61, newValue61) -> {
                phoneNumberValueDBProperty.setValue(newValue61);
            });
            phoneNumberValueDB.textProperty().bindBidirectional(phoneNumberValueDBProperty, new DefaultStringConverter());

            // Заполняем поле Фамилия
            firstNameValueDBProperty.setValue(v.getSurname());
            firstNameValueDBProperty.addListener((observable1, oldValue1, newValue1) -> {
                firstNameValueDBProperty.setValue(newValue1);
            });
            firstNameValueDB.textProperty().bindBidirectional(firstNameValueDBProperty, new DefaultStringConverter());

            // Заполняем поле Имя
            nameValueDBProperty.setValue(v.getName());
            nameValueDBProperty.addListener((observable2, oldValue2, newValue2) -> {
                nameValueDBProperty.setValue(newValue2);
            });
            nameValueDB.textProperty().bindBidirectional(nameValueDBProperty, new DefaultStringConverter());

            // Заполняем поле Отчество
            patronymicValueDBProperty.setValue(v.getPatronymic());
            patronymicValueDBProperty.addListener((observable3, oldValue3, newValue3) -> {
                patronymicValueDBProperty.setValue(newValue3);
            });
            patronymicValueDB.textProperty().bindBidirectional(patronymicValueDBProperty, new DefaultStringConverter());
        }
    }

    private void fillGetInfoStudentIfEmpty() {
        // Заполняем поле Номер телефона
        phoneNumberValueDBProperty.setValue(null);
        phoneNumberValueDBProperty.addListener((observable61, oldValue61, newValue61) -> {
            phoneNumberValueDBProperty.setValue(newValue61);
        });
        phoneNumberValueDB.textProperty().bindBidirectional(phoneNumberValueDBProperty, new DefaultStringConverter());

        // Заполняем поле Фамилия
        firstNameValueDBProperty.setValue(null);
        firstNameValueDBProperty.addListener((observable1, oldValue1, newValue1) -> {
            firstNameValueDBProperty.setValue(newValue1);
        });
        firstNameValueDB.textProperty().bindBidirectional(firstNameValueDBProperty, new DefaultStringConverter());

        // Заполняем поле Имя
        nameValueDBProperty.setValue(null);
        nameValueDBProperty.addListener((observable2, oldValue2, newValue2) -> {
            nameValueDBProperty.setValue(newValue2);
        });
        nameValueDB.textProperty().bindBidirectional(nameValueDBProperty, new DefaultStringConverter());

        // Заполняем поле Отчество
        patronymicValueDBProperty.setValue(null);
        patronymicValueDBProperty.addListener((observable3, oldValue3, newValue3) -> {
            patronymicValueDBProperty.setValue(newValue3);
        });
        patronymicValueDB.textProperty().bindBidirectional(patronymicValueDBProperty, new DefaultStringConverter());
    }

    private void fillingNewValueStudent(Image image) {
        newNameValue.textProperty().addListener((observable1, oldValue1, newValue1) -> {
            String newNameValueArray = newNameValue.getText();
            if (newNameValueArray.length() > 0) {
                if (!newNameValueArray.isBlank()) {
                    changeButton.setDisable(false);
                    changeButton.setOnAction(event -> updateStudentInDB(image));
                }
            } else {
                changeButton.setDisable(true);
            }
        });
    }

    @FXML
    public void updateStudentInDB(Image image) {
        String phoneNumberForDB = newPhoneNumberValue.getText();
        String firstNameForDB = newFirstNameValue.getText();
        String nameForDB = newNameValue.getText();
        String patronymicForDB = newPatronymicValue.getText();

        Visitors visitor = new Visitors();
        visitor.setTelephoneNum(phoneNumberForDB);
        visitor.setName(nameForDB);
        visitor.setSurname(firstNameForDB);
        visitor.setPatronymic(patronymicForDB);
        boolean isSuccess = visitorsRepository.create(visitor);
        if (isSuccess) {
            String message = "Карточка студента успешно обновлена в базе данных";
            new GetCommonWindowHelper().openWindowSuccess(image, message);
        } else {
            //new GetCommonWindowHelper().openWindowUnSuccess(image);
        }
    }
}
