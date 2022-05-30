package com.example.demo.ui.abstractClasses;

import com.example.demo.util.FillingFieldsHelper;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public abstract class PassController {
    private final FillingFieldsHelper fillingFieldsHelper;

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

    protected PassController() {
        this.fillingFieldsHelper = new FillingFieldsHelper();
    }

    public FillingFieldsHelper getFillingFieldsHelper() {
        return fillingFieldsHelper;
    }

    public TextField getInputPhoneNumber() {
        return inputPhoneNumber;
    }

    public void setInputPhoneNumber(TextField inputPhoneNumber) {
        this.inputPhoneNumber = inputPhoneNumber;
    }

    public String getPhoneNumberProperty() {
        return phoneNumberProperty.get();
    }

    public StringProperty phoneNumberPropertyProperty() {
        return phoneNumberProperty;
    }

    public void setPhoneNumberProperty(String phoneNumberProperty) {
        this.phoneNumberProperty.set(phoneNumberProperty);
    }

    public Label getPassId() {
        return passId;
    }

    public void setPassId(Label passId) {
        this.passId = passId;
    }

    public Label getPassIdValue() {
        return passIdValue;
    }

    public void setPassIdValue(Label passIdValue) {
        this.passIdValue = passIdValue;
    }

    public int getPassIdValueProperty() {
        return passIdValueProperty.get();
    }

    public IntegerProperty passIdValuePropertyProperty() {
        return passIdValueProperty;
    }

    public void setPassIdValueProperty(int passIdValueProperty) {
        this.passIdValueProperty.set(passIdValueProperty);
    }

    public Label getFullName() {
        return fullName;
    }

    public void setFullName(Label fullName) {
        this.fullName = fullName;
    }

    public Label getFullNameValue() {
        return fullNameValue;
    }

    public void setFullNameValue(Label fullNameValue) {
        this.fullNameValue = fullNameValue;
    }

    public String getFullNameValueProperty() {
        return fullNameValueProperty.get();
    }

    public StringProperty fullNameValuePropertyProperty() {
        return fullNameValueProperty;
    }

    public void setFullNameValueProperty(String fullNameValueProperty) {
        this.fullNameValueProperty.set(fullNameValueProperty);
    }

    public Label getDateStart() {
        return dateStart;
    }

    public void setDateStart(Label dateStart) {
        this.dateStart = dateStart;
    }

    public Label getDateStartValue() {
        return dateStartValue;
    }

    public void setDateStartValue(Label dateStartValue) {
        this.dateStartValue = dateStartValue;
    }

    public LocalDate getDateStartValueProperty() {
        return dateStartValueProperty.get();
    }

    public ObjectProperty<LocalDate> dateStartValuePropertyProperty() {
        return dateStartValueProperty;
    }

    public void setDateStartValueProperty(LocalDate dateStartValueProperty) {
        this.dateStartValueProperty.set(dateStartValueProperty);
    }

    public Label getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Label dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Label getDateEndValue() {
        return dateEndValue;
    }

    public void setDateEndValue(Label dateEndValue) {
        this.dateEndValue = dateEndValue;
    }

    public LocalDate getDateEndValueProperty() {
        return dateEndValueProperty.get();
    }

    public ObjectProperty<LocalDate> dateEndValuePropertyProperty() {
        return dateEndValueProperty;
    }

    public void setDateEndValueProperty(LocalDate dateEndValueProperty) {
        this.dateEndValueProperty.set(dateEndValueProperty);
    }

    public Label getDateStartFreeze() {
        return dateStartFreeze;
    }

    public void setDateStartFreeze(Label dateStartFreeze) {
        this.dateStartFreeze = dateStartFreeze;
    }

    public Label getDateStartFreezeValue() {
        return dateStartFreezeValue;
    }

    public void setDateStartFreezeValue(Label dateStartFreezeValue) {
        this.dateStartFreezeValue = dateStartFreezeValue;
    }

    public LocalDate getDateStartFreezeValueProperty() {
        return dateStartFreezeValueProperty.get();
    }

    public ObjectProperty<LocalDate> dateStartFreezeValuePropertyProperty() {
        return dateStartFreezeValueProperty;
    }

    public void setDateStartFreezeValueProperty(LocalDate dateStartFreezeValueProperty) {
        this.dateStartFreezeValueProperty.set(dateStartFreezeValueProperty);
    }

    public Label getCountFreeze() {
        return countFreeze;
    }

    public void setCountFreeze(Label countFreeze) {
        this.countFreeze = countFreeze;
    }

    public Label getCountFreezeValue() {
        return countFreezeValue;
    }

    public void setCountFreezeValue(Label countFreezeValue) {
        this.countFreezeValue = countFreezeValue;
    }

    public int getCountFreezeValueProperty() {
        return countFreezeValueProperty.get();
    }

    public IntegerProperty countFreezeValuePropertyProperty() {
        return countFreezeValueProperty;
    }

    public void setCountFreezeValueProperty(int countFreezeValueProperty) {
        this.countFreezeValueProperty.set(countFreezeValueProperty);
    }

    public Label getVisitLimit() {
        return visitLimit;
    }

    public void setVisitLimit(Label visitLimit) {
        this.visitLimit = visitLimit;
    }

    public Label getVisitLimitValue() {
        return visitLimitValue;
    }

    public void setVisitLimitValue(Label visitLimitValue) {
        this.visitLimitValue = visitLimitValue;
    }

    public int getVisitLimitValueProperty() {
        return visitLimitValueProperty.get();
    }

    public IntegerProperty visitLimitValuePropertyProperty() {
        return visitLimitValueProperty;
    }

    public void setVisitLimitValueProperty(int visitLimitValueProperty) {
        this.visitLimitValueProperty.set(visitLimitValueProperty);
    }

    public Button getBackButton() {
        return backButton;
    }

    public void setBackButton(Button backButton) {
        this.backButton = backButton;
    }

    public Button getChangePassButton() {
        return changePassButton;
    }

    public void setChangePassButton(Button changePassButton) {
        this.changePassButton = changePassButton;
    }
}
