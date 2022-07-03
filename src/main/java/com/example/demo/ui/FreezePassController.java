package com.example.demo.ui;

import com.example.demo.dao.Pass;
import com.example.demo.util.FillingFieldsHelper;
import com.example.demo.util.GetCommonWindowHelper;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.LocalDateStringConverter;
import javafx.util.converter.NumberStringConverter;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class FreezePassController {
    private final FillingFieldsHelper fillingFieldsHelper;
    private ObservableList<Pass> passObservableList;
    private AtomicReference<String> phoneNumberForSearch = new AtomicReference<>();
    private AtomicInteger newCountFreezeForDB = new AtomicInteger();
    private AtomicReference<String> newDateStartFreezeForDB = new AtomicReference<>();
    private AtomicInteger passIdForDB = new AtomicInteger();

    @FXML
    private Button backButton;
    @FXML
    private Button freezeButton;

    @FXML
    private Label countFreezeDB;
    private final IntegerProperty countFreezeDBProperty = new SimpleIntegerProperty();
    @FXML
    private Label dateStartFreezeDB;
    private final ObjectProperty<LocalDate> dateStartFreezeDBProperty = new SimpleObjectProperty<>();
    @FXML
    private Label passIdValue;
    private final IntegerProperty passIdValueProperty = new SimpleIntegerProperty();
    @FXML
    private TextField inputPhoneNumber;
    private final StringProperty phoneNumberProperty = new SimpleStringProperty("");
    @FXML
    private TextField newCountFreezeValue;
    private final IntegerProperty newCountFreezeValueProperty = new SimpleIntegerProperty();
    @FXML
    private TextField newDateStartFreezeValue;
    private final ObjectProperty<LocalDate> newDateStartFreezeValueProperty = new SimpleObjectProperty<>();

    public FreezePassController() {
        this.fillingFieldsHelper = new FillingFieldsHelper();
    }

    @FXML
    void initialize(Stage stageFreezePass, Image image) {
        observeInputPhoneNumber(image);
        backButton.setOnAction(event -> stageFreezePass.close());
    }

    private void observeInputPhoneNumber(Image image) {
        FillingFieldsHelper.correctInputPhoneLine(inputPhoneNumber);
        FillingFieldsHelper.correctInputIntegerLine(newCountFreezeValue);
        FillingFieldsHelper.correctInputDateLine(newDateStartFreezeValue);
        freezeButton.setDisable(true);

        inputPhoneNumber.textProperty().bindBidirectional(phoneNumberProperty, new DefaultStringConverter());
        phoneNumberProperty.addListener((observable, oldValue, newValue) -> {
            if (phoneNumberProperty.length().get() == 11) {
                passObservableList = fillingFieldsHelper.getTablePass(phoneNumberProperty);
                if (!passObservableList.isEmpty()) {
                    phoneNumberForSearch.set(inputPhoneNumber.getText());
                    fillGetInfoPass(image);
                } else {
                    phoneNumberForSearch.set(null);
                    fillGetInfoPassIfEmpty();
                    freezeButton.setDisable(true);
                }
            } else {
                phoneNumberForSearch.set(null);
                fillGetInfoPassIfEmpty();
                freezeButton.setDisable(true);
            }
        });
    }

    private void fillGetInfoPass(Image image) {
        for (Pass p : passObservableList) {
            // Заполняем поле "Номер абонемента"
            passIdValueProperty.setValue(p.getNumPass());
            passIdValueProperty.addListener((observable1, oldValue1, newValue1) -> {
                passIdValueProperty.setValue(newValue1);
                passIdForDB.set((Integer) newValue1);
            });
            passIdValue.textProperty().bindBidirectional(passIdValueProperty, new NumberStringConverter());

            // Заполняем поле "Количество использованных заморозок"
            countFreezeDBProperty.setValue(p.getFreezeLimit());
            countFreezeDBProperty.addListener((observable6, oldValue6, newValue6) -> {
                countFreezeDBProperty.setValue(newValue6);
            });
            countFreezeDB.textProperty().bindBidirectional(countFreezeDBProperty, new NumberStringConverter());

            // Заполняем поле "Дата начала последней заморозки"
            dateStartFreezeDBProperty.setValue(p.getDateStartFreeze());
            dateStartFreezeDBProperty.addListener((observable7, oldValue7, newValue7) -> {
                dateStartFreezeDBProperty.setValue(newValue7);
            });
            dateStartFreezeDB.textProperty().bindBidirectional(dateStartFreezeDBProperty, new LocalDateStringConverter());

            observableNewValues(image);
        }
    }

    private void observableNewValues(Image image) {
        FillingFieldsHelper.correctInputPhoneLine(inputPhoneNumber);
        FillingFieldsHelper.correctInputIntegerLine(newCountFreezeValue);
        FillingFieldsHelper.correctInputDateLine(newDateStartFreezeValue);

        observableNewDataFreeze(image);
    }

    private void observableNewDataFreeze(Image image) {
        newCountFreezeValue.textProperty().addListener((observable2, oldValue2, newValue2) -> {
            if (FillingFieldsHelper.isNumbers(newCountFreezeValue.getText())) {
                newDateStartFreezeValue.textProperty().addListener((observable32, oldValue32, newValue32) -> {
                    if (FillingFieldsHelper.isDate(newDateStartFreezeValue)) {
                        newCountFreezeForDB.set(Integer.parseInt(newCountFreezeValue.getText()));
                        newDateStartFreezeForDB.set(newValue32);
                        freezeButton.setDisable(false);
                        freezeButton.setOnAction(event -> freezePassInDB(image));
                    } else {
                        newCountFreezeForDB.set(0);
                        newDateStartFreezeForDB.set(null);
                        freezeButton.setDisable(true);
                    }
                });
            } else {
                newCountFreezeForDB.set(0);
                newDateStartFreezeForDB.set(null);
                freezeButton.setDisable(true);
            }
        });

        newDateStartFreezeValue.textProperty().addListener((observable35, oldValue35, newValue35) -> {
            if (FillingFieldsHelper.isDate(newDateStartFreezeValue)) {
                newCountFreezeValue.textProperty().addListener((observable26, oldValue26, newValue26) -> {
                    if (FillingFieldsHelper.isNumbers(newValue26)) {
                        newCountFreezeForDB.set(Integer.parseInt(newValue26));
                        newDateStartFreezeForDB.set(newValue35);
                        freezeButton.setDisable(false);
                        freezeButton.setOnAction(event -> freezePassInDB(image));
                    } else {
                        newCountFreezeForDB.set(0);
                        newDateStartFreezeForDB.set(null);
                        freezeButton.setDisable(true);
                    }
                });
            } else {
                newCountFreezeForDB.set(0);
                newDateStartFreezeForDB.set(null);
                freezeButton.setDisable(true);
            }
        });
    }

    private void freezePassInDB(Image image) {
        Pass pass = new Pass();
        if (passIdForDB != null && FillingFieldsHelper.isNumbers(String.valueOf(passIdForDB))) {
            pass.setNumPass(passIdForDB.get());
        }
        if (newDateStartFreezeForDB != null && newDateStartFreezeForDB.get() != null) {
            try {
                pass.setDateStartFreeze(fillingFieldsHelper.convertFormatLocalDate(newDateStartFreezeForDB.get()));
            } catch (ParseException e) {
                String messageError = "Ошибка парсинга даты при попытке заморозки. Обратитесь к разработчику";
                new GetCommonWindowHelper().openWindowUnSuccess(image, event -> freezePassInDB(image));
            }
        }
        if (newCountFreezeForDB != null && FillingFieldsHelper.isNumbers(String.valueOf(newCountFreezeForDB))) {
            pass.setFreezeLimit(newCountFreezeForDB.get());
        }
        boolean isSuccessCreateFreeze = fillingFieldsHelper.freezePass(pass);

        if (isSuccessCreateFreeze) {
            String message = "Абонемент заморожен";
            new GetCommonWindowHelper().openWindowSuccess(image, message);
        } else {
            String messageError = "Произошла ошибка при попытке заморозки. Обратитесь к разработчику";
            new GetCommonWindowHelper().openWindowUnSuccess(image, event -> freezePassInDB(image));
        }
    }

    private void fillGetInfoPassIfEmpty() {
        // Заполняем поле "Номер абонемента"
        passIdValueProperty.setValue(null);
        passIdValueProperty.addListener((observable13, oldValue13, newValue13) -> {
            passIdValueProperty.setValue(newValue13);
        });
        passIdValue.textProperty().bindBidirectional(passIdValueProperty, new NumberStringConverter());

        // Заполняем поле "Количество использованных заморозок"
        countFreezeDBProperty.setValue(0);
        countFreezeDBProperty.addListener((observable16, oldValue16, newValue16) -> {
            countFreezeDBProperty.setValue(newValue16);
        });
        countFreezeDB.textProperty().bindBidirectional(countFreezeDBProperty, new NumberStringConverter());

        // Заполняем поле "Дата начала последней заморозки"
        dateStartFreezeDBProperty.setValue(null);
        dateStartFreezeDBProperty.addListener((observable17, oldValue17, newValue17) -> {
            dateStartFreezeDBProperty.setValue(newValue17);
        });
        dateStartFreezeDB.textProperty().bindBidirectional(dateStartFreezeDBProperty, new LocalDateStringConverter());
    }
}
