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

import java.util.regex.Pattern;

public class FillingFieldsHelper {
    private final PassService passService;
    private static final Pattern integerNum = Pattern.compile(PatternTemplate.INTEGER_LINE.getTemplate());
    private static final Pattern letters = Pattern.compile("[а-яА-Я]");
    private static final Pattern firstNumberInPhone = Pattern.compile("7");
    private static final Pattern secondNumberInPhone = Pattern.compile("9");
    private static final Pattern firstNumber = Pattern.compile("0?|[123]");
    private static final Pattern secondNumberIfNoThree = Pattern.compile("[1-9]?");
    private static final Pattern secondNumberIfThree = Pattern.compile("[01]?");
    private static final Pattern thirdOrSixthSymbol = Pattern.compile("\\.");
    private static final Pattern fourthNumber = Pattern.compile("[01]");
    private static final Pattern fifthNumberIfFourthIsZero = Pattern.compile("[1-9]");
    private static final Pattern fifthNumberIfFourthIsOne = Pattern.compile("[0-2]");
    private static final Pattern seventhNumber = Pattern.compile("2");
    private static final Pattern eightNumber = Pattern.compile("0");
    private static final Pattern ninthNumber = Pattern.compile("[2-9]");
    private static final Pattern tenthNumber = Pattern.compile("[0-9]");

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

    public static void correctInputPhoneLine(TextField newPhoneNumberValue) {
        int maxCharacters = 11;
        newPhoneNumberValue.textProperty().addListener((observable10, oldValue10, newValue10) -> {
            String newPhoneNumberValueArray = newPhoneNumberValue.getText();
            if (newValue10.length() > maxCharacters) newPhoneNumberValue.deleteNextChar();
            if (newPhoneNumberValueArray.length() == 1) {
                newValue10 = newPhoneNumberValueArray;
                if (!firstNumberInPhone.matcher(newValue10).matches()) newPhoneNumberValue.setText(oldValue10);
            }
            if (newPhoneNumberValueArray.length() == 2) {
                newValue10 = String.valueOf(newPhoneNumberValueArray.charAt(1));
                if (!secondNumberInPhone.matcher(newValue10).matches()) newPhoneNumberValue.setText(oldValue10);
            }
            if (newPhoneNumberValueArray.length() > 2 && newPhoneNumberValueArray.length() <= 10) {
                newValue10 = String.valueOf(newPhoneNumberValueArray.charAt(newPhoneNumberValueArray.length() - 1));
                if (!integerNum.matcher(newValue10).matches()) newPhoneNumberValue.setText(oldValue10);
            }
        });
    }

    public static void correctInputDateLine(TextField dateInput) {
        dateInput.textProperty().addListener((observable, oldValue, newValue) -> {
            String dateInputArray = dateInput.getText();
            if (dateInputArray.length() == 1) {
                newValue = dateInputArray;
                if (!firstNumber.matcher(newValue).matches()) dateInput.setText(oldValue);
            }
            if (dateInputArray.length() == 2) {
                if ("3".equals(String.valueOf(dateInputArray.charAt(0)))) {
                    newValue = String.valueOf(dateInputArray.charAt(1));
                    if (!secondNumberIfThree.matcher(newValue).matches()) dateInput.setText(oldValue);
                } else {
                    newValue = String.valueOf(dateInputArray.charAt(1));
                    if (!secondNumberIfNoThree.matcher(newValue).matches()) dateInput.setText(oldValue);
                }
            }
            if (dateInputArray.length() == 3 || dateInputArray.length() == 6) {
                newValue = String.valueOf(dateInputArray.charAt(2));
                if (dateInputArray.length() == 6) newValue = String.valueOf(dateInputArray.charAt(5));;
                if (!thirdOrSixthSymbol.matcher(newValue).matches()) dateInput.setText(oldValue);
            }
            if (dateInputArray.length() == 4) {
                newValue = String.valueOf(dateInputArray.charAt(3));
                if (!fourthNumber.matcher(newValue).matches()) dateInput.setText(oldValue);
            }
            if (dateInputArray.length() == 5) {
                if ("0".equals(String.valueOf(dateInputArray.charAt(3)))) {
                    newValue = String.valueOf(dateInputArray.charAt(4));
                    if (!fifthNumberIfFourthIsZero.matcher(newValue).matches()) dateInput.setText(oldValue);
                } else {
                    newValue = String.valueOf(dateInputArray.charAt(4));
                    if (!fifthNumberIfFourthIsOne.matcher(newValue).matches()) dateInput.setText(oldValue);
                }
            }
            if (dateInputArray.length() == 7) {
                newValue = String.valueOf(dateInputArray.charAt(6));
                if (!seventhNumber.matcher(newValue).matches()) dateInput.setText(oldValue);
            }
            if (dateInputArray.length() == 8) {
                newValue = String.valueOf(dateInputArray.charAt(7));
                if (!eightNumber.matcher(newValue).matches()) dateInput.setText(oldValue);
            }
            if (dateInputArray.length() == 9) {
                newValue = String.valueOf(dateInputArray.charAt(8));
                if (!ninthNumber.matcher(newValue).matches()) dateInput.setText(oldValue);
            }
            if (dateInputArray.length() == 10) {
                newValue = String.valueOf(dateInputArray.charAt(9));
                if (!tenthNumber.matcher(newValue).matches()) dateInput.setText(oldValue);
            }
            if (dateInputArray.length() > 10) dateInput.setText(oldValue);
        });
    }

    public static void correctInputIntegerLine(TextField number) {
        number.textProperty().addListener((observable11, oldValue11, newValue11) -> {
            if (!integerNum.matcher(newValue11).matches()) number.setText(oldValue11);
        });
    }

    public static void correctInputStringLine(TextField anyString) {
        anyString.textProperty().addListener((observable50, oldValue50, newValue50) -> {
            System.out.println("letters.matcher(newValue1).matches() = " + letters.matcher(newValue50).matches());
            if (!letters.matcher(newValue50).matches()) {anyString.setText(oldValue50);}
        });
    }

    public boolean isDate(TextField dateInput) {
        return Pattern.compile(PatternTemplate.DATE.getTemplate()).matcher(dateInput.getCharacters()).matches();
    }

    public static boolean isPhoneNumber(String phoneNumber) {
        return phoneNumber != null && !phoneNumber.isBlank() && phoneNumber.length() == 11
                && phoneNumber.matches(PatternTemplate.INTEGER_LINE.getTemplate())
                && phoneNumber.matches(PatternTemplate.FIRST_IN_PHONE.getTemplate());
    }
}
