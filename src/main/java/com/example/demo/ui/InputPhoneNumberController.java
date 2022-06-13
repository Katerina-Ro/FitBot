package com.example.demo.ui;

import com.example.demo.config.DBConfig;
import com.example.demo.dao.Pass;
import com.example.demo.dao.Visitors;
import com.example.demo.dao.repositories.IVisitorsRepository;
import com.example.demo.dao.repositories.impl.VisitorsRepository;
import com.example.demo.exception.SeveralException;
import com.example.demo.util.FillingFieldsHelper;
import com.example.demo.util.PatternTemplate;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.LocalDateStringConverter;
import javafx.util.converter.NumberStringConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Pattern;

public class InputPhoneNumberController {
    private final IVisitorsRepository visitorsRepository;
    private final FillingFieldsHelper fillingFieldsHelper;
    private ObservableList<Pass> passObservableList;
    private ObservableList<String> visitorsObservableList;
    Pattern p = Pattern.compile(PatternTemplate.INTEGER_LINE.getTemplate());

    @FXML
    private TextField inputPhoneNumber;
    private final StringProperty phoneNumberProperty = new SimpleStringProperty("");

    @FXML
    private Label passId;
    @FXML
    private Label passIdValue;
    private final IntegerProperty passIdValueProperty = new SimpleIntegerProperty();

    @FXML
    private Label fullName;
    @FXML
    private Label fullNameValue;
    private final StringProperty fullNameValueProperty = new SimpleStringProperty("");

    @FXML
    private Label dateStart;
    @FXML
    private Label dateStartValue;
    private final ObjectProperty<LocalDate> dateStartValueProperty = new SimpleObjectProperty<>();

    @FXML
    private Label dateEnd;
    @FXML
    private Label dateEndValue;
    private final ObjectProperty<LocalDate> dateEndValueProperty = new SimpleObjectProperty<>();

    @FXML
    private Label dateStartFreeze;
    @FXML
    private Label dateStartFreezeValue;
    private final ObjectProperty<LocalDate> dateStartFreezeValueProperty = new SimpleObjectProperty<>();

    @FXML
    private Label countFreeze;
    @FXML
    private Label countFreezeValue;
    private final IntegerProperty countFreezeValueProperty = new SimpleIntegerProperty();

    @FXML
    private Label visitLimit;
    @FXML
    private Label visitLimitValue;
    private final IntegerProperty visitLimitValueProperty = new SimpleIntegerProperty();


    @FXML
    private Button backButton;

    @FXML
    private Button changePassButton;


    public InputPhoneNumberController() {
        this.fillingFieldsHelper = new FillingFieldsHelper();
        this.visitorsRepository = new VisitorsRepository(new DBConfig().namedParameterJdbcTemplate());
    }

    @FXML
    public void initialize(Stage stage, Image image) {
        // Введенный номер телефона
        correctInputPhoneLine();
        inputPhoneNumber.textProperty().bindBidirectional(phoneNumberProperty, new DefaultStringConverter());
        phoneNumberProperty.addListener((observable, oldValue, newValue) -> {
            if (phoneNumberProperty.length().get() == 11) {
                // В случае изменения введенного номера телефона, меняются данные полей. Устанавливаем функцию наблюдения
                passObservableList = fillingFieldsHelper.getTablePass(phoneNumberProperty);
                visitorsObservableList = getFullNameStudent(phoneNumberProperty, image);
                if (!passObservableList.isEmpty() && !visitorsObservableList.isEmpty()) {
                    fillStageIfPassObservableListNotEmpty();
                } else {
                    fillStageIfPassObservableListIsEmpty();
                }
            } else {
                fillStageIfPassObservableListIsEmpty();
            }
        });
        backButton.setOnAction(event -> stage.close());
    }

    private ObservableList<String> getFullNameStudent(StringProperty phoneNumber, Image image) {
        String inputPhoneNumber = String.valueOf(phoneNumber.get());
        Optional<Visitors> visitor = Optional.empty();
        try {
            visitor = visitorsRepository.findVisitorByPhoneNumber(inputPhoneNumber);
        } catch (SeveralException e) {
            openWindowWhichPass(String.valueOf(phoneNumber), image);
        }
        ObservableList<String> fullNameFromDB = FXCollections.emptyObservableList();
        StringBuilder sb = new StringBuilder();
        if (visitor.isPresent()) {
            String surname = visitor.get().getSurname();
            String name = visitor.get().getName();
            String patronymic = visitor.get().getPatronymic();

            if (surname != null && !surname.isBlank()) {
                sb.append(surname);
                if (name != null && !name.isBlank()) {
                    sb.append(" ").append(name);
                    if (patronymic != null && !patronymic.isBlank()) {
                        sb.append(" ").append(patronymic);
                    }
                }
                fullNameFromDB = FXCollections.observableArrayList(sb.toString());
            }
        }
        return fullNameFromDB;
    }

    public void openWindowWhichPass(String phoneNumber, Image image) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/whichPass-view.fxml"));
        Parent root1;
        try {
            root1 = fxmlLoader.load();
            Stage stage1 = new Stage();
            stage1.setResizable(false);
            stage1.getIcons().add(image);
            stage1.setTitle("Выберите абонемент");
            stage1.setScene(new Scene(root1, 700, 500));

            WhichPassController whichPassController = fxmlLoader.getController();
            whichPassController.initialize(phoneNumber);

            stage1.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void correctInputPhoneLine() {
        int maxCharacters = 11;
        inputPhoneNumber.textProperty().addListener((observable10, oldValue10, newValue10) -> {
            if (newValue10.length() > maxCharacters) inputPhoneNumber.deleteNextChar();
            if (!p.matcher(newValue10).matches()) inputPhoneNumber.setText(oldValue10);
        });
    }

    private void fillStageIfPassObservableListNotEmpty () {
        for (Pass p : passObservableList) {
            // Заполняем поле "Номер абонемента"
            passIdValueProperty.setValue(p.getNumPass());
            passIdValueProperty.addListener((observable1, oldValue1, newValue1) -> {
                passIdValueProperty.setValue(newValue1);
            });
            passIdValue.textProperty().bindBidirectional(passIdValueProperty, new NumberStringConverter());

            // Заполняем поле "Номер телефона"
            for (String s: visitorsObservableList) {
                fullNameValueProperty.setValue(s);
                fullNameValueProperty.addListener((observable2, oldValue2, newValue2) -> {
                    fullNameValueProperty.setValue(newValue2);
                    fullNameValue.textProperty().bindBidirectional(fullNameValueProperty, new DefaultStringConverter());
                });
            }

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
        }
    }

    private void fillStageIfPassObservableListIsEmpty () {
        // Заполняем поле "Номер абонемента"
        passIdValueProperty.setValue(null);
        passIdValueProperty.addListener((observable1, oldValue1, newValue1) -> {
            passIdValueProperty.setValue(newValue1);
        });
        passIdValue.textProperty().bindBidirectional(passIdValueProperty, new NumberStringConverter());

        // Заполняем поле "Номер телефона"
        phoneNumberProperty.setValue("");
        phoneNumberProperty.addListener((observable2, oldValue2, newValue2) -> {
            phoneNumberProperty.setValue(newValue2);
        });
        fullNameValue.textProperty().bindBidirectional(phoneNumberProperty, new DefaultStringConverter());

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
    }
}
