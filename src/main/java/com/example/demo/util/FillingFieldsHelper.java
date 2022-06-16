package com.example.demo.util;

import com.example.demo.config.DBConfig;
import com.example.demo.dao.Pass;
import com.example.demo.dao.Visitors;
import com.example.demo.dao.Visits;
import com.example.demo.dao.repositories.impl.PassRepository;
import com.example.demo.dao.repositories.impl.VisitorsRepository;
import com.example.demo.dao.repositories.impl.VisitsRepository;
import com.example.demo.modelService.PassService;
import com.example.demo.modelService.VisitorsService;
import com.example.demo.modelService.VisitsService;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class FillingFieldsHelper {
    private final PassService passService;
    private final VisitorsRepository visitorsRepository;
    private final VisitsRepository visitsRepository;
    private final PassRepository passRepository;
    private static final Pattern integerNum = Pattern.compile(PatternTemplate.INTEGER_LINE.getTemplate());
    private static final Pattern letters = Pattern.compile("^[а-яА-Я]+$");
    private static final Pattern firstNumberInPhone = Pattern.compile("7");
    private static final Pattern secondNumberInPhone = Pattern.compile("9");
    private static final Pattern firstNumber = Pattern.compile("0?|[123]");
    private static final Pattern secondNumberIfNoThree = Pattern.compile("[1-9]?");
    private static final Pattern secondNumberIfThree = Pattern.compile("[01]?");
    private static final Pattern FIRST_TWO_SYMBOL_IN_PHONE_NUMBER = Pattern.compile("79");
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
        this.visitorsRepository = new VisitorsRepository(jdbcTemplate);
        this.passRepository = new PassRepository(jdbcTemplate);
        this.visitsRepository = new VisitsRepository(jdbcTemplate);
        VisitsService visitsService = new VisitsService(this.visitsRepository);
        VisitsRepository visitsRepository = new VisitsRepository(jdbcTemplate);
        this.passService = new PassService(passRepository, visitsService,
                new VisitorsService(this.visitorsRepository, this.passRepository, visitsService),
                visitsRepository);
    }

    public ObservableList<Pass> getTablePass(StringProperty inputPhoneNumber){
        String phoneNumber = String.valueOf(inputPhoneNumber.get());
        return passService.getListPass(phoneNumber);
    }

    public ObservableList<Visitors> getVisitorsObservableList(StringProperty inputPhoneNumber) {
        String phoneNumber = String.valueOf(inputPhoneNumber.get());
        if (phoneNumber != null) {
            Optional<Visitors> visitor = visitorsRepository.findVisitorByPhoneNumber(phoneNumber);
            return visitor.map(FXCollections::observableArrayList).orElseGet(FXCollections::emptyObservableList);
        }
        return FXCollections.emptyObservableList();
    }

    /**
     * Получение информации об актуальном абонементе
     * @return список абонементов. По логике кода заложено, что может быть несколько активных абонементов.
     * Но, по идее (в реальности), должен быть только один
     */
    public Optional<List<Pass>> findVisitorByPhoneNumber(String phoneNumber) {
        Optional<Visitors> visitor = visitorsRepository.findVisitorByPhoneNumber(phoneNumber);
        Optional<List<Pass>> passListFromDB;
        if (visitor.isPresent()) {
            passListFromDB = passRepository.findPassByPhone(phoneNumber);
            if (passListFromDB.isPresent()) {
                List<Pass> listActualPass = new ArrayList<>();
                for (Pass p: passListFromDB.get()) {
                    if (haveDayInPassCalculate(p)) {
                        listActualPass.add(p);
                        return Optional.of(listActualPass);
                    }
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Проверка, есть ли еще день в абонементе у конкретного студента
     * @param pass - информация об абонементе
     * @return true, если есть как минимум 1 занятие
     */
    public boolean haveDayInPassCalculate(Pass pass) {
        Optional<String> countDayInPass = calculateClassesLeft(pass);
        return countDayInPass.filter(s -> Integer.parseInt(s) > 0).isPresent();
    }

    /**
     * Расчет оставшегося количества занятий у конкретного студента
     * @param pass - информация об абонементе
     */
    public Optional<String> calculateClassesLeft(Pass pass) {
        String classesLeft = null;
        int cumulativeTotal = 0;
        if (betweenDate(pass)) {
            Optional<List<Visits>> listVisits = visitsRepository.findAllVisitsByPassId(pass.getNumPass());
            if (listVisits.isPresent()) {
                for (Visits v: listVisits.get()) {
                    int countVisitInOneDay = v.getCountVisit();
                    cumulativeTotal += countVisitInOneDay;
                }
            }
            classesLeft = String.valueOf(pass.getVisitLimit() - cumulativeTotal);
        }
        return Optional.ofNullable(classesLeft);
    }

    /**
     * Определение, находится ли текущая дата в промежутке между началом и окончанием абонемента
     * @param pass - данные абонемента
     * @return true, если находится
     */
    public boolean betweenDate(Pass pass) {
        boolean betweenDate = false;
        LocalDate currencyDay = LocalDate.now(ZoneId.of("GMT+03:00"));
        LocalDate dateEnd = pass.getDateEnd();
        LocalDate dateStart = pass.getDateStart();

        // могут быть true и false
        boolean isEqualDateStart = currencyDay.isEqual(dateStart);

        // оба должны быть true
        boolean isBeforeDateEnd = currencyDay.isBefore(dateEnd);
        boolean isAfterDateStart = currencyDay.isAfter(dateStart);

        if ((isEqualDateStart || isAfterDateStart) && (isBeforeDateEnd) ) {
            betweenDate = true;
        }
        return betweenDate;
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
            String anyStringArray = anyString.getText();
            if (anyStringArray.length() == 1) {
                newValue50 = anyStringArray;
                if (!letters.matcher(newValue50).matches()) anyString.setText(oldValue50);
            }
            if (anyStringArray.length() > 1) {
                newValue50 = String.valueOf(anyStringArray.charAt(anyStringArray.length() - 1));
                if (!letters.matcher(newValue50).matches()) anyString.setText(oldValue50);
            }
        });
    }

    public boolean isDate(TextField dateInput) {
        return Pattern.compile(PatternTemplate.DATE.getTemplate()).matcher(dateInput.getCharacters()).matches();
    }

    public static boolean isPhoneNumber(String phoneNumber) {
        return !phoneNumber.isBlank() && phoneNumber.length() == 11
                && phoneNumber.matches(PatternTemplate.INTEGER_LINE.getTemplate())
                && Pattern.compile(PatternTemplate.FIRST_IN_PHONE.getTemplate()).matcher(String.valueOf(phoneNumber.charAt(0))).matches();
    }

    private static boolean is79FirstTwoSymbol(String substringPhone) {
        return FIRST_TWO_SYMBOL_IN_PHONE_NUMBER.matcher(substringPhone).matches();
    }
}
