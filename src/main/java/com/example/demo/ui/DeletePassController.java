package com.example.demo.ui;

import com.example.demo.dao.Pass;
import com.example.demo.exception.SeveralException;
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

public class DeletePassController {
    private final FillingFieldsHelper fillingFieldsHelper;
    private ObservableList<Pass> passObservableList;

    @FXML
    private Button backButton;
    @FXML
    private Button deletePassButton;


    @FXML
    private Label countFreezeValue;
    private final IntegerProperty countFreezeValueProperty = new SimpleIntegerProperty();
    @FXML
    private Label dateEndValue;
    private final ObjectProperty<LocalDate> dateEndValueProperty = new SimpleObjectProperty<>();
    @FXML
    private Label dateStartFreezeValue;
    private final ObjectProperty<LocalDate> dateStartFreezeValueProperty = new SimpleObjectProperty<>();
    @FXML
    private Label dateStartValue;
    private final ObjectProperty<LocalDate> dateStartValueProperty = new SimpleObjectProperty<>();
    @FXML
    private TextField inputPhoneNumber;
    private final StringProperty phoneNumberProperty = new SimpleStringProperty("");
    @FXML
    private Label passIdValue;
    private final IntegerProperty passIdValueProperty = new SimpleIntegerProperty();
    @FXML
    private Label visitLimitValue;
    private final IntegerProperty visitLimitValueProperty = new SimpleIntegerProperty();

    public DeletePassController() {
        this.fillingFieldsHelper = new FillingFieldsHelper();
    }

    @FXML
    void initialize(Stage stageDeletePass, Image image, String phoneNumber) {
        observeInputPhoneNumber(image);
        backButton.setOnAction(event -> stageDeletePass.close());
        deletePassButton.setDisable(true);
    }

    private void observeInputPhoneNumber(Image image) {
        FillingFieldsHelper.correctInputPhoneLine(inputPhoneNumber);
        inputPhoneNumber.textProperty().bindBidirectional(phoneNumberProperty, new DefaultStringConverter());
        phoneNumberProperty.addListener((observable, oldValue, newValue) -> {
            if (phoneNumberProperty.length().get() == 11) {
                try {
                    passObservableList = fillingFieldsHelper.getTablePass(phoneNumberProperty);
                } catch (SeveralException e) {
                    new GetCommonWindowHelper().openWindowSeveralPass(image, inputPhoneNumber.getText());
                }
                if (!passObservableList.isEmpty()) {
                    fillGetInfoPassIfListNoEmpty(image);
                } else {
                    fillInfoPassIfListEmpty();
                }
            } else {
                fillInfoPassIfListEmpty();
            }
        });
    }

    private void fillGetInfoPassIfListNoEmpty(Image image) {
        for (Pass p : passObservableList) {
            // Заполняем поле "Номер абонемента"
            passIdValueProperty.setValue(p.getNumPass());
            passIdValueProperty.addListener((observable1, oldValue1, newValue1) -> passIdValueProperty.setValue(newValue1));
            passIdValue.textProperty().bindBidirectional(passIdValueProperty, new NumberStringConverter());

            // Заполняем поле "Дата начала абонемента"
            dateStartValueProperty.setValue(p.getDateStart());
            dateStartValueProperty.addListener((observable3, oldValue3, newValue3) -> {
                dateStartValueProperty.setValue(newValue3);
            });
            dateStartValue.textProperty().bindBidirectional(dateStartValueProperty, new LocalDateStringConverter());

            // Заполняем поле "До какого числа абонемент"
            dateEndValueProperty.setValue(p.getDateEnd());
            dateEndValueProperty.addListener((observable4, oldValue4, newValue4) -> {
                dateEndValueProperty.setValue(newValue4);
            });
            dateEndValue.textProperty().bindBidirectional(dateEndValueProperty, new LocalDateStringConverter());

            // Заполняем поле "Количество занятий в абонементе"
            visitLimitValueProperty.setValue(p.getVisitLimit());
            visitLimitValueProperty.addListener((observable5, oldValue5, newValue5) -> {
                visitLimitValueProperty.setValue(newValue5);
            });
            visitLimitValue.textProperty().bindBidirectional(visitLimitValueProperty, new NumberStringConverter());

            // Заполняем поле "Количество использованных заморозок"
            countFreezeValueProperty.setValue(p.getFreezeLimit());
            countFreezeValueProperty.addListener((observable6, oldValue6, newValue6) -> {
                countFreezeValueProperty.setValue(newValue6);
            });
            countFreezeValue.textProperty().bindBidirectional(countFreezeValueProperty, new NumberStringConverter());

            // Заполняем поле "Дата начала последней заморозки"
            dateStartFreezeValueProperty.setValue(p.getDateStartFreeze());
            dateStartFreezeValueProperty.addListener((observable7, oldValue7, newValue7) -> {
                dateStartFreezeValueProperty.setValue(newValue7);
            });
            dateStartFreezeValue.textProperty().bindBidirectional(dateStartFreezeValueProperty, new LocalDateStringConverter());

            deletePassButton.setDisable(false);
            deletePassButton.setOnAction(event -> new GetCommonWindowHelper().openWindowConfirmation(image,
                    event2 -> deletePassFromDB(image)));
        }
    }

