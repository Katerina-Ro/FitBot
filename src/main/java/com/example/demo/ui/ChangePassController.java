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

import java.text.ParseException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChangePassController {
    private static final Logger logger;
    private final FillingFieldsHelper fillingFieldsHelper;
    private ObservableList<Pass> passObservableList;

    @FXML
    private TextField inputPhoneNumber;
    private final StringProperty phoneNumberProperty = new SimpleStringProperty();
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
    private Label dateEndValue;
    private final ObjectProperty<LocalDate> dateEndValueProperty = new SimpleObjectProperty<>();
    @FXML
    private Label countFreezeValue;
    private final IntegerProperty countFreezeValueProperty = new SimpleIntegerProperty();
    @FXML
    private Label dateStartFreezeValue;
    private final ObjectProperty<LocalDate> dateStartFreezeValueProperty = new SimpleObjectProperty<>();
    @FXML
    private Label dateStartValue;
    private final ObjectProperty<LocalDate> dateStartValueProperty = new SimpleObjectProperty<>();
    @FXML
    private Label passIdValue;
    private final IntegerProperty passIdValueProperty = new SimpleIntegerProperty();
    @FXML
    private Label visitLimitValue;
    private final IntegerProperty visitLimitValueProperty = new SimpleIntegerProperty();
    @FXML
    private Label leftVisitsValue;
    private final IntegerProperty leftVisitsValueProperty = new SimpleIntegerProperty();

    @FXML
    private Button backButton;
    @FXML
    private Button changePassButton;

    static {
        logger = Logger.getLogger(ChangeStudentController.class.getName());
    }

    public ChangePassController() {
        this.fillingFieldsHelper = new FillingFieldsHelper();
    }

    @FXML
    public void initialize(Stage stage, Image image) {
        FillingFieldsHelper.correctInputDateLine(dateStartPassInput);
        FillingFieldsHelper.correctInputDateLine(dateEndPassInput);
        FillingFieldsHelper.correctInputDateLine(dateStartFreezeInput);
        FillingFieldsHelper.correctInputIntegerLine(countFreezeInput);
        FillingFieldsHelper.correctInputIntegerLine(countVisitInput);

        observablePhone(image);
        backButton.setOnAction(event -> stage.close());
    }

    private void observablePhone(Image image) {
        inputPhoneNumber.textProperty().bindBidirectional(phoneNumberProperty, new DefaultStringConverter());
        phoneNumberProperty.addListener((observable, oldValue, newValue) -> {
            if (phoneNumberProperty.length().get() == 11) {
                if (FillingFieldsHelper.isPhoneNumber(inputPhoneNumber.getText())) {
                    try {
                        passObservableList = fillingFieldsHelper.getTablePass(phoneNumberProperty);
                    } catch (SeveralException e) {
                        new GetCommonWindowHelper().openWindowSeveralPass(image, inputPhoneNumber.getText());
                    }
                    if (passObservableList != null && !passObservableList.isEmpty()) {
                        fillStageIfPassObservableListNotEmpty(image);
                    } else {
                        fillStageIfPassObservableListIsEmpty();
                    }
                }
            } else {
                fillStageIfPassObservableListIsEmpty();
            }
        });
    }

    private void fillStageIfPassObservableListNotEmpty(Image image) {
        for (Pass p : passObservableList) {
            // Заполняем поле "Номер абонемента"
            passIdValueProperty.setValue(p.getNumPass());
            passIdValueProperty.addListener((observable1, oldValue1, newValue1) -> {
                passIdValueProperty.setValue(newValue1);
            });
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

            // Заполняем количество оставшихся посещений по текущему абонементу
            leftVisitsValueProperty.setValue(p.getVisitsLeft());
            leftVisitsValueProperty.addListener((observable77, oldValue77, newValue77) -> {
                leftVisitsValueProperty.setValue(newValue77);
            });
            leftVisitsValue.textProperty().bindBidirectional(leftVisitsValueProperty, new NumberStringConverter());
        }

        changePassButton.setDisable(false);
        changePassButton.setOnAction(event -> updatePassInDB(image));
    }

    private void fillStageIfPassObservableListIsEmpty() {
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

        // Заполняем количество оставшихся посещений по текущему абонементу
        leftVisitsValueProperty.setValue(null);
        leftVisitsValueProperty.addListener((observable77, oldValue77, newValue77) -> {
            leftVisitsValueProperty.setValue(newValue77);
        });
        leftVisitsValue.textProperty().bindBidirectional(leftVisitsValueProperty, new NumberStringConverter());

        changePassButton.setDisable(true);
    }

    private void updatePassInDB(Image image) {
        Pass newPass = new Pass();
        newPass.setNumPass(Integer.valueOf(passIdValue.getText()));
        if (dateStartPassInput.getText() != null && !dateStartPassInput.getText().isBlank()
                && FillingFieldsHelper.isDate(dateStartPassInput)) {
            try {
                newPass.setDateStart(fillingFieldsHelper.convertFormatLocalDate(dateStartPassInput.getText()));
            } catch (ParseException e) {
                String message = "Произошла ошибка во время записи в базу данных. Обратитесь к разработчику";
                new GetCommonWindowHelper().openWindowUnSuccess(image, event -> fillStageIfPassObservableListNotEmpty(image),
                        message);
            }
        }
        if (countVisitInput.getText() != null && !countVisitInput.getText().isBlank()
                && FillingFieldsHelper.isNumbers(countVisitInput.getText())) {
            newPass.setVisitLimit(Integer.valueOf(countVisitInput.getText()));
        }
        if (dateEndPassInput.getText() != null &&  !dateEndPassInput.getText().isBlank() &&
                FillingFieldsHelper.isDate(dateEndPassInput)) {
            try {
                newPass.setDateEnd(fillingFieldsHelper.convertFormatLocalDate(dateEndPassInput.getText()));
            } catch (ParseException e) {
                String message = "Произошла ошибка во время записи в базу данных. Обратитесь к разработчику";
                new GetCommonWindowHelper().openWindowUnSuccess(image, event -> fillStageIfPassObservableListNotEmpty(image),
                        message);
            }
        }
        if (countFreezeInput.getText() != null && !countFreezeInput.getText().isBlank()
                && FillingFieldsHelper.isNumbers(countFreezeInput.getText())) {
            newPass.setFreezeLimit(Integer.valueOf(countFreezeInput.getText()));
        }
        if (dateStartFreezeInput.getText() != null && !dateStartFreezeInput.getText().isBlank()
                && FillingFieldsHelper.isDate(dateStartFreezeInput)) {
            try {
                newPass.setDateStartFreeze(fillingFieldsHelper.convertFormatLocalDate(dateStartFreezeInput.getText()));
            } catch (ParseException e) {
                String message = "Произошла ошибка во время записи в базу данных. Обратитесь к разработчику";
                new GetCommonWindowHelper().openWindowUnSuccess(image, event -> fillStageIfPassObservableListNotEmpty(image),
                        message);
            }
        }

        try {
            boolean isSuccessUpdate = fillingFieldsHelper.updatePass(newPass);
            if (isSuccessUpdate) {
                String message = "Абонемент успешно обновлен в базе данных";
                new GetCommonWindowHelper().openWindowSuccess(image, message);
            } else {
                String message = "Произошла ошибка во время записи в базу данных. Обратитесь к разработчику";
                String messageForDeveloper = String.format("\nПроизошла ошибка в ChangeStudentController во время записи " +
                        "в updateVisitors номер телефона %s", inputPhoneNumber.getText());
                logger.log(Level.SEVERE, messageForDeveloper);
                new GetCommonWindowHelper().openWindowUnSuccess(image, event -> fillStageIfPassObservableListNotEmpty(image), message);
            }
        } catch (Exception e) {
            String message = "Произошла ошибка во время записи в базу данных. Обратитесь к разработчику";
            String messageForDeveloper = String.format("\nПроизошла ошибка в ChangeStudentController во время записи " +
                    "в updateVisitors номер телефона %s", inputPhoneNumber.getText());
            logger.log(Level.SEVERE, messageForDeveloper);
            new GetCommonWindowHelper().openWindowUnSuccess(image, event -> fillStageIfPassObservableListNotEmpty(image), message);
        }
    }
}
