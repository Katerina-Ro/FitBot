package com.example.demo.ui;

import com.example.demo.config.DBConfig;
import com.example.demo.dao.Pass;
import com.example.demo.dao.repositories.IPassRepository;
import com.example.demo.dao.repositories.impl.PassRepository;
import com.example.demo.observableModels.ObservablePassCreateFields;
import com.example.demo.util.FillingFieldsHelper;
import com.example.demo.util.GetCommonWindowHelper;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
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
        observableFields(image);
        backButton.setOnAction(event -> stageCreatePass.close());
    }

    @FXML
    public void observableFields(Image image) {
        observableCreatePhoneNumber(image);
        observableCreateDateStartPass(image);
        observableCreateCountVisits(image);
        observableCreateDateEndPass(image);
        observableCreateCountFreeze();
        observableCreateDateStartFreeze(image);


    }

    private void observableCondition(Image image) {
        createPhoneNumber.textProperty().bindBidirectional();
        activation.addListener((observable2, oldValue2, newValue2) -> {
            if (observableList.size() == 4 && !observableList.containsValue(false)) {
                System.out.println("зашли вовнутрь");
                createPassButton.setDisable(false);
                createPassButton.setOnAction(event -> createPassInDB(image));
            } else {
                createPassButton.setDisable(true);
            }
        });
    }

    public void observableCreatePhoneNumber(Image image) {
        createPhoneNumber.textProperty().addListener((observable2, oldValue2, newValue2) -> {
            if (createPhoneNumber.getText().length() == 11) {
                if (FillingFieldsHelper.isPhoneNumber(createPhoneNumber.getText())) {
                    phoneNumberForDB.set(newValue2);
                    observableList.put("isPhoneNumber", true);
                } else {
                    phoneNumberForDB.set(null);
                    observableList.put("isPhoneNumber", false);
                }
            } else {
                phoneNumberForDB.set(null);
                observableList.put("isPhoneNumber", false);
            }
        });
    }

    private void observableCreateDateStartPass(Image image) {
        createDateStartPass.textProperty().addListener((observable3, oldValue3, newValue3) -> {
            if (FillingFieldsHelper.isDate(createDateStartPass)) {
                try {
                    dateStartForDB.set(fillingFieldsHelper.convertFormatLocalDate(newValue3));
                    observableList.put("isDateStart", true);
                } catch (ParseException e) {
                    dateStartForDB.set(null);
                    observableList.put("isDateStart", false);
                    new GetCommonWindowHelper().openWindowUnSuccess(image, event -> observableCreateDateStartPass(image));
                }
            } else if (createDateStartPass == null || createDateStartPass.getText().isBlank()) {
                dateStartForDB.set(null);
                observableList.put("isDateStart", false);
            } else {
                dateStartForDB.set(null);
                observableList.put("isDateStart", false);
            }
        });
    }

    private void observableCreateCountVisits(Image image) {
        createCountVisits.textProperty().addListener((observable4, oldValue4, newValue4) -> {
            if (FillingFieldsHelper.isNumbers(createCountVisits.getText())) {
                limitVisitsForDB.set(Integer.parseInt(newValue4));
                observableList.put("isCountVisits", true);
            } else if (createCountFreeze == null || createCountFreeze.getText().isBlank()) {
                limitVisitsForDB.set(0);
                observableList.put("isCountVisits", false);
            } else {
                limitVisitsForDB.set(0);
                observableList.put("isCountVisits", false);
            }
        });
    }

    private void observableCreateDateEndPass(Image image) {
        createDateEndPass.textProperty().addListener((observable5, oldValue5, newValue5) -> {
            if (FillingFieldsHelper.isDate(createDateEndPass)) {
                try {
                    dateEndForDB.set(fillingFieldsHelper.convertFormatLocalDate(newValue5));
                    observableList.put("isDateEnd", true);
                } catch (ParseException e) {
                    dateEndForDB.set(null);
                    observableList.put("isDateEnd", false);
                    new GetCommonWindowHelper().openWindowUnSuccess(image, event -> observableCreateDateStartPass(image));
                }
            } else if (createDateEndPass == null || createDateEndPass.getText().isBlank()) {
                dateEndForDB.set(null);
                observableList.put("isDateEnd", false);
            } else {
                dateEndForDB.set(null);
                observableList.put("isDateEnd", false);
            }
        });
    }

    private void observableCreateCountFreeze() {
        createCountFreeze.textProperty().addListener((observable6, oldValue6, newValue6) -> {
            if (FillingFieldsHelper.isNumbers(createCountFreeze.getText())) {
                countFreezeForDB.set(Integer.parseInt(createCountFreeze.getText()));
            } else {
                countFreezeForDB.set(0);
            }
        });
    }

    private void observableCreateDateStartFreeze(Image image) {
        createDateStartFreeze.textProperty().addListener((observable7, oldValue7, newValue7) -> {
            if (FillingFieldsHelper.isDate(createDateStartFreeze)) {
                try {
                    dateStartFreezeForDB.set(fillingFieldsHelper.convertFormatLocalDate(newValue7));
                } catch (ParseException e) {
                    e.printStackTrace();
                    dateStartFreezeForDB.set(null);
                    new GetCommonWindowHelper().openWindowUnSuccess(image, event -> observableCreateDateStartPass(image));
                }
            } else {
                dateStartFreezeForDB.set(null);
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
