package com.example.demo.ui;

import com.example.demo.dao.Pass;
import com.example.demo.util.FillingFieldsHelper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

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
                    passObservableList = fillingFieldsHelper.getTablePass(inputPhoneNumber.textProperty());
                    if (passObservableList != null && !passObservableList.isEmpty()) {
                        if (passObservableList.size() == 1) {
                            Pass pass = passObservableList.get(0);
                            passNumberLableDB.setText(String.valueOf(pass.getNumPass()));

                            createDateVisit.textProperty().addListener((observable1, oldValue1, newValue1) -> {
                                if (FillingFieldsHelper.isDate(createDateVisit)) {
                                    createCountVisitsInThisDay.textProperty().addListener((observable2, oldValue2, newValue2) -> {
                                        if (FillingFieldsHelper.isNumbers(createCountVisitsInThisDay.getText())) {
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
                                        if (FillingFieldsHelper.isDate(createDateVisit)) {
                                            createVisitInDBButton.setDisable(false);
                                            createVisitInDBButton.setOnAction(event -> createVisitInDB(image));
                                        } else {
                                            createVisitInDBButton.setDisable(true);
                                        }
                                    });
                                }
                            } else {
                                createCountVisitsInThisDay.textProperty().addListener((observable3, oldValue3, newValue3) -> {
                                    if (FillingFieldsHelper.isNumbers(createCountVisitsInThisDay.getText())) {
                                        createDateVisit.textProperty().addListener((observable4, oldValue4, newValue4) -> {
                                            if (FillingFieldsHelper.isDate(createDateVisit)) {
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
                        }
                    } else {
                        // TODO: окно, если несколько абонементов
                    }
                } else {
                    createVisitInDBButton.setDisable(true);
                    passNumberLableDB.setText(null);
                }
                createVisitInDBButton.setDisable(true);
            }
            createVisitInDBButton.setDisable(true);
        });
        createVisitInDBButton.setDisable(true);
        passNumberLableDB.setText(null);
    }

    private void createVisitInDB(Image image) {

    }
}
