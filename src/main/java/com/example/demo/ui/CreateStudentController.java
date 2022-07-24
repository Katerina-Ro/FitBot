package com.example.demo.ui;

import com.example.demo.config.DBConfig;
import com.example.demo.dao.Visitors;
import com.example.demo.dao.repositories.IVisitorsRepository;
import com.example.demo.dao.repositories.impl.VisitorsRepository;
import com.example.demo.util.FillingFieldsHelper;
import com.example.demo.util.GetCommonWindowHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.io.IOException;

public class CreateStudentController {
    private final IVisitorsRepository visitorsRepository;

    @FXML
    private Button backButton;
    @FXML
    private Button createPassButton;
    @FXML
    private Button createStudentButton;

    @FXML
    private TextField newFirstNameValue;
    @FXML
    private TextField newNameValue;
    @FXML
    private TextField newPatronymicValue;
    @FXML
    private TextField newPhoneNumberValue;

    public CreateStudentController() {
        NamedParameterJdbcTemplate jdbcTemplate = new DBConfig().namedParameterJdbcTemplate();
        this.visitorsRepository = new VisitorsRepository(jdbcTemplate);
    }

    @FXML
    void initialize(Stage stageCreateStudent, Image image) {
        FillingFieldsHelper.correctInputPhoneLine(newPhoneNumberValue);
        FillingFieldsHelper.correctInputStringLine(newFirstNameValue);
        FillingFieldsHelper.correctInputStringLine(newNameValue);
        FillingFieldsHelper.correctInputStringLine(newPatronymicValue);
        observableFields(image);
        backButton.setOnAction(event -> stageCreateStudent.close());
    }

    private void observableFields(Image image) {
        createStudentButton.setDisable(true);
        createPassButton.setDisable(true);
        newPhoneNumberValue.textProperty().addListener((observable, oldValue, newValue) -> {
            String newPhoneNumberValueArray = newPhoneNumberValue.getText();
            if (newPhoneNumberValueArray.length() == 11) {
                if (FillingFieldsHelper.isPhoneNumber(newPhoneNumberValueArray)) {
                    newNameValue.textProperty().addListener((observable1, oldValue1, newValue1) -> {
                        String newNameValueArray = newNameValue.getText();
                        if (newNameValueArray.length() > 0) {
                            if (!newNameValueArray.isBlank()) {
                                createStudentButton.setDisable(false);
                                createStudentButton.setOnAction(event -> createStudentInDB(image));
                            }
                        } else {
                            createStudentButton.setDisable(true);
                        }
                    });
                   createPassButton.setDisable(false);
                   createPassButton.setOnAction(event -> openWindowCreatePass(image, newPhoneNumberValueArray));
                }
            } else {
                createStudentButton.setDisable(true);
                createPassButton.setDisable(true);
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
        visitor.setSurname(firstNameForDB);
        visitor.setPatronymic(patronymicForDB);
        boolean isExist = visitorsRepository.findVisitorByPhoneNumber(phoneNumberForDB).isPresent();
        if (!isExist) {
            boolean isSuccess = visitorsRepository.create(visitor);
            if (isSuccess) {
                String message = "Карточка студента успешно внесена в базу данных";
                new GetCommonWindowHelper().openWindowSuccess(image, message);
            } else {
                String message = "Произошла ошибка во время записи в базу данных. Обратитесь к разработчику";
                new GetCommonWindowHelper().openWindowUnSuccess(image, event -> createStudentInDB(image), message);
            }
        } else {
            openWindowSeveral(image,newPhoneNumberValue);
        }
    }

    @FXML
    public void openWindowCreatePass(Image image, String phoneNumber) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/createPass-view.fxml"));
        Parent root1;
        try {
            root1 = fxmlLoader.load();
            Stage stageCreatePassWindow = new Stage();
            stageCreatePassWindow.setResizable(false);
            stageCreatePassWindow.getIcons().add(image);
            stageCreatePassWindow.setTitle("Внесение абонемента в базу данных");
            stageCreatePassWindow.setScene(new Scene(root1, 700, 535));

            CreatePassController createPassController = fxmlLoader.getController();
            createPassController.initialize(stageCreatePassWindow, image);

            stageCreatePassWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openWindowSeveral(Image image, TextField phoneNumber) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/several-view.fxml"));
        Parent root1;
        try {
            root1 = fxmlLoader.load();
            Stage stageSeveralWindow = new Stage();
            stageSeveralWindow.setResizable(false);
            stageSeveralWindow.getIcons().add(image);
            stageSeveralWindow.setTitle("Карточка студента существует в базе данных");
            stageSeveralWindow.setScene(new Scene(root1, 374, 133));

            SeveralController severalController = fxmlLoader.getController();
            severalController.initialize(stageSeveralWindow, image, phoneNumber);

            stageSeveralWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
