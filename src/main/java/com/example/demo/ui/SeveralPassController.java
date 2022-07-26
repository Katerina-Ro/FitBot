package com.example.demo.ui;

import com.example.demo.dao.supportTables.PassSupport;
import com.example.demo.util.FillingFieldsHelper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.time.LocalDate;

public class SeveralPassController {
    private final FillingFieldsHelper fillingFieldsHelper;

    @FXML
    private Button backButton;

    @FXML
    private Label phoneLabel;

    @FXML
    private TableColumn<PassSupport, LocalDate> dateEndPass;
    @FXML
    private TableColumn<PassSupport, LocalDate> dateStartPass;
    @FXML
    private TableColumn<PassSupport, Integer> visitLimit;
    @FXML
    private TableColumn<PassSupport, Button> deletePassButton;
    @FXML
    private TableColumn<PassSupport, Button> getPassButton;
    @FXML
    private TableColumn<PassSupport, Integer> passNumber;
    @FXML
    private TableView<PassSupport> tableVisits;

    public SeveralPassController() {
        this.fillingFieldsHelper = new FillingFieldsHelper();
    }

    @FXML
    public void initialize(Stage stageSeveralPass, Image image, String phoneNumber) {
        initData(image, phoneNumber);
        backButton.setOnAction(event -> stageSeveralPass.close());
    }

    private void initData(Image image, String phoneNumber) {
        ObservableList<PassSupport> listPassSupport = fillingFieldsHelper.passSupportList(phoneNumber);

        phoneLabel.setText(phoneNumber);
        passNumber.setCellValueFactory(new PropertyValueFactory<>("numPass"));
        visitLimit.setCellValueFactory(new PropertyValueFactory<>("visitLimit"));
        dateStartPass.setCellValueFactory(new PropertyValueFactory<>("dateStart"));
        dateEndPass.setCellValueFactory(new PropertyValueFactory<>("dateEnd"));
        deletePassButton.setCellValueFactory(new PropertyValueFactory<>("deletePassButton"));
        getPassButton.setCellValueFactory(new PropertyValueFactory<>("getPassButton"));

        tableVisits.setItems(listPassSupport);
    }
}
