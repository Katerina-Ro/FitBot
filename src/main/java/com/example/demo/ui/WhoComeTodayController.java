package com.example.demo.ui;

import com.example.demo.dao.Pass;
import com.example.demo.dao.Visits;
import com.example.demo.dao.supportTables.ComeToDay;
import com.example.demo.exception.ExceptionDB;
import com.example.demo.util.FillingFieldsHelper;
import com.example.demo.util.GetCommonWindowHelper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class WhoComeTodayController {
    private final FillingFieldsHelper fillingFieldsHelper;
    private ObservableList<ComeToDay> comeToDayObservableList;

    @FXML
    private Button backButton;
    @FXML
    private Button writeOffVisitButton;

    @FXML
    private TableColumn<ComeToDay, String> firstNameColumn;
    @FXML
    private TableColumn<ComeToDay, String> nameColumn;
    @FXML
    private TableColumn<ComeToDay, String> patronymicColumn;
    @FXML
    private TableColumn<ComeToDay, String> phoneNumberColumn;
    @FXML
    private TableColumn<ComeToDay, LocalDate> todayColumn;
    @FXML
    private TableView<ComeToDay> tableView;

    public WhoComeTodayController() {
        this.fillingFieldsHelper =new FillingFieldsHelper();
    }


    @FXML
    void initialize(Stage stageWhoComeToday, Image image) {
        initDataToTable();
        writeOffVisitButton.setOnAction(event -> writeOffVisits(image));
        backButton.setOnAction(event -> stageWhoComeToday.close());
    }

    private void initDataToTable() {
        comeToDayObservableList = fillingFieldsHelper.getObservableListAllComeToday();

        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        patronymicColumn.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("telephoneNum"));
        todayColumn.setCellValueFactory(new PropertyValueFactory<>("currencyDate"));

        tableView.setItems(comeToDayObservableList);
    }

    private void writeOffVisits(Image image) {
        if (!comeToDayObservableList.isEmpty()) {
            for (ComeToDay c: comeToDayObservableList) {
                String phoneNumber = c.getTelephoneNum();
                Optional<List<Pass>> actualListPass = fillingFieldsHelper.getActualPassByPhoneNumber(phoneNumber);
                if (actualListPass.isPresent()) {
                    Pass pass = actualListPass.get().get(0);
                    Integer passId = pass.getNumPass();
                    LocalDate dateVisit = c.getCurrencyDate();

                    Visits visit = new Visits();
                    visit.setDateVisit(dateVisit);
                    visit.setCountVisit(1);
                    visit.setPass(passId);

                    boolean createVisit = false;
                    try {
                        createVisit = fillingFieldsHelper.createVisit(visit);
                    } catch (ExceptionDB e) {
                        String messageError = e.getMessage();
                        new GetCommonWindowHelper().openWindowUnSuccess(image, event -> writeOffVisits(image));
                    }
                    if (!createVisit) {
                        String messageError = "Занятие не записалось в базу данных";
                        new GetCommonWindowHelper().openWindowUnSuccess(image, event -> writeOffVisits(image));
                    } else {
                        String message = "Занятие успешно записано в базу данных";
                        new GetCommonWindowHelper().openWindowSuccess(image, message);
                    }
                }
            }
        }
    }
}
