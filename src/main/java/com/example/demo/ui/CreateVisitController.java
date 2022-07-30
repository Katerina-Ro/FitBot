package com.example.demo.ui;

import com.example.demo.dao.Pass;
import com.example.demo.dao.Visits;
import com.example.demo.exception.ExceptionDB;
import com.example.demo.exception.SeveralException;
import com.example.demo.util.FillingFieldsHelper;
import com.example.demo.util.GetCommonWindowHelper;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class CreateVisitController {
    private final FillingFieldsHelper fillingFieldsHelper;
    private ObservableList<Pass> passObservableList;

    @FXML
    private Button backButton;
    @FXML
    private Button createVisitInDBButton;

    @FXML
    private TextField createCountVisitsInThisDay;
    @FXML
    private TextField createDateVisit;
    @FXML
    private TextField inputPhoneNumber;
    @FXML
    private Label passNumberLableDB;
    private final IntegerProperty passNumberLableDBProperty = new SimpleIntegerProperty();

    public CreateVisitController() {
        this.fillingFieldsHelper = new FillingFieldsHelper();
    }

    @FXML
    public void initialize(Stage createVisitStage, Image image) {
        FillingFieldsHelper.correctInputDateLine(createDateVisit);
        FillingFieldsHelper.correctInputIntegerLine(createCountVisitsInThisDay);
        observableFields(image);
        backButton.setOnAction(event -> createVisitStage.close());
    }

    private void observableFields(Image image) {
        createVisitInDBButton.setDisable(true);
        inputPhoneNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            String newPhoneNumberValueArray = inputPhoneNumber.getText();
            if (newPhoneNumberValueArray.length() == 11) {
                if (FillingFieldsHelper.isPhoneNumber(newPhoneNumberValueArray)) {
                    try {
                        passObservableList = fillingFieldsHelper.getTablePass(inputPhoneNumber.textProperty());
                    } catch (SeveralException e) {
                        new GetCommonWindowHelper().openWindowSeveralPass(image, inputPhoneNumber.getText());
                    }
                    if (passObservableList != null && !passObservableList.isEmpty()) {
                        if (passObservableList.size() == 1) {
                            Pass pass = passObservableList.get(0);

                            passNumberLableDBProperty.setValue(pass.getNumPass());
                            passNumberLableDBProperty.addListener((observable10, oldValue10, newValue10) -> {
                                passNumberLableDBProperty.setValue(newValue10);
                            });
                            passNumberLableDB.textProperty().bindBidirectional(passNumberLableDBProperty, new NumberStringConverter());

                            createDateVisit.textProperty().addListener((observable1, oldValue1, newValue1) -> {
                                if (FillingFieldsHelper.isDate(createDateVisit)) {
                                    createCountVisitsInThisDay.textProperty().addListener((observable2, oldValue2, newValue2) -> {
                                        if (createDateVisit != null && createDateVisit.getText() != null
                                            && FillingFieldsHelper.isNumbers(createCountVisitsInThisDay.getText())) {
                                            createVisitInDBButton.setDisable(false);
                                            createVisitInDBButton.setOnAction(event -> createVisitInDB(image));
                                        } else {
                                            createVisitInDBButton.setDisable(true);
                                        }
                                    });
                                } else {
                                    createVisitInDBButton.setDisable(true);
                                }
                            });
                            if (createCountVisitsInThisDay.getText() != null && !createCountVisitsInThisDay.getText().isEmpty()) {
                                if (FillingFieldsHelper.isNumbers(createCountVisitsInThisDay.getText())) {
                                    createDateVisit.textProperty().addListener((observable4, oldValue4, newValue4) -> {
                                        if (createDateVisit != null && createDateVisit.getText() != null
                                            && FillingFieldsHelper.isDate(createDateVisit)) {
                                            createVisitInDBButton.setDisable(false);
                                            createVisitInDBButton.setOnAction(event -> createVisitInDB(image));
                                        } else {
                                            createVisitInDBButton.setDisable(true);
                                        }
                                    });
                                } else {
                                    createVisitInDBButton.setDisable(true);
                                }
                            } else {
                                createCountVisitsInThisDay.textProperty().addListener((observable3, oldValue3, newValue3) -> {
                                    if (FillingFieldsHelper.isNumbers(createCountVisitsInThisDay.getText())) {
                                        createDateVisit.textProperty().addListener((observable4, oldValue4, newValue4) -> {
                                            if (createDateVisit != null && createDateVisit.getText() != null
                                                && FillingFieldsHelper.isDate(createDateVisit)) {
                                                createVisitInDBButton.setDisable(false);
                                                createVisitInDBButton.setOnAction(event -> createVisitInDB(image));
                                            } else {
                                                createVisitInDBButton.setDisable(true);
                                            }
                                        });
                                    } else {
                                        createVisitInDBButton.setDisable(true);
                                    }
                                });
                            }
                        } else {
                            // TODO: окно, если несколько абонементов
                        }
                    } else {
                        createVisitInDBButton.setDisable(true);
                        fillPassIdIfEmpty();
                    }
                } else {
                    createVisitInDBButton.setDisable(true);
                    fillPassIdIfEmpty();
                }
            } else {
                createVisitInDBButton.setDisable(true);
                fillPassIdIfEmpty();
            }
        });
        createVisitInDBButton.setDisable(true);
        passNumberLableDB.setText(null);
    }

    private void createVisitInDB(Image image) {
        LocalDate dateVisitForDB = null;
        try {
            dateVisitForDB = fillingFieldsHelper.convertFormatLocalDate(createDateVisit.getText());
        } catch (ParseException e) {
            String message = "Произошла ошибка во время записи в базу данных. Обратитесь к разработчику";
            new GetCommonWindowHelper().openWindowUnSuccess(image, event -> observableFields(image), message);
        }
        Integer countVisitForDB = Integer.valueOf(createCountVisitsInThisDay.getText());

        Visits visits = new Visits();
        visits.setPass(passNumberLableDBProperty.get());
        visits.setCountVisit(countVisitForDB);
        visits.setDateVisit(dateVisitForDB);
        Optional<List<Visits>> listVisits = fillingFieldsHelper.getVisitFromDB(passNumberLableDBProperty.get());
        boolean isExist = false;
        if (listVisits.isPresent()) {
            for (Visits v : listVisits.get()) {
                LocalDate dateFromDB = v.getDateVisit();
                if (dateFromDB != null) {
                    if (dateFromDB.isEqual(visits.getDateVisit())) {
                        isExist = true;
                    }
                }
            }
        }

        if (!isExist) {
            boolean isSuccess = false;
            try {
                isSuccess = fillingFieldsHelper.createVisit(visits);
                if (isSuccess) {
                    String message = "Посещение успешно внесено в базу данных";
                    new GetCommonWindowHelper().openWindowSuccess(image, message);
                } else {
                    String message = "Произошла ошибка во время записи в базу данных. Обратитесь к разработчику";
                    new GetCommonWindowHelper().openWindowUnSuccess(image, event -> createVisitInDB(image), message);
                }
            } catch (ExceptionDB e) {
                String message = "Произошла ошибка во время записи в базу данных. Обратитесь к разработчику";
                new GetCommonWindowHelper().openWindowUnSuccess(image, event -> createVisitInDB(image), message);
            }
        } else {
            // openWindowSeveral(image,newPhoneNumberValue);
        }
    }

    private void fillPassIdIfEmpty() {
        passNumberLableDBProperty.setValue(null);
        passNumberLableDBProperty.addListener((observable10, oldValue10, newValue10) -> {
            passNumberLableDBProperty.setValue(newValue10);
        });
        passNumberLableDB.textProperty().bindBidirectional(passNumberLableDBProperty, new NumberStringConverter());
    }
}
