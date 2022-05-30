package com.example.demo.ui;

import com.example.demo.dao.Pass;
import com.example.demo.util.FillingFieldsHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

public class WhichPassController {
    private final FillingFieldsHelper passRepositoryHelper;

    @FXML
    private TableView<Pass> tableWhichPass;
    @FXML
    private TableColumn<Pass, Button> buttonChoose;

    @FXML
    private TableColumn<Pass, LocalDate> dateEndPassColumn;

    @FXML
    private TableColumn<Pass, LocalDate> dateStartPassColumn;

    @FXML
    private TableColumn<Pass, Integer> leftPassColumn;

    @FXML
    private TableColumn<Pass, Integer> passNumberColumn;

    public WhichPassController() {
        this.passRepositoryHelper = new FillingFieldsHelper();
    }

    @FXML
    void initialize(String phoneNumber) {
        ObservableList<Pass> passObservableList = FXCollections.observableArrayList(passRepositoryHelper.getListPass(phoneNumber));
        if (!passObservableList.isEmpty()) {
            passNumberColumn.setCellValueFactory(new PropertyValueFactory<Pass, Integer>("pass_id"));
            dateStartPassColumn.setCellValueFactory(new PropertyValueFactory<>("date_start"));
            dateEndPassColumn.setCellValueFactory(new PropertyValueFactory<>("date_end"));
            leftPassColumn.setCellValueFactory(new PropertyValueFactory<>("visit_limit"));

            tableWhichPass.setItems(passObservableList);
        }
    }
}
