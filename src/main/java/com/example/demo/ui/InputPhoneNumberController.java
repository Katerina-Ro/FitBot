package com.example.demo.ui;

import com.example.demo.config.DBConfig;
import com.example.demo.dao.Pass;
import com.example.demo.dao.repositories.IPassRepository;
import com.example.demo.dao.repositories.impl.PassRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class InputPhoneNumberController {

    private final IPassRepository passRepository;
    private ObservableList<Pass> pass;

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;

    @FXML
    private TableView<Pass> tablePass;
    @FXML
    private TableColumn<Pass, Integer> passIdColumn;
    @FXML
    private TableColumn<Pass, String> phoneNumberColumn;
    @FXML
    private TableColumn<Pass, LocalDate> dateStartColumn;
    @FXML
    private TableColumn<Pass, LocalDate> dateEndColumn;
    @FXML
    private TableColumn<Pass, Integer> visitLimitColumn;
    @FXML
    private TableColumn<Pass, Integer> freezeLimitColumn;
    @FXML
    private TableColumn<Pass, LocalDate> dateStartFreezeColumn;

    @FXML
    private TextField inputPhoneNumber;

    @FXML
    private Label passId;
    @FXML
    private Label passIdValue;

    @FXML
    private Label phoneNumber;
    @FXML
    private Label phoneNumberValue;

    @FXML
    private Label dateStart;
    @FXML
    private Label dateStartValue;

    @FXML
    private Label dateEnd;
    @FXML
    private Label dateEndValue;

    @FXML
    private Label dateStartFreeze;
    @FXML
    private Label dateStartFreezeValue;

    @FXML
    private Label countFreeze;
    @FXML
    private Label countFreezeValue;

    @FXML
    private Label visitLimit;
    @FXML
    private Label visitLimitValue;


    public InputPhoneNumberController() {
        this.passRepository = new PassRepository(new DBConfig().namedParameterJdbcTemplate());
    }

    @FXML
    private void initialize() {
        // Инициализация таблицы
        passIdColumn.setCellValueFactory(new PropertyValueFactory<>("numPass"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        dateStartColumn.setCellValueFactory(new PropertyValueFactory<>("dateStart"));
        dateEndColumn.setCellValueFactory(new PropertyValueFactory<>("dateEnd"));
        visitLimitColumn.setCellValueFactory(new PropertyValueFactory<>("visitLimit"));
        freezeLimitColumn.setCellValueFactory(new PropertyValueFactory<>("freezeLimit"));
        dateStartFreezeColumn.setCellValueFactory(new PropertyValueFactory<>("dateStartFreeze"));
    }

    public void getTablePass(TextField inputPhoneNumber) {
        String phoneNumber = String.valueOf(inputPhoneNumber.getText());
        pass = getListPass(phoneNumber);

        if (!pass.isEmpty()) {
            tablePass.setItems(pass);
        }
    }

    private ObservableList<Pass> getListPass(String phoneNumber) {
        Optional<List<Pass>> listPass = passRepository.findPassByPhone(phoneNumber);
        return (ObservableList<Pass>) listPass.orElse(FXCollections.emptyObservableList());
    }
}
