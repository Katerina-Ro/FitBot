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

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class UnFreezePassController {
    private final FillingFieldsHelper fillingFieldsHelper;
    private ObservableList<Pass> passObservableList;
    private AtomicReference<String> phoneNumberForSearch = new AtomicReference<>();
    private AtomicInteger passIdForDB = new AtomicInteger();

    @FXML
    private Button backButton;
    @FXML
    private Button unFreezeButton;

    @FXML
    private TextField inputPhoneNumber;
    private final StringProperty phoneNumberProperty = new SimpleStringProperty("");

    @FXML
    private Label countFreezeDB;
    private final IntegerProperty countFreezeDBProperty = new SimpleIntegerProperty();
    @FXML
    private Label dateStartFreezeDB;
    private final ObjectProperty<LocalDate> dateStartFreezeDBProperty = new SimpleObjectProperty<>();
    @FXML
    private Label passIdValue;
    private final IntegerProperty passIdValueProperty = new SimpleIntegerProperty();

    public UnFreezePassController() {
        this.fillingFieldsHelper = new FillingFieldsHelper();
    }

    @FXML
    void initialize(Stage stageUnFreezePass, Image image) {
        observeInputPhoneNumber(image);
        backButton.setOnAction(event -> stageUnFreezePass.close());
    }

    private void observeInputPhoneNumber(Image image) {
        FillingFieldsHelper.correctInputPhoneLine(inputPhoneNumber);
        unFreezeButton.setDisable(true);

        inputPhoneNumber.textProperty().bindBidirectional(phoneNumberProperty, new DefaultStringConverter());
        phoneNumberProperty.addListener((observable, oldValue, newValue) -> {
            if (phoneNumberProperty.length().get() == 11) {
                passObservableList = fillingFieldsHelper.getPassList(inputPhoneNumber.getText());
                if (!passObservableList.isEmpty()) {
                    phoneNumberForSearch.set(inputPhoneNumber.getText());
                    fillGetInfoPass(image);
                } else {
                    phoneNumberForSearch.set(null);
                    fillGetInfoPassIfEmpty();
                    unFreezeButton.setDisable(true);
                }
            } else {
                phoneNumberForSearch.set(null);
                fillGetInfoPassIfEmpty();
                unFreezeButton.setDisable(true);
            }
        });
    }

    private void fillGetInfoPass(Image image) {
        for (Pass p : passObservableList) {
            // Заполняем поле "Номер абонемента"
            passIdValueProperty.setValue(p.getNumPass());
            passIdForDB.set(p.getNumPass());
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
        unFreezeButton.setDisable(false);
        unFreezeButton.setOnAction(event -> unFreezePassInDB(image));
    }

    private void unFreezePassInDB(Image image) {
        boolean isSuccessCreateFreeze = false;
        if (passIdForDB != null && FillingFieldsHelper.isNumbers(String.valueOf(passIdForDB))
                && passIdForDB.get() != 0) {
            isSuccessCreateFreeze = fillingFieldsHelper.unFreezePass(passIdForDB.get());
        }
        if (isSuccessCreateFreeze) {
            String message = "Абонемент разморожен";
            new GetCommonWindowHelper().openWindowSuccess(image, message);
        } else {
            String messageError = "Произошла ошибка при попытке заморозки. Обратитесь к разработчику";
            new GetCommonWindowHelper().openWindowUnSuccess(image, event -> unFreezePassInDB(image), messageError);
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
