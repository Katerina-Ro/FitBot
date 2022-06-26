package com.example.demo.ui;

import com.example.demo.config.DBConfig;
import com.example.demo.dao.Pass;
import com.example.demo.dao.repositories.IPassRepository;
import com.example.demo.dao.repositories.impl.PassRepository;
import com.example.demo.util.FillingFieldsHelper;
import com.example.demo.util.GetCommonWindowHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicBoolean;

public class CreatePassController {
    private final IPassRepository passRepository;
    private AtomicBoolean canBeActivated = new AtomicBoolean(false);
    private AtomicBoolean isDateStarts = new AtomicBoolean(false);
    private AtomicBoolean isCountVisits = new AtomicBoolean(false);
    private AtomicBoolean isDateEnd = new AtomicBoolean(false);

    @FXML
    private Button backButton;
    @FXML
    private Button createPassButton;
    @FXML
    private Button createVisitToThisPassButton;

    @FXML
    private Label countFreezeColumn;

    @FXML
    private TextField createCountFreeze;

    @FXML
    private TextField createCountVisits;

    @FXML
    private TextField createDateEndPass;

    @FXML
    private TextField createDateStartFreeze;

    @FXML
    private TextField createDateStartPass;



    @FXML
    private TextField createPhoneNumber;



    @FXML
    private Label dateEndColumn;

    @FXML
    private Label dateStartColumn;

    @FXML
    private Label dateStartFreezeColumn;

    @FXML
    private Label phoneNumber;

    @FXML
    private Label visitLimitColumn;

    public CreatePassController() {
        NamedParameterJdbcTemplate jdbcTemplate = new DBConfig().namedParameterJdbcTemplate();
        this.passRepository = new PassRepository(jdbcTemplate);
    }

    @FXML
    void initialize(Stage stageCreatePass, Image image, String phoneNumber) {
        FillingFieldsHelper.correctInputPhoneLine(createPhoneNumber);
        FillingFieldsHelper.correctInputDateLine(createDateStartPass);
        FillingFieldsHelper.correctInputIntegerLine(createCountVisits);
        FillingFieldsHelper.correctInputDateLine(createDateEndPass);
        FillingFieldsHelper.correctInputIntegerLine(createCountFreeze);
        FillingFieldsHelper.correctInputDateLine(createDateStartFreeze);

        observableFields(image);
        backButton.setOnAction(event -> stageCreatePass.close());
    }

    @FXML
    public void observableFields(Image image) {
        createPassButton.setDisable(true);
        createPhoneNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            String newPhoneNumberValueArray = createPhoneNumber.getText();
            if (newPhoneNumberValueArray.length() == 11) {
                if (FillingFieldsHelper.isPhoneNumber(newPhoneNumberValueArray)) {
                    createDateStartPass.textProperty().addListener((observable1, oldValue1, newValue1) -> {
                        System.out.println("сейчас будет isDate");
                        if (FillingFieldsHelper.isDate(createDateStartPass)) {
                            System.out.println("внутри if (FillingFieldsHelper.isDate(createDateStartPass))");
                            isDateStarts.set(true);
                        } else {
                            System.out.println("isDateStarts.set(false);");
                            isDateStarts.set(false);
                        }
                    });

                    createCountVisits.textProperty().addListener((observable2, oldValue2, newValue2) -> {
                        if (FillingFieldsHelper.isNumbers(createCountVisits.getText())) {
                            System.out.println("введенная цифра - это кол-во занятий + isCountVisits.set(true);");
                            isCountVisits.set(true);
                        } else {
                            System.out.println("введенная цифра - это кол-во занятий + isCountVisits.set(false);");
                            isCountVisits.set(false);
                        }
                    });

                    createDateEndPass.textProperty().addListener((observable3, oldValue3, newValue3) -> {
                        isDateEnd.set(FillingFieldsHelper.isDate(createDateEndPass));
                    });

                    System.out.println("isDateStarts после = " + isDateStarts);
                    System.out.println("isCountVisits = " + isCountVisits);
                    System.out.println("isDateEnd = " + isDateEnd);

                    if (Boolean.TRUE.equals(isDateStarts.get()) && Boolean.TRUE.equals(isCountVisits.get()
                            && Boolean.TRUE.equals(isDateEnd.get()))) {
                        System.out.println("canBeActivated.set(true)");

                        createPassButton.setDisable(false);
                        createPassButton.setOnAction(event -> createPassInDB(image));
                    }
                } else {
                    createPassButton.setDisable(true);
                }
            } else {
                createPassButton.setDisable(true);
            }
        });
    }

    @FXML
    public void createPassInDB(Image image) {
        String phoneNumberForDB = createPhoneNumber.getText();
        LocalDate dateStartPassForDB = LocalDate.parse(createDateStartPass.getText());
        Integer countVisitsForDB = Integer.valueOf(createCountVisits.getText());
        LocalDate dateEndPassForDB = LocalDate.parse(createDateEndPass.getText());
        Integer countFreezeForDB = Integer.valueOf(createCountFreeze.getText());
        LocalDate dateStartFreezeForDB = LocalDate.parse(createDateStartFreeze.getText());

        System.out.println(phoneNumberForDB);
        System.out.println(dateStartPassForDB);
        System.out.println(countVisitsForDB);
        System.out.println(dateEndPassForDB);
        System.out.println(countFreezeForDB);
        System.out.println(dateStartFreezeForDB);

        Pass pass = new Pass();
        pass.setPhoneNumber(phoneNumberForDB);
        pass.setDateStart(dateStartPassForDB);
        pass.setVisitLimit(countVisitsForDB);
        pass.setDateEnd(dateEndPassForDB);
        pass.setFreezeLimit(countFreezeForDB);
        pass.setDateStartFreeze(dateStartFreezeForDB);

        boolean isExist = passRepository.findPassByPhone(phoneNumberForDB).isPresent();
        if (!isExist) {
            boolean isSuccess = passRepository.createPass(pass);
            if (isSuccess) {
                String message = "Абонемент успешно внесен в базу данных";
                new GetCommonWindowHelper().openWindowSuccess(image, message);
            } else {
                new GetCommonWindowHelper().openWindowUnSuccess(image, event -> createPassInDB(image));
            }
        } else {
            openWindowExistPass(image, createPhoneNumber);
        }
    }

    @FXML
    public void openWindowExistPass(Image image, TextField phoneNumber) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/existPass-view.fxml"));
        Parent root1;
        try {
            root1 = fxmlLoader.load();
            Stage stageExistPass = new Stage();
            stageExistPass.setResizable(false);
            stageExistPass.getIcons().add(image);
            stageExistPass.setTitle("Абонемент с такими данными есть в базе данных");
            stageExistPass.setScene(new Scene(root1, 383, 158));

            ExistPassController existPassController = fxmlLoader.getController();
            existPassController.initialize(stageExistPass, image, phoneNumber);

            stageExistPass.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
