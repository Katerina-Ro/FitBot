package com.example.demo.ui;

import com.example.demo.config.DBConfig;
import com.example.demo.dao.Visitors;
import com.example.demo.dao.repositories.impl.VisitorsRepository;
import com.example.demo.util.FillingFieldsHelper;
import com.example.demo.util.GetCommonWindowHelper;
import com.example.demo.util.PatternTemplate;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.regex.Pattern;

public class CreateStudentController {
    private ActionsWithPassController actionsWithPassController;
    private final VisitorsRepository visitorsRepository;
    Pattern p = Pattern.compile(PatternTemplate.INTEGER_LINE.getTemplate());
    @FXML
    private Button backButton;

    @FXML
    private Button createPassButton;

    @FXML
    private Button createStudentButton;

    @FXML
    private Label firstNameLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private TextField newFirstNameValue;

    @FXML
    private TextField newNameValue;
    private final StringProperty newNameValueProperty = new SimpleStringProperty("");

    @FXML
    private TextField newPatronymicValue;

    @FXML
    private TextField newPhoneNumberValue;
    private final StringProperty newPhoneNumberValueProperty = new SimpleStringProperty("");

    @FXML
    private Label patronymicLabel;

    @FXML
    private Label phoneNumberLabel;

    public CreateStudentController() {
        NamedParameterJdbcTemplate jdbcTemplate = new DBConfig().namedParameterJdbcTemplate();
        this.visitorsRepository = new VisitorsRepository(jdbcTemplate);
        this.actionsWithPassController = new ActionsWithPassController();
    }

    @FXML
    void initialize(Stage stageCreateStudent, Image image) {
        createStudentButton.setDisable(true);
        //createPassButton.setOnAction(event -> openWindowCreatePass(image));
        backButton.setOnAction(event -> stageCreateStudent.close());
    }

    @FXML
    public void createStudent() {
        correctInputPhoneLine();
        FillingFieldsHelper.correctInputStringLine(newFirstNameValue);
        FillingFieldsHelper.correctInputStringLine(newNameValue);
        FillingFieldsHelper.correctInputStringLine(newPatronymicValue);

        newPhoneNumberValue.textProperty().bindBidirectional(newPhoneNumberValueProperty, new DefaultStringConverter());
        newPhoneNumberValueProperty.addListener((observable, oldValue, newValue) -> {
            if (FillingFieldsHelper.isPhoneNumber(newPhoneNumberValue.getText()) && !newNameValue.getText().isBlank()) {
                createStudentButton.setDisable(false);

            }
        });
    }

    @FXML
    public void createStudentInDB(Image image) {
        String phoneNumberForDB = newPhoneNumberValue.getText();
        String firstNameForDB = newFirstNameValue.getText();
        String nameForDB = newNameValue.getText();
        String patronymicForDB = newPatronymicValue.getText();

        Visitors visitor = new Visitors();
        visitor.setTelephoneNum(phoneNumberForDB);
        visitor.setName(nameForDB);
        if (firstNameForDB != null) {
            visitor.setSurname(firstNameForDB);
        }
        if (patronymicForDB != null) {
            visitor.setPatronymic(patronymicForDB);
        }
        boolean isSuccess = visitorsRepository.create(visitor);

        if (isSuccess) {
            String message = "Карточка студента успешно внесена в базу данных";
            new GetCommonWindowHelper().openWindowSuccess(image, message);
        } else {
            //new GetCommonWindowHelper().openWindowUnSuccess(image);
        }
    }

    @FXML
    public void openWindowCreatePass(Image image, String phoneNumber) {
        actionsWithPassController.openWindowCreatePass(image, phoneNumber);
    }



    private void correctInputPhoneLine() {
        int maxCharacters = 11;
        newPhoneNumberValue.textProperty().addListener((observable10, oldValue10, newValue10) -> {
            if (newValue10.length() > maxCharacters) newPhoneNumberValue.deleteNextChar();
            if (!p.matcher(newValue10).matches()) newPhoneNumberValue.setText(oldValue10);
        });
    }
}
