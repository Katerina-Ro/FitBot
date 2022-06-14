package com.example.demo.ui;

import com.example.demo.config.DBConfig;
import com.example.demo.dao.Pass;
import com.example.demo.dao.Visitors;
import com.example.demo.dao.repositories.IVisitorsRepository;
import com.example.demo.dao.repositories.impl.VisitorsRepository;
import com.example.demo.observableModels.ObservableVisitor;
import com.example.demo.util.FillingFieldsHelper;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.LocalDateStringConverter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class GetInfoStudentController {
    private final ActionsWithStudentController actionsWithStudentController;
    private final ActionsWithPassController actionsWithPassController;
    private final IVisitorsRepository visitorsRepository;
    private final FillingFieldsHelper fillingFieldsHelper;
    private ObservableList<Visitors> visitorsObservableList;
    private ObservableList<ObservableVisitor> observableVisitor;
    private ObservableList<Boolean> existPassObservableList;
    private ObservableList<Boolean> passIsFreezeObservableList;
    private ObservableList<LocalDate> dateStartFreezeObservableList;

    @FXML
    private Button backButton;
    @FXML
    private Button changeStudentButton;
    @FXML
    private Button getInfoPassButton;
    @FXML
    private Label dateStartFreezeValue;
    private final ObjectProperty<LocalDate> dateStartFreezeValueProperty = new SimpleObjectProperty<>();
    @FXML
    private Label existActualPassDB;
    private final StringProperty existActualPassDBProperty = new SimpleStringProperty("");
    @FXML
    private Label firstNameDB;
    private final StringProperty firstNameDBProperty = new SimpleStringProperty("");
    @FXML
    private TextField inputPhoneNumber;
    private final StringProperty phoneNumberProperty = new SimpleStringProperty("");
    @FXML
    private Label nameDB;
    private final StringProperty nameDBProperty = new SimpleStringProperty("");
    @FXML
    private Label passIsFreezeDB;
    private final StringProperty passIsFreezeDBProperty = new SimpleStringProperty("");
    @FXML
    private Label patronymicDB;
    private final StringProperty patronymicDBProperty = new SimpleStringProperty("");

    public GetInfoStudentController() {
        NamedParameterJdbcTemplate jdbcTemplate = new DBConfig().namedParameterJdbcTemplate();
        this.actionsWithPassController = new ActionsWithPassController();
        this.actionsWithStudentController = new ActionsWithStudentController();
        this.visitorsRepository = new VisitorsRepository(jdbcTemplate);
        this.fillingFieldsHelper = new FillingFieldsHelper();
    }

    @FXML
    void initialize(Stage stageGetInfoStudent, Image image, TextField phoneNumber) {
        System.out.println("TextField phoneNumber = " + phoneNumber);
        System.out.println("phoneNumber.getText() = " + phoneNumber.getText());
        if (phoneNumber != null && FillingFieldsHelper.isPhoneNumber(phoneNumber.getText())) inputPhoneNumber.setText(phoneNumber.getText());
        System.out.println("inputPhoneNumber.getText() = " + inputPhoneNumber.getText());
        FillingFieldsHelper.correctInputPhoneLine(inputPhoneNumber);
        inputPhoneNumber.textProperty().bindBidirectional(phoneNumberProperty, new DefaultStringConverter());
        phoneNumberProperty.addListener((observable, oldValue, newValue) -> {
            if (phoneNumberProperty.length().get() == 11) {
                Optional<Visitors> visitor = visitorsRepository.findVisitorByPhoneNumber(phoneNumberProperty.get());
                Optional<List<Pass>> pass = fillingFieldsHelper.findVisitorByPhoneNumber(inputPhoneNumber.getText());
                if (visitor.isPresent()) {
                    ObservableVisitor visitorView = new ObservableVisitor();
                    visitorView.setPatronymic(visitor.get().getPatronymic());
                    visitorView.setSurname(visitor.get().getSurname());
                    visitorView.setName(visitor.get().getName());
                    if (pass.isPresent()) {
                        LocalDate dateStartFreeze = pass.get().get(0).getDateStartFreeze();
                        visitorView.setExistActualPass("Есть");
                        visitorView.setIsFreezePass(dateStartFreeze != null ? "Да" : "Нет");
                        if (dateStartFreeze != null) {
                            visitorView.setDateStartFreeze(dateStartFreeze);
                        }
                    } else {
                        visitorView.setExistActualPass("Нет");
                    }
                    observableVisitor = FXCollections.observableArrayList(visitorView);
                    if (!observableVisitor.isEmpty()) {
                        fillGetInfoStudent();
                    }
                } else {
                    observableVisitor = FXCollections.emptyObservableList();
                }
            }
        });
        backButton.setOnAction(event -> stageGetInfoStudent.close());
        changeStudentButton.setOnAction(event -> actionsWithStudentController.openWindowChangeStudent(image, phoneNumber));
        getInfoPassButton.setOnAction(event -> actionsWithPassController.openWindowGetInfoPass(image, phoneNumber));
    }

    private void fillGetInfoStudent() {
        for (ObservableVisitor ov: observableVisitor) {
            // Заполняем поле Фамилия
            firstNameDBProperty.setValue(ov.getSurname());
            firstNameDBProperty.addListener((observable1, oldValue1, newValue1) -> {
                firstNameDBProperty.setValue(newValue1);
            });
            firstNameDB.textProperty().bindBidirectional(firstNameDBProperty, new DefaultStringConverter());

            // Заполняем поле Имя
            nameDBProperty.setValue(ov.getName());
            nameDBProperty.addListener((observable2, oldValue2, newValue2) -> {
                nameDBProperty.setValue(newValue2);
            });
            nameDB.textProperty().bindBidirectional(nameDBProperty, new DefaultStringConverter());

            // Заполняем поле Отчество
            patronymicDBProperty.setValue(ov.getPatronymic());
            patronymicDBProperty.addListener((observable3, oldValue3, newValue3) -> {
                patronymicDBProperty.setValue(newValue3);
            });
            patronymicDB.textProperty().bindBidirectional(patronymicDBProperty, new DefaultStringConverter());

            // Заполняем поле Есть действующий абонмент?
            existActualPassDBProperty.setValue(ov.getExistActualPass());
            existActualPassDBProperty.addListener((observable4, oldValue4, newValue4) -> {
                existActualPassDBProperty.setValue(newValue4);
            });
            existActualPassDB.textProperty().bindBidirectional(existActualPassDBProperty, new DefaultStringConverter());

            // Заполняем поле Абонемент заморожен?
            passIsFreezeDBProperty.setValue(ov.getIsFreezePass());
            passIsFreezeDBProperty.addListener((observable5, oldValue5, newValue5) -> {
                passIsFreezeDBProperty.setValue(newValue5);
            });
            passIsFreezeDB.textProperty().bindBidirectional(passIsFreezeDBProperty, new DefaultStringConverter());

            // Заполняем поле Дата начала заморозки
            dateStartFreezeValueProperty.setValue(ov.getDateStartFreeze());
            dateStartFreezeValueProperty.addListener((observable6, oldValue6, newValue6) -> {
                dateStartFreezeValueProperty.setValue(newValue6);
            });
            dateStartFreezeValue.textProperty().bindBidirectional(dateStartFreezeValueProperty, new LocalDateStringConverter());
        }
    }
}