    private void deletePassFromDB(Image image) {
        int passId;
        if (passIdValueProperty.getValue() != null) {
            passId = passIdValueProperty.getValue();
            boolean isSuccess = fillingFieldsHelper.deletePassFromDB(passId);
            if (isSuccess) {
                String message = "Абонемент успешно удален из базы данных";
                new GetCommonWindowHelper().openWindowSuccess(image, message);
            } else {
                String message = "Произошла ошибка во время удаления из базы данных. Обратитесь к разработчику";
                new GetCommonWindowHelper().openWindowUnSuccess(image, event -> deletePassFromDB(image), message);
            }
        }
    }

    private void fillInfoPassIfListEmpty() {
        // Заполняем поле "Номер абонемента"
        passIdValueProperty.setValue(null);
        passIdValueProperty.addListener((observable1, oldValue1, newValue1) -> {
            passIdValueProperty.setValue(newValue1);
        });
        passIdValue.textProperty().bindBidirectional(passIdValueProperty, new NumberStringConverter());

        // Заполняем поле "Дата начала абонемента"
        dateStartValueProperty.setValue(null);
        dateStartValueProperty.addListener((observable3, oldValue3, newValue3) -> {
            dateStartValueProperty.setValue(newValue3);
        });
        dateStartValue.textProperty().bindBidirectional(dateStartValueProperty, new LocalDateStringConverter());

        // Заполняем поле "До какого числа абонемент"
        dateEndValueProperty.setValue(null);
        dateEndValueProperty.addListener((observable4, oldValue4, newValue4) -> {
            dateEndValueProperty.setValue(newValue4);
        });
        dateEndValue.textProperty().bindBidirectional(dateEndValueProperty, new LocalDateStringConverter());

        // Заполняем поле "Количество занятий в абонементе"
        visitLimitValueProperty.setValue(null);
        visitLimitValueProperty.addListener((observable5, oldValue5, newValue5) -> {
            visitLimitValueProperty.setValue(newValue5);
        });
        visitLimitValue.textProperty().bindBidirectional(visitLimitValueProperty, new NumberStringConverter());

        // Заполняем поле "Количество использованных заморозок"
        countFreezeValueProperty.setValue(null);
        countFreezeValueProperty.addListener((observable6, oldValue6, newValue6) -> {
            countFreezeValueProperty.setValue(newValue6);
        });
        countFreezeValue.textProperty().bindBidirectional(countFreezeValueProperty, new NumberStringConverter());

        // Заполняем поле "Дата начала последней заморозки"
        dateStartFreezeValueProperty.setValue(null);
        dateStartFreezeValueProperty.addListener((observable7, oldValue7, newValue7) -> {
            dateStartFreezeValueProperty.setValue(newValue7);
        });
        dateStartFreezeValue.textProperty().bindBidirectional(dateStartFreezeValueProperty, new LocalDateStringConverter());
    }
}
