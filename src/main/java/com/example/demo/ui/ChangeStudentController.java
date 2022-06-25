package com.example.demo.ui;

import com.example.demo.dao.Visitors;
import com.example.demo.observableModels.ObservableVisitor;
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

import java.util.concurrent.atomic.AtomicReference;

public class ChangeStudentController {
    private final FillingFieldsHelper fillingFieldsHelper;
    private ObservableList<Visitors> visitorsObservableList;
    private ObservableList<ObservableVisitor> observableVisitor;
    private ObservableList<String> observableNewPhoneNumber;
    private AtomicReference<String> phoneNumberForDB = new AtomicReference<>();
    private AtomicReference<String> firstNameForDB = new AtomicReference<>();
    private AtomicReference<String> nameForDB = new AtomicReference<>();
    private AtomicReference<String> patronymicForDB = new AtomicReference<>();
    private AtomicReference<String> phoneNumberForSearch = new AtomicReference<>();

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
    private final StringProperty newPhoneNumberValueProperty = new SimpleStringProperty("");

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
        observeInputPhoneNumber(image);
        backButton.setOnAction(event -> stageChangeStudent.close());
    }

    private void observeInputPhoneNumber(Image image) {
        FillingFieldsHelper.correctInputPhoneLine(newPhoneNumberValue);
        FillingFieldsHelper.correctInputStringLine(newFirstNameValue);
        FillingFieldsHelper.correctInputStringLine(newNameValue);
        FillingFieldsHelper.correctInputStringLine(newPatronymicValue);
        FillingFieldsHelper.correctInputPhoneLine(inputPhoneNumber);
        changeButton.setDisable(true);

        inputPhoneNumber.textProperty().bindBidirectional(phoneNumberProperty, new DefaultStringConverter());
        phoneNumberProperty.addListener((observable, oldValue, newValue) -> {
            if (phoneNumberProperty.length().get() == 11) {
                if (FillingFieldsHelper.isPhoneNumber(inputPhoneNumber.getText())) {
                    visitorsObservableList = fillingFieldsHelper.getVisitorsObservableList(phoneNumberProperty);
                    if (!visitorsObservableList.isEmpty()) {
                        phoneNumberForSearch.set(inputPhoneNumber.getText());
                        fillGetInfoStudent(image);
                    } else {
                        phoneNumberForSearch.set(null);
                        fillGetInfoStudentIfEmpty();
                        changeButton.setDisable(true);
                    }
                }
            } else {
                phoneNumberForSearch.set(null);
                fillGetInfoStudentIfEmpty();
                changeButton.setDisable(true);
            }
        });
    }

    private void fillGetInfoStudent(Image image) {
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

            observableNewValues(image);
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

    private void observableNewValues(Image image) {
        FillingFieldsHelper.correctInputPhoneLine(newPhoneNumberValue);
        FillingFieldsHelper.correctInputStringLine(newFirstNameValue);
        FillingFieldsHelper.correctInputStringLine(newNameValue);
        FillingFieldsHelper.correctInputStringLine(newPatronymicValue);

        observableNewPhoneNumber(image);
        observableNewPatronymicValue(image);
        observableNewNameValue(image);
        observableNewFirstNameValue(image);
    }

    private void observableNewNameValue(Image image) {
        newNameValue.textProperty().addListener((observable2, oldValue2, newValue2) -> {
            String newNameValueArray = newNameValue.getText();
            if (newNameValueArray.length() > 0) {
                if (!newNameValueArray.isBlank()) {
                    nameForDB.set(newNameValueArray);
                    changeButton.setDisable(false);
                    changeButton.setOnAction(event -> updateStudentInDB(image));
                } else {
                    nameForDB.set(null);
                    observableNewNameValue(image);
                    observableNewPhoneNumber(image);
                    observableNewFirstNameValue(image);
                    observableNewPatronymicValue(image);
                }
            } else {
                nameForDB.set(null);
                observableNewNameValue(image);
                observableNewPhoneNumber(image);
                observableNewFirstNameValue(image);
                observableNewPatronymicValue(image);
            }
        });
    }

    private void observableNewFirstNameValue(Image image) {
        newFirstNameValue.textProperty().addListener((observable3, oldValue3, newValue3) -> {
            String newFirstNameValueArray = newFirstNameValue.getText();
            if (newFirstNameValueArray.length() > 0) {
                if (!newFirstNameValueArray.isBlank()) {
                    firstNameForDB.set(newFirstNameValueArray);
                    changeButton.setDisable(false);
                    changeButton.setOnAction(event -> updateStudentInDB(image));
                } else {
                    firstNameForDB.set(null);
                    observableNewFirstNameValue(image);
                    observableNewPhoneNumber(image);
                    observableNewNameValue(image);
                    observableNewPatronymicValue(image);
                }
            } else {
                firstNameForDB.set(null);
                observableNewFirstNameValue(image);
                observableNewPhoneNumber(image);
                observableNewNameValue(image);
                observableNewPatronymicValue(image);
            }
        });
    }

    private void observableNewPatronymicValue(Image image) {
        newPatronymicValue.textProperty().addListener((observable3, oldValue3, newValue3) -> {
            String newPatronymicValueArray = newPatronymicValue.getText();
            if (newPatronymicValueArray.length() > 0) {
                if (!newPatronymicValueArray.isBlank()) {
                    patronymicForDB.set(newPatronymicValueArray);
                    changeButton.setDisable(false);
                    changeButton.setOnAction(event -> updateStudentInDB(image));
                } else {
                    patronymicForDB.set(null);
                    observableNewPatronymicValue(image);
                    observableNewPhoneNumber(image);
                    observableNewNameValue(image);
                    observableNewFirstNameValue(image);
                }
            } else {
                patronymicForDB.set(null);
                observableNewPatronymicValue(image);
                observableNewPhoneNumber(image);
                observableNewNameValue(image);
                observableNewFirstNameValue(image);
            }
        });
    }

    private void observableNewPhoneNumber(Image image) {
        newPhoneNumberValue.textProperty().addListener((observable21, oldValue21, newValue21) -> {
            if (newPhoneNumberValue.getText().length() == 11) {
                if (FillingFieldsHelper.isPhoneNumber(newPhoneNumberValue.getText())) {
                    phoneNumberForDB.set(newValue21);
                    changeButton.setDisable(false);
                    changeButton.setOnAction(event -> updateStudentInDB(image));
                } else {
                    phoneNumberForDB.set(null);
                    changeButton.setDisable(true);
                    observableNewPhoneNumber(image);
                    observableNewNameValue(image);
                    observableNewFirstNameValue(image);
                    observableNewPatronymicValue(image);
                }
            } else if (newPhoneNumberValue == null || newPhoneNumberValue.getText().length() == 0
                    || newPhoneNumberValue.getText().isBlank()) {
                phoneNumberForDB.set(null);
                observableNewPhoneNumber(image);
                observableNewNameValue(image);
                observableNewFirstNameValue(image);
                observableNewPatronymicValue(image);
            } else {
                phoneNumberForDB.set(null);
                changeButton.setDisable(true);
            }
        });
    }

    @FXML
    public void updateStudentInDB(Image image) {
        Visitors visitor = new Visitors();
        if (phoneNumberForSearch != null && FillingFieldsHelper.isPhoneNumber(String.valueOf(phoneNumberForSearch))) {
            visitor.setTelephoneNum(String.valueOf(phoneNumberForSearch));
        }
        if (phoneNumberForDB != null && FillingFieldsHelper.isPhoneNumber(String.valueOf(phoneNumberForDB))) {
            visitor.setNewPhoneNumber(String.valueOf(phoneNumberForDB));
        }
        if (nameForDB != null && FillingFieldsHelper.isLetter(String.valueOf(nameForDB))) {
            visitor.setName(String.valueOf(nameForDB));
        }
        if (firstNameForDB != null && FillingFieldsHelper.isLetter(String.valueOf(firstNameForDB))) {
            visitor.setSurname(String.valueOf(firstNameForDB));
        }
        if (patronymicForDB != null && FillingFieldsHelper.isLetter(String.valueOf(patronymicForDB))) {
            visitor.setPatronymic(String.valueOf(patronymicForDB));
        }
        boolean isSuccess = fillingFieldsHelper.updateVisitors(visitor);
        if (isSuccess) {
            String message = "Карточка студента успешно обновлена в базе данных";
            new GetCommonWindowHelper().openWindowSuccess(image, message);
        } else {
            new GetCommonWindowHelper().openWindowUnSuccess(image, event -> updateStudentInDB(image));
        }
    }
}
