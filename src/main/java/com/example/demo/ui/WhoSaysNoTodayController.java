package com.example.demo.ui;

import com.example.demo.dao.supportTables.DontComeToDay;
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

public class WhoSaysNoTodayController {
    private final FillingFieldsHelper fillingFieldsHelper;
    private ObservableList<DontComeToDay> dontComeToDayObservableList;

    @FXML
    private Button backButton;
    @FXML
    private Button deleteDontComeToDay;

    @FXML
    private TableColumn<DontComeToDay, String> firstNameColumn;
    @FXML
    private TableColumn<DontComeToDay, String> nameColumn;
    @FXML
    private TableColumn<DontComeToDay, String> patronymicColumn;
    @FXML
    private TableColumn<DontComeToDay, String> phoneNumberColumn;
    @FXML
    private TableColumn<DontComeToDay, LocalDate> todayColumn;
    @FXML
    private TableView<DontComeToDay> tableView;

    public WhoSaysNoTodayController() {
        this.fillingFieldsHelper = new FillingFieldsHelper();
    }

    @FXML
    void initialize(Stage stageWhoSaysNoToday, Image image) {
        initData();
        backButton.setOnAction(event -> stageWhoSaysNoToday.close());
        deleteDontComeToDay.setOnAction(event -> new GetCommonWindowHelper().openWindowConfirmation(image,
                event2 -> clearTableDontComeToDayFromDB()));
    }

    private void initData() {
        dontComeToDayObservableList = fillingFieldsHelper.getObservableListDontComeToday();

        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        patronymicColumn.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("telephoneNum"));
        todayColumn.setCellValueFactory(new PropertyValueFactory<>("currencyDate"));

        tableView.setItems(dontComeToDayObservableList);
    }

    private void clearTableDontComeToDayFromDB() {
        if (!dontComeToDayObservableList.isEmpty()) {
            for (DontComeToDay dc : dontComeToDayObservableList) {
                fillingFieldsHelper.deleteDontComeToDay(dc.getTelephoneNum());
            }
        }
    }
}
