package com.example.demo.ui;

import com.example.demo.dao.Pass;
import com.example.demo.util.FillingFieldsHelper;
import com.example.demo.util.GetCommonWindowHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class CreatePassController {
    private final FillingFieldsHelper fillingFieldsHelper;

    @FXML
    private Button backButton;
    @FXML
    private Button createPassButton;

    @FXML
    private TextField createCountFreeze;
    @FXML
    private TextField createCountVisits;
    @FXML
    private TextField createDateEndPass;
    @FXML
    private TextField createDateStartFreeze;
    @FXML
    private TextField createDateStartPass;
    @FXML
    private TextField createPhoneNumber;

    public CreatePassController() {
        this.fillingFieldsHelper = new FillingFieldsHelper();
    }

    @FXML
    void initialize(Stage stageCreatePass, Image image) {
        FillingFieldsHelper.correctInputPhoneLine(createPhoneNumber);
        FillingFieldsHelper.correctInputDateLine(createDateStartPass);
        FillingFieldsHelper.correctInputIntegerLine(createCountVisits);
        FillingFieldsHelper.correctInputDateLine(createDateEndPass);
        FillingFieldsHelper.correctInputIntegerLine(createCountFreeze);
        FillingFieldsHelper.correctInputDateLine(createDateStartFreeze);

        createPassButton.setOnAction(event -> validateFields(image));
        backButton.setOnAction(event -> stageCreatePass.close());
    }

    private void validateFields(Image image) {
        if (FillingFieldsHelper.isFillingAllFields(createPhoneNumber, createDateStartPass, createCountVisits,
                createDateEndPass, createCountFreeze, createDateStartPass)) {
            createPassInDB(image);
        } else {
            String message = "Заполните все поля, отмеченные '*'";
            new GetCommonWindowHelper().openWindowSuccess(image, message);
        }
    }

    private void createPassInDB(Image image) {
        Pass pass = new Pass();
        pass.setPhoneNumber(createPhoneNumber.getText());
        try {
            pass.setDateStart(fillingFieldsHelper.convertFormatLocalDate(createDateStartPass.getText()));
        } catch (ParseException e) {
            String message = "Произошла ошибка во время записи в базу данных. Обратитесь к разработчику";
            new GetCommonWindowHelper().openWindowUnSuccess(image, event -> validateFields(image), message);
        }
        pass.setVisitLimit(Integer.valueOf(createCountVisits.getText()));
        try {
            pass.setDateEnd(fillingFieldsHelper.convertFormatLocalDate(createDateEndPass.getText()));
        } catch (ParseException e) {
            String message = "Произошла ошибка во время записи в базу данных. Обратитесь к разработчику";
            new GetCommonWindowHelper().openWindowUnSuccess(image, event -> validateFields(image), message);
        }
        if (createCountFreeze != null && createCountFreeze.getText() != null && !createCountFreeze.getText().isBlank()) {
            pass.setFreezeLimit(Integer.valueOf(createCountFreeze.getText()));
        }
        if (createDateStartFreeze != null && createDateStartFreeze.getText() != null && !createDateStartFreeze.getText().isBlank()) {
            try {
                pass.setDateStartFreeze(fillingFieldsHelper.convertFormatLocalDate(createDateStartFreeze.getText()));
            } catch (ParseException e) {
                String message = "Произошла ошибка во время записи в базу данных. Обратитесь к разработчику";
                new GetCommonWindowHelper().openWindowUnSuccess(image, event -> validateFields(image), message);
            }
        }

        Optional<List<Pass>> passList = fillingFieldsHelper.getPassList(pass.getPhoneNumber());
        boolean isExist = false;
        if (passList.isPresent()) {
            for (Pass p: passList.get()) {
               LocalDate dateStartForEqual = p.getDateStart();
               int limitVisits = p.getVisitLimit();
               LocalDate dateEndForEqual = p.getDateEnd();

               if (pass.getDateStart() != null && pass.getDateEnd() != null && pass.getVisitLimit() != null) {
                   boolean isEqualDateStarts = pass.getDateStart().isEqual(dateStartForEqual);
                   boolean isEqualDateEnds = pass.getDateEnd().isEqual(dateEndForEqual);
                   boolean isEqualLimitVisits = pass.getVisitLimit() == limitVisits;

                   if (isEqualDateEnds && isEqualLimitVisits && isEqualDateStarts) {
                       isExist = true;
                   }
               }
            }
        }
        if (!isExist) {
            boolean isSuccess = fillingFieldsHelper.createPassInDB(pass);
            if (isSuccess) {
                String message = "Абонемент успешно внесен в базу данных";
                new GetCommonWindowHelper().openWindowSuccess(image, message);
            } else {
                String message = "Произошла ошибка во время записи в базу данных. Обратитесь к разработчику";
                new GetCommonWindowHelper().openWindowUnSuccess(image, event -> createPassInDB(image), message);
            }
        } else {
            openWindowExistPass(image, createPhoneNumber);
        }
    }

    @FXML
    public void openWindowExistPass(Image image, TextField phoneNumber) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/existPass-view.fxml"));
        Parent root1;
        try {
            root1 = fxmlLoader.load();
            Stage stageExistPass = new Stage();
            stageExistPass.setResizable(false);
            stageExistPass.getIcons().add(image);
            stageExistPass.setTitle("Абонемент с такими данными есть в базе данных");
            stageExistPass.setScene(new Scene(root1, 383, 158));

            ExistPassController existPassController = fxmlLoader.getController();
            existPassController.initialize(stageExistPass, image, phoneNumber);

            stageExistPass.show();
        } catch (IOException e) {
            String message = "Произошла ошибка во время открытия окна. Обратитесь к разработчику";
            new GetCommonWindowHelper().openWindowUnSuccess(image, event -> openWindowExistPass(image, phoneNumber), message);
        }
    }
}
