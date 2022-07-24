package com.example.demo.ui;

import com.example.demo.dao.Visits;
import com.example.demo.util.FillingFieldsHelper;
import com.example.demo.util.GetCommonWindowHelper;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChangeVisitsController {
    private static final Logger logger;
    private final FillingFieldsHelper fillingFieldsHelper;

    @FXML
    private Button backButton;
    @FXML
    private Button changeButton;

    @FXML
    private Label countVisitsInThisDayValueDB;
    @FXML
    private Label dateVisitValueDB;
    @FXML
    private Label passNumberValueDB;
    @FXML
    private Label phoneLabel;

    @FXML
    private TextField newDateVisitValue;
    private final ObjectProperty<LocalDate> newDateVisitValueProperty = new SimpleObjectProperty<>();
    @FXML
    private TextField newCountVisitsInThisDayValue;

    static {
        logger = Logger.getLogger(ChangeStudentController.class.getName());
    }

    public ChangeVisitsController() {
        this.fillingFieldsHelper = new FillingFieldsHelper();
    }


    @FXML
    public void initialize(Stage changeVisitsStage, Image image, Integer pass, LocalDate dateVisit, Integer countVisit,
                           String inputPhoneNumber) {
        initData(image, pass, dateVisit, countVisit, inputPhoneNumber);
        backButton.setOnAction(event -> changeVisitsStage.close());
    }

    private void initData(Image image, Integer pass, LocalDate dateVisit, Integer countVisit,
                          String inputPhoneNumber) {
        phoneLabel.setText(inputPhoneNumber);
        passNumberValueDB.setText(String.valueOf(pass));
        dateVisitValueDB.setText(String.valueOf(dateVisit));
        countVisitsInThisDayValueDB.setText(String.valueOf(countVisit));

        observableNewValues(image);

    }

    private void observableNewValues(Image image) {
        changeButton.setDisable(true);
        FillingFieldsHelper.correctInputDateLine(newDateVisitValue);
        FillingFieldsHelper.correctInputIntegerLine(newCountVisitsInThisDayValue);

        newDateVisitValue.textProperty().addListener((observable1, oldValue1, newValue1) -> {
            if (FillingFieldsHelper.isDate(newDateVisitValue)) {
                changeButton.setDisable(false);
                changeButton.setOnAction(event -> updateVisitInDB(image));
                newCountVisitsInThisDayValue.textProperty().addListener((observable11, oldValue11, newValue11) -> {
                    if (FillingFieldsHelper.isNumbers(newDateVisitValueProperty.toString())) {
                        changeButton.setDisable(false);
                        changeButton.setOnAction(event -> updateVisitInDB(image));
                    } else {
                        changeButton.setDisable(true);
                    }
                });
            } else {
                changeButton.setDisable(true);
            }
        });

        newCountVisitsInThisDayValue.textProperty().addListener((observable2, oldValue2, newValue2) -> {
            if (FillingFieldsHelper.isNumbers(newDateVisitValue.getText())) {
                changeButton.setDisable(false);
                changeButton.setOnAction(event -> updateVisitInDB(image));
                newDateVisitValue.textProperty().addListener((observable1, oldValue1, newValue1) -> {
                    if (FillingFieldsHelper.isDate(newDateVisitValue)) {
                        changeButton.setDisable(false);
                        changeButton.setOnAction(event -> updateVisitInDB(image));
                    } else {
                        changeButton.setDisable(true);
                    }
                });
            } else {
                changeButton.setDisable(true);
            }
        });
    }

    @FXML
    public void updateVisitInDB(Image image) {
        Visits visit = new Visits();
        if (newDateVisitValue.getText() != null && FillingFieldsHelper.isDate(newDateVisitValue)) {
            try {
                visit.setDateVisit(fillingFieldsHelper.convertFormatLocalDate(newDateVisitValue.getText()));
            } catch (ParseException e) {
                String message = "Произошла ошибка во время записи в базу данных. Обратитесь к разработчику";
                new GetCommonWindowHelper().openWindowUnSuccess(image, event -> updateVisitInDB(image), message);
            }
        }
        if (newCountVisitsInThisDayValue.getText() != null && FillingFieldsHelper.isNumbers(
                (newCountVisitsInThisDayValue.getText()))) {
            visit.setCountVisit(Integer.valueOf(newCountVisitsInThisDayValue.getText()));
        }
        if (passNumberValueDB != null && FillingFieldsHelper.isNumbers(passNumberValueDB.getText())) {
            visit.setPass(Integer.valueOf(passNumberValueDB.getText()));
        }

        boolean isSuccessUpdate = fillingFieldsHelper.updateVisit(visit);
        if (isSuccessUpdate) {
            String message = "Посещение успешно обновлено в базе данных";
            new GetCommonWindowHelper().openWindowSuccess(image, message);
        } else {
            String message = "Произошла ошибка во время записи в базу данных. Обратитесь к разработчику";
            String messageForDeveloper = String.format("\nПроизошла ошибка в ChangeVisitsController во время записи " +
                    "в updateVisit номер телефона %s", phoneLabel);
            logger.log(Level.SEVERE, messageForDeveloper);
            new GetCommonWindowHelper().openWindowUnSuccess(image, event -> updateVisitInDB(image), message);
        }
    }
}
