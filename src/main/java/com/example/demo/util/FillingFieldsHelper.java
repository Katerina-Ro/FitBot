package com.example.demo.util;

import com.example.demo.config.DBConfig;
import com.example.demo.dao.Pass;
import com.example.demo.dao.repositories.impl.PassRepository;
import com.example.demo.dao.repositories.impl.VisitorsRepository;
import com.example.demo.dao.repositories.impl.VisitsRepository;
import com.example.demo.modelService.PassService;
import com.example.demo.modelService.VisitorsService;
import com.example.demo.modelService.VisitsService;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public class FillingFieldsHelper {
    private final PassService passService;
    Pattern p = Pattern.compile(PatternTemplate.INTEGER_LINE.getTemplate());

    public FillingFieldsHelper() {
        NamedParameterJdbcTemplate jdbcTemplate = new DBConfig().namedParameterJdbcTemplate();
        VisitorsRepository visitorsRepository = new VisitorsRepository(jdbcTemplate);
        PassRepository passRepository = new PassRepository(jdbcTemplate);
        VisitsService visitsService = new VisitsService(new VisitsRepository(jdbcTemplate));
        VisitsRepository visitsRepository = new VisitsRepository(jdbcTemplate);
        this.passService = new PassService(passRepository, visitsService,
                new VisitorsService(visitorsRepository, passRepository, visitsService),
                visitsRepository);
    }

    public ObservableList<Pass> getTablePass(StringProperty inputPhoneNumber){
        String phoneNumber = String.valueOf(inputPhoneNumber.get());
        return passService.getListPass(phoneNumber);
    }

    public void correctInputDateLine(TextField dateInput) {
        Pattern p2=Pattern.compile("(\\d+\\.?\\d*)?");
        Pattern pa = Pattern.compile("^(0?[1-9]|[12][0-9]|3[01])[\\/\\-\\.](0?[1-9]|1[012])[ \\/\\.\\-]/");
        Pattern pattern = Pattern.compile("^(0?[1-9])[\\.](0?[1-9])[\\/\\.\\-]([202][0-9])$");
        Pattern pat = Pattern.compile("^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$");
        dateInput.textProperty().addListener((observable12, oldValue12, newValue12) -> {
            if (!pattern.matcher(newValue12).matches()) dateInput.setText(oldValue12);
        });
    }

    public void correctInputIntegerLine(TextField number) {
        number.textProperty().addListener((observable11, oldValue11, newValue11) -> {
            if (!p.matcher(newValue11).matches()) number.setText(oldValue11);
        });
    }

    public static void correctInputStringLine(TextField anyString) {
        anyString.textProperty().addListener((observable1, oldValue1, newValue1) -> {
            if (!CheckingInputLinesUtil.isLetters(anyString.toString())) anyString.setText(oldValue1);
        });
    }

    public boolean isDate(TextField dateInput) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return simpleDateFormat.equals(dateInput);
    }

    public static boolean isPhoneNumber(String phoneNumber) {
        return phoneNumber != null && !phoneNumber.isBlank() && phoneNumber.length() == 11
                && phoneNumber.matches(PatternTemplate.INTEGER_LINE.getTemplate())
                && phoneNumber.matches(PatternTemplate.FIRST_IN_PHONE.getTemplate());
    }
}
