package com.example.demo.ui;

import com.example.demo.config.DBConfig;
import com.example.demo.dao.Pass;
import com.example.demo.dao.repositories.impl.PassRepository;
import com.example.demo.dao.repositories.impl.VisitorsRepository;
import com.example.demo.dao.repositories.impl.VisitsRepository;
import com.example.demo.modelService.PassService;
import com.example.demo.modelService.VisitorsService;
import com.example.demo.modelService.VisitsService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.time.LocalDate;

public class WhichPassController {
    private final PassService passService;

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
        NamedParameterJdbcTemplate jdbcTemplate = new DBConfig().namedParameterJdbcTemplate();
        VisitorsRepository visitorsRepository = new VisitorsRepository(jdbcTemplate);
        PassRepository passRepository = new PassRepository(jdbcTemplate);
        VisitsService visitsService = new VisitsService(new VisitsRepository(jdbcTemplate));
        VisitsRepository visitsRepository = new VisitsRepository(jdbcTemplate);
        this.passService = new PassService(passRepository, visitsService,
                new VisitorsService(visitorsRepository, passRepository, visitsService),
                visitsRepository);
    }

    @FXML
    void initialize(String phoneNumber) {
        ObservableList<Pass> passObservableList = FXCollections.observableArrayList(passService.getListPass(phoneNumber));
        if (!passObservableList.isEmpty()) {
            passNumberColumn.setCellValueFactory(new PropertyValueFactory<Pass, Integer>("pass_id"));
            dateStartPassColumn.setCellValueFactory(new PropertyValueFactory<>("date_start"));
            dateEndPassColumn.setCellValueFactory(new PropertyValueFactory<>("date_end"));
            leftPassColumn.setCellValueFactory(new PropertyValueFactory<>("visit_limit"));

            tableWhichPass.setItems(passObservableList);
        }
    }
}
