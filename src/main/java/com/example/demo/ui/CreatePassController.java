package com.example.demo.ui;

import com.example.demo.config.DBConfig;
import com.example.demo.dao.Pass;
import com.example.demo.dao.repositories.IPassRepository;
import com.example.demo.dao.repositories.impl.PassRepository;
import com.example.demo.observableModels.ObservablePassCreateFields;
import com.example.demo.util.FillingFieldsHelper;
import com.example.demo.util.GetCommonWindowHelper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
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
import java.text.ParseException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class CreatePassController {
    private final FillingFieldsHelper fillingFieldsHelper;
    private final IPassRepository passRepository;
    private ObservableMap<String, Boolean> observableList = FXCollections.observableMap(new HashMap<>());
    private ObservableMap<String, Boolean> observableList2 = FXCollections.observableMap(new HashMap<>());
    private ObservablePassCreateFields observablePassCreateFields = new ObservablePassCreateFields();
    private ObservableList<Boolean> activation = FXCollections.emptyObservableList();
    private AtomicBoolean isDateStarts = new AtomicBoolean(false);
    private AtomicBoolean isCountVisits = new AtomicBoolean(false);
    private AtomicBoolean isDateEnd = new AtomicBoolean(false);
    private AtomicBoolean isPhoneNumber = new AtomicBoolean(false);
    private AtomicReference<String> phoneNumberForDB = new AtomicReference<>();
    private AtomicReference<LocalDate> dateStartForDB = new AtomicReference<>();
    private AtomicInteger limitVisitsForDB = new AtomicInteger();
    private AtomicReference<LocalDate> dateEndForDB = new AtomicReference<>();
    private AtomicInteger countFreezeForDB = new AtomicInteger();
    private AtomicReference<LocalDate> dateStartFreezeForDB = new AtomicReference<>();

    @FXML
    private Button backButton;
    @FXML
    private Button createPassButton;

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
    private final StringProperty createPhoneNumberProperty = new SimpleStringProperty("");

    public CreatePassController() {
        NamedParameterJdbcTemplate jdbcTemplate = new DBConfig().namedParameterJdbcTemplate();
        this.fillingFieldsHelper = new FillingFieldsHelper();
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

        createPassButton.setDisable(true);
        observableCreatePhoneNumber(image);
        backButton.setOnAction(event -> stageCreatePass.close());
    }

    public void observableCreatePhoneNumber(Image image) {
        createPhoneNumber.textProperty().addListener((observable2, oldValue2, newValue2) -> {
            if (createPhoneNumber.getText().length() == 11) {
                System.out.println("createPhoneNumber.getText().length() == 11");
                if (FillingFieldsHelper.isPhoneNumber(createPhoneNumber.getText())) {
                    System.out.println("FillingFieldsHelper.isPhoneNumber(createPhoneNumber.getText()");
                    createDateStartPass.textProperty().addListener((observable22, oldValue22, newValue22) -> {
                        if (FillingFieldsHelper.isDate(createDateStartPass)) {
                            System.out.println("FillingFieldsHelper.isDate(createDateStartPass)");
                            createCountVisits.textProperty().addListener((observable23, oldValue23, newValue23) -> {
                                if (FillingFieldsHelper.isNumbers(createCountVisits.getText())) {
                                    System.out.println("FillingFieldsHelper.isNumbers(createCountVisits.getText())");
                                    createDateEndPass.textProperty().addListener((observable24, oldValue24, newValue24) -> {
                                        if (FillingFieldsHelper.isDate(createDateEndPass)) {
                                            System.out.println("FillingFieldsHelper.isDate(createDateEndPass)");
                                            createPassButton.setDisable(false);
                                            phoneNumberForDB.set(newValue2);
                                            try {
                                                dateStartForDB.set(fillingFieldsHelper.convertFormatLocalDate(newValue22));
                                            } catch (ParseException e) {
                                                new GetCommonWindowHelper().openWindowUnSuccess(image, event -> observableCreatePhoneNumber(image));
                                            }
                                            limitVisitsForDB.set(Integer.parseInt(newValue23));
                                            try {
                                                dateEndForDB.set(fillingFieldsHelper.convertFormatLocalDate(newValue24));
                                            } catch (ParseException e) {
                                                new GetCommonWindowHelper().openWindowUnSuccess(image, event -> observableCreatePhoneNumber(image));
                                            }
                                            createCountFreeze.textProperty().addListener((observable28, oldValue28, newValue28) -> {
                                                if (createCountFreeze != null && createCountFreeze.getText() != null
                                                        && FillingFieldsHelper.isNumbers(createCountVisits.getText()))  {
                                                    createDateStartFreeze.textProperty().addListener((observable26, oldValue26, newValue26) -> {
                                                        if (createDateStartFreeze != null && createDateStartPass.getText() != null
                                                                && FillingFieldsHelper.isDate(createDateStartFreeze)) {
                                                                    countFreezeForDB.set(Integer.parseInt(newValue28));
                                                            try {
                                                                dateStartFreezeForDB.set(fillingFieldsHelper.convertFormatLocalDate(newValue26));
                                                            } catch (ParseException e) {
                                                                new GetCommonWindowHelper().openWindowUnSuccess(image, event -> observableCreatePhoneNumber(image));
                                                            }
                                                        } else {
                                                            countFreezeForDB.set(0);
                                                            dateStartFreezeForDB.set(null);
                                                        }
                                                    });
                                                } else {
                                                    countFreezeForDB.set(0);
                                                    dateStartFreezeForDB.set(null);
                                                }
                                            });
                                            createPassButton.setOnAction(event -> createPassInDB(image));
                                        } else {
                                            System.out.println("1");
                                            createPassButton.setDisable(true);
                                        }
                                    });
                                } else {
                                    System.out.println("2");
                                    createPassButton.setDisable(true);
                                }
                            });
                        } else {
                            System.out.println("3");
                            createPassButton.setDisable(true);
                        }
                    });
                } else {
                    System.out.println("4");
                    createPassButton.setDisable(true);
                }
            } else {
                System.out.println("5");
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
