package com.example.demo.ui;

import com.example.demo.config.DBConfig;
import com.example.demo.dao.repositories.IVisitsRepository;
import com.example.demo.dao.repositories.impl.VisitsRepository;
import com.example.demo.util.GetCommonWindowHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.time.LocalDate;

public class DeleteVisitController {
    private final IVisitsRepository visitsRepository;

    @FXML
    private Button backButton;
    @FXML
    private Button deleteVisitButton;

    @FXML
    private Label countVisitsInThisDayValueDB;
    @FXML
    private Label dateVisitValueDB;
    @FXML
    private Label passNumberValueDB;
    @FXML
    private Label phoneLabel;

    public DeleteVisitController() {
        NamedParameterJdbcTemplate jdbcTemplate = new DBConfig().namedParameterJdbcTemplate();
        this.visitsRepository = new VisitsRepository(jdbcTemplate);
    }


    @FXML
    void initialize(Stage stageDeleteVisit, Image image, Integer pass, LocalDate dateVisit, Integer countVisit,
                    String inputPhoneNumber) {
        initData(image, pass, dateVisit, countVisit, inputPhoneNumber);
        backButton.setOnAction(event -> stageDeleteVisit.close());
    }

    private void initData(Image image, Integer pass, LocalDate dateVisit, Integer countVisit,
                     String inputPhoneNumber) {
        phoneLabel.setText(inputPhoneNumber);
        passNumberValueDB.setText(String.valueOf(pass));
        dateVisitValueDB.setText(String.valueOf(dateVisit));
        countVisitsInThisDayValueDB.setText(String.valueOf(countVisit));

        deleteVisitButton.setDisable(false);
        deleteVisitButton.setOnAction(event -> new GetCommonWindowHelper().openWindowConfirmation(image,
                event2 -> deleteVisitFromDB(image, dateVisit)));
    }

    private void deleteVisitFromDB(Image image, LocalDate date) {
        boolean isSuccess = visitsRepository.deleteVisit(date);
        if (isSuccess) {
            String message = "Посещение успешно удалено из базы данных";
            new GetCommonWindowHelper().openWindowSuccess(image, message);
        } else {
            String message = "Произошла ошибка во время удаления из базы данных. Обратитесь к разработчику";
            new GetCommonWindowHelper().openWindowUnSuccess(image, event -> deleteVisitFromDB(image, date), message);
        }
    }
}
