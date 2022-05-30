package com.example.demo.ui;

import com.example.demo.util.FillingFieldsHelper;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
import javafx.util.converter.NumberStringConverter;

import java.time.LocalDate;
import java.util.Optional;

public class ChangePassController {
    private final FillingFieldsHelper fillingFieldsHelper;

    @FXML
    private TextField inputPhoneNumber;
    private final StringProperty phoneNumberProperty = new SimpleStringProperty("");

    @FXML
    private TextField countFreezeInput;
    private final IntegerProperty countFreezeInputProperty = new SimpleIntegerProperty();

    @FXML
    private TextField countVisitInput;
    private final IntegerProperty countVisitInputProperty = new SimpleIntegerProperty();

    @FXML
    private TextField dateEndPassInput;
    private final ObjectProperty<LocalDate> dateEndPassInputProperty = new SimpleObjectProperty<>();

    @FXML
    private TextField dateStartFreezeInput;
    private final ObjectProperty<LocalDate> dateStartFreezeInputProperty = new SimpleObjectProperty<>();

    @FXML
    private TextField dateStartPassInput;
    private final ObjectProperty<LocalDate> dateStartPassInputProperty = new SimpleObjectProperty<>();

    @FXML
    private Button backButton;
    @FXML
    private Button changePassButton;


    public ChangePassController() {
        this.fillingFieldsHelper = new FillingFieldsHelper();
    }

    @FXML
    public void initialize(Stage stage) {
        fillingFieldsHelper.correctInputDateLine(dateStartPassInput);
        fillingFieldsHelper.correctInputDateLine(dateEndPassInput);
        fillingFieldsHelper.correctInputDateLine(dateStartFreezeInput);
        fillingFieldsHelper.correctInputIntegerLine(countFreezeInput);
        fillingFieldsHelper.correctInputIntegerLine(countVisitInput);

        if (countFreezeInput != null || countVisitInput != null || dateStartPassInput != null || dateStartFreezeInput != null
                || dateEndPassInput != null) {
            changePassButton.setOnAction(event -> changePassInDB());
        }
    }



    private Optional<LocalDate> getDateStartPassForInputDB(TextField date) {
        fillingFieldsHelper.correctInputDateLine(date);
        dateStartPassInputProperty.setValue(getLocalDateFromTextField(date));
        dateStartPassInputProperty.addListener((observable13, oldValue13, newValue13) -> {
            if (fillingFieldsHelper.isDate(date)) {
                dateStartPassInputProperty.setValue(newValue13);
            }
        });
        dateStartPassInput.textProperty().bindBidirectional(dateStartPassInputProperty, new LocalDateStringConverter());
        return Optional.ofNullable(LocalDate.parse(dateStartPassInput.toString()));
    }

    private void changePassInDB() {

    }

    private Integer getCountVisitForInputDB(TextField countVisitInput) {
        fillingFieldsHelper.correctInputIntegerLine(countVisitInput);
        countVisitInputProperty.setValue(getIntegerFromTextField(countVisitInput));
        countVisitInputProperty.addListener((observable14, oldValue14, newValue14) -> {
            countVisitInputProperty.setValue(newValue14);
        });
        countVisitInput.textProperty().bindBidirectional(countVisitInputProperty, new NumberStringConverter());
        return Integer.valueOf(countVisitInput.toString());
    }

    private Integer getCountFreezeForInputDB(TextField countFreeze) {
        fillingFieldsHelper.correctInputIntegerLine(countFreeze);
        countFreezeInputProperty.setValue(getIntegerFromTextField(countFreeze));
        countFreezeInputProperty.addListener((observable17, oldValue17, newValue17) -> {
            countFreezeInputProperty.setValue(newValue17);
        });
        countFreezeInput.textProperty().bindBidirectional(countVisitInputProperty, new NumberStringConverter());
        return Integer.valueOf(countFreezeInput.toString());
    }

    private Integer getIntegerFromTextField(TextField numberInput) {
        if (numberInput != null) {
            return Integer.valueOf(countVisitInput.toString());
        }
        return 0;
    }

    private Optional<LocalDate> getStartFreezeForInputDB(TextField date) {
        fillingFieldsHelper.correctInputDateLine(date);
        dateStartFreezeInputProperty.setValue(getLocalDateFromTextField(date));
        dateStartFreezeInputProperty.addListener((observable15, oldValue15, newValue15) -> {
            if (fillingFieldsHelper.isDate(date)) {
                dateStartFreezeInputProperty.setValue(newValue15);
            }
        });
        dateStartFreezeInput.textProperty().bindBidirectional(dateStartFreezeInputProperty, new LocalDateStringConverter());
        return Optional.ofNullable(LocalDate.parse(dateStartFreezeInput.toString()));
    }

    private Optional<LocalDate> getDateEndPassForInputDB(TextField date) {
        fillingFieldsHelper.correctInputDateLine(date);
        dateEndPassInputProperty.setValue(getLocalDateFromTextField(date));
        dateEndPassInputProperty.addListener((observable16, oldValue16, newValue16) -> {
            if (fillingFieldsHelper.isDate(date)) {
                dateEndPassInputProperty.setValue(newValue16);
            }
        });
        dateEndPassInput.textProperty().bindBidirectional(dateEndPassInputProperty, new LocalDateStringConverter());
        return Optional.ofNullable(LocalDate.parse(dateEndPassInput.toString()));
    }

    private LocalDate getLocalDateFromTextField(TextField date) {
        return LocalDate.parse(date.toString());
    }
}
