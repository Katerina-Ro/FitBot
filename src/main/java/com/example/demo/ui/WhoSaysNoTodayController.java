package com.example.demo.ui;

import com.example.demo.dao.supportTables.DontComeToDay;
import com.example.demo.util.FillingFieldsHelper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDate;

public class WhoSaysNoTodayController {
    private final FillingFieldsHelper fillingFieldsHelper;

    @FXML
    private Button backButton;

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
    void initialize(Stage stageWhoSaysNoToday) {
        initData();
        backButton.setOnAction(event -> stageWhoSaysNoToday.close());
    }

    private void initData() {
        ObservableList<DontComeToDay> dontComeToDayObservableList = fillingFieldsHelper.getObservableListDontComeToday();

        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        patronymicColumn.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("telephoneNum"));
        todayColumn.setCellValueFactory(new PropertyValueFactory<>("currencyDate"));

        tableView.setItems(dontComeToDayObservableList);
    }
}
