package com.example.demo.ui;

import com.example.demo.dao.Visits;
import com.example.demo.util.FillingFieldsHelper;
import com.example.demo.util.GetCommonWindowHelper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;

import java.io.IOException;
import java.time.LocalDate;

public class GetVisitsController {
    private final FillingFieldsHelper fillingFieldsHelper;
    private ObservableList<Visits> visitsObservableList;

    @FXML
    private Button backButton;

    @FXML
    private TextField inputPhoneNumber;
    private final StringProperty phoneNumberProperty = new SimpleStringProperty("");
    @FXML
    private TableColumn<Visits, Button> changeVisitButton;
    @FXML
    private TableColumn<Visits, Button> deleteVisitButton;
    @FXML
    private TableColumn<Visits, Integer> countVisitsInThisDayColumn;
    @FXML
    private TableColumn<Visits, LocalDate> dateVisitColumn;
    @FXML
    private TableColumn<Visits, Integer> passNumberColumn;
    @FXML
    private TableView<Visits> tableVisits;

    public GetVisitsController() {
        this.fillingFieldsHelper = new FillingFieldsHelper();
    }


    @FXML
    void initialize(Stage stageGetVisits, Image image) {
        observeInputPhoneNumber(image);
        backButton.setOnAction(event -> stageGetVisits.close());
    }

    private void observeInputPhoneNumber(Image image) {
        FillingFieldsHelper.correctInputPhoneLine(inputPhoneNumber);
        inputPhoneNumber.textProperty().bindBidirectional(phoneNumberProperty, new DefaultStringConverter());
        phoneNumberProperty.addListener((observable, oldValue, newValue) -> {
            if (phoneNumberProperty.length().get() == 11) {
                visitsObservableList = fillingFieldsHelper.getAllVisits(phoneNumberProperty);
                if (!visitsObservableList.isEmpty()) {
                    initDataToTable(image);
                } else {
                    fillVisitsIfEmpty();
                }
            } else {
                fillVisitsIfEmpty();
            }
        });
    }

    private void initDataToTable(Image image) {
        passNumberColumn.setCellValueFactory(new PropertyValueFactory<>("pass"));
        dateVisitColumn.setCellValueFactory(new PropertyValueFactory<>("dateVisit"));
        countVisitsInThisDayColumn.setCellValueFactory(new PropertyValueFactory<>("countVisit"));
        changeVisitButton.setCellValueFactory(new PropertyValueFactory<>("changeVisitButton"));
        //System.out.println("changeVisitButton.getColumns() = " + changeVisitButton.get);
        System.out.println();
        deleteVisitButton.setCellValueFactory(new PropertyValueFactory<>("deleteVisitButton"));

        tableVisits.setItems(visitsObservableList);

        Visits visit = new Visits();
        visit.setPass(visitsObservableList.get(0).getPass());
        visit.setDateVisit(visitsObservableList.get(0).getDateVisit());
        visit.setCountVisit(visitsObservableList.get(0).getCountVisit());
    }

    private void fillVisitsIfEmpty() {
        passNumberColumn.setCellValueFactory(null);
        dateVisitColumn.setCellValueFactory(null);
        countVisitsInThisDayColumn.setCellValueFactory(null);
    }

    /*
    private void openWindowChangeVisits(Image image, Integer pass, LocalDate dateVisit, Integer countVisit) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/changeVisits-view.fxml"));
        Parent root1;
        try {
            root1 = fxmlLoader.load();
            Stage stageChangeVisit = new Stage();
            stageChangeVisit.setResizable(false);
            stageChangeVisit.getIcons().add(image);
            stageChangeVisit.setTitle("Внесение изменений в посещение студента");
            stageChangeVisit.setScene(new Scene(root1, 700, 438));

            ChangeVisitsController changeVisitsController = fxmlLoader.getController();
            changeVisitsController.initialize(stageChangeVisit, image);

            stageChangeVisit.show();
        } catch (IOException e) {
            String message = "Произошла ошибка во время открытия окна. Обратитесь к разработчику";
            new GetCommonWindowHelper().openWindowUnSuccess(image, event -> openWindowChangeVisits(image,
                    visit, phone), message);
        }
    } */

    private void openWindowDeleteVisit(Image image, Visits visit, String phone) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/deleteVisit-view.fxml"));
        Parent root1;
        try {
            root1 = fxmlLoader.load();
            Stage stageDeleteVisit = new Stage();
            stageDeleteVisit.setResizable(false);
            stageDeleteVisit.getIcons().add(image);
            stageDeleteVisit.setTitle("Удаление посещения студента из базы данных");
            stageDeleteVisit.setScene(new Scene(root1, 504, 315));

            DeleteVisitController deleteVisitController = fxmlLoader.getController();
            deleteVisitController.initialize(stageDeleteVisit, image);

            stageDeleteVisit.show();
        } catch (IOException e) {
            String message = "Произошла ошибка во время открытия окна. Обратитесь к разработчику";
            new GetCommonWindowHelper().openWindowUnSuccess(image, event -> openWindowDeleteVisit(image,
                    visit, phone), message);
        }
    }
}
