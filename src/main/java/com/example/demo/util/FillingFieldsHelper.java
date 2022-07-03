package com.example.demo.util;

import com.example.demo.config.DBConfig;
import com.example.demo.dao.Pass;
import com.example.demo.dao.Visitors;
import com.example.demo.dao.Visits;
import com.example.demo.dao.repositories.IPassRepository;
import com.example.demo.dao.repositories.IVisitorsRepository;
import com.example.demo.dao.repositories.IVisitsRepository;
import com.example.demo.dao.repositories.impl.PassRepository;
import com.example.demo.dao.repositories.impl.VisitorsRepository;
import com.example.demo.dao.repositories.impl.VisitsRepository;
import com.example.demo.dao.repositories.support.IComeToDay;
import com.example.demo.dao.repositories.support.IDontComeToDay;
import com.example.demo.exception.ExceptionDB;
import com.example.demo.exception.SeveralException;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import lombok.NonNull;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Pattern;

public class FillingFieldsHelper {
    private final IVisitorsRepository visitorsRepository;
    private final IVisitsRepository visitsRepository;
    private final IPassRepository passRepository;
    private final IComeToDay comeToDay;
    private final IDontComeToDay dontComeToDay;
    private static final Pattern INTEGER_NUM = Pattern.compile(PatternTemplate.INTEGER_LINE.getTemplate());
    private static final Pattern LETTERS = Pattern.compile("^[а-яА-Я]+$");
    private static final Pattern FIRST_NUMBER_IN_PHONE = Pattern.compile("7");
    private static final Pattern SECOND_NUMBER_IN_PHONE = Pattern.compile("9");
    private static final Pattern FIRST_NUMBER = Pattern.compile("0?|[123]");
    private static final Pattern SECOND_NUMBER_IF_NO_THREE = Pattern.compile("[1-9]?");
    private static final Pattern SECOND_NUMBER_IF_THREE = Pattern.compile("[01]?");
    private static final Pattern FIRST_TWO_SYMBOL_IN_PHONE_NUMBER = Pattern.compile("79");
    private static final Pattern THIRD_OR_SIXTH_SYMBOL = Pattern.compile("\\.");
    private static final Pattern FOURTH_NUMBER = Pattern.compile("[01]");
    private static final Pattern FIFTH_NUMBER_IF_FOURTH_IS_ZERO = Pattern.compile("[1-9]");
    private static final Pattern FIFTH_NUMBER_IF_FOURTH_IS_ONE = Pattern.compile("[0-2]");
    private static final Pattern SEVENTH_NUMBER = Pattern.compile("2");
    private static final Pattern EIGHT_NUMBER = Pattern.compile("0");
    private static final Pattern NINTH_NUMBER = Pattern.compile("[2-9]");
    private static final Pattern TENTH_NUMBER = Pattern.compile("[0-9]");

    public FillingFieldsHelper() {
        NamedParameterJdbcTemplate jdbcTemplate = new DBConfig().namedParameterJdbcTemplate();
        this.visitorsRepository = new VisitorsRepository(jdbcTemplate);
        this.passRepository = new PassRepository(jdbcTemplate);
        this.visitsRepository = new VisitsRepository(jdbcTemplate);
        this.comeToDay = new com.example.demo.dao.repositories.impl.support.ComeToDay(jdbcTemplate);
        this.dontComeToDay = new com.example.demo.dao.repositories.impl.support.DontComeToDay(jdbcTemplate);
    }

    public ObservableList<Pass> getTablePass(StringProperty inputPhoneNumber){
        String phoneNumber = String.valueOf(inputPhoneNumber.get());
        return getListPass(phoneNumber);
    }

    public ObservableList<Visitors> getVisitorsObservableList(StringProperty inputPhoneNumber) {
        String phoneNumber = String.valueOf(inputPhoneNumber.get());
        if (phoneNumber != null) {
            System.out.println("в методе getVisitorsObservableList, если phoneNumber != null");
            Optional<Visitors> visitor = visitorsRepository.findVisitorByPhoneNumber(phoneNumber);
            System.out.println("visitor = " + visitor);
            return visitor.map(FXCollections::observableArrayList).orElseGet(FXCollections::emptyObservableList);
        }
        return FXCollections.emptyObservableList();
    }

    public boolean createVisitors(Visitors visitor) {
        return visitorsRepository.create(visitor);
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

    private Optional<List<com.example.demo.dao.supportTables.ComeToDay>> getAllComeToday() {
        return comeToDay.getAllComeToDay();
    }

    public ObservableList<com.example.demo.dao.supportTables.ComeToDay> getObservableListAllComeToday() {
        Optional<List<com.example.demo.dao.supportTables.ComeToDay>> listAll = getAllComeToday();
        return listAll.map(FXCollections::observableArrayList).orElseGet(FXCollections::emptyObservableList);
    }

    public ObservableList<com.example.demo.dao.supportTables.DontComeToDay> getObservableListDontComeToday() {
        Optional<List<com.example.demo.dao.supportTables.DontComeToDay>> listAll = getDontComeToday();
        return listAll.map(FXCollections::observableArrayList).orElseGet(FXCollections::emptyObservableList);
    }

    private Optional<List<com.example.demo.dao.supportTables.DontComeToDay>> getDontComeToday() {
        return dontComeToDay.getDontComeToDay();    }

    public boolean deletePassFromDB(Integer passId) {
        return passRepository.deletePass(passId);
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
     * Прибавляем занятие в абонементе (доступно только пдмину)
     * @param pass - информация об абонементе
     * @return количество оставшихся занятий в абонементе после прибавления
     */
    public Optional<String> plusClasses(Pass pass, Integer inputNumber, LocalDate specifiedDate) {
        pass.setVisitLimit(inputNumber);
        Optional<String> classesLeft = calculateClassesLeft(pass);
        if (classesLeft.isPresent()) {
            int numMinusVisits = inputNumber - Integer.parseInt(classesLeft.get());
            boolean isSuccess = minusVisit(pass.getNumPass(), numMinusVisits, specifiedDate);
            /* log.info(String.format("К оставшимся дням в абонементе %s прибавлено %s занятий. Из таблицы о посещениях " +
                    "удалены день и количество посещений? %s", pass.getVisitLimit(), inputNumber, isSuccess)); */
        }
        return classesLeft;
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

    public Optional<List<Integer>> passNumberActivePassList(List<Pass> listPass) {
        List<Integer> listPassNumberActivePass = new ArrayList<>();
        for (Pass p: listPass) {
            listPassNumberActivePass.add(p.getNumPass());
        }
        return Optional.of(listPassNumberActivePass);
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

    public String getClassesLeftFromAllPass(List<Pass> passList) {
        int classesLeftFromAllPass = 0;
        for (Pass p: passList) {
            Optional<String> classesLeftFromOnePass = calculateClassesLeft(p);
            if (classesLeftFromOnePass.isPresent()) {
                classesLeftFromAllPass += Integer.parseInt(classesLeftFromOnePass.get());
            }
        }
        return String.valueOf(classesLeftFromAllPass);
    }

    /**
     * Получение информации об абонементе (для админа)
     * @param numberPass - номер телефона студента
     */
    public Optional<Pass> getPassByPassNumber(Integer numberPass) {
        return passRepository.findPassByPassId(numberPass);
    }

    /**
     * Обновление информации об абонементе
     * @param phoneNumber - номер телефона студента
     * @param numberPass - номер абонемента
     * @param dateStart - дата начала абонемента
     * @param dateEnd - дата окончания абонемента
     * @param visitLimit - количество посещений в абонементе
     * @param visits - информация о посещениях по этому абонементу
     * @return true, если обновление прошло успешно
     */
    public boolean updatePass(String phoneNumber, Integer numberPass, LocalDate dateStart, LocalDate dateEnd,
                              Integer visitLimit, Visits visits) {
        Optional<Pass> pass = getPassByPassNumber(numberPass);
        if (pass.isPresent()) {
            pass.get().setDateStart(dateStart);
            pass.get().setDateEnd(dateEnd);
            pass.get().setVisitLimit(visitLimit);
            if (visits != null) {
                Optional<List<Visits>> listVisits = visitsRepository.findAllVisitsByPassId(pass.get().getNumPass());
                if (listVisits.isPresent()) {
                    listVisits.get().add(visits);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Получение информации об актуальном абонементе
     * @return список абонементов. По логике кода заложено, что может быть несколько активных абонементов.
     * Но, по идее (в реальности), должен быть только один
     */
    public Optional<List<Pass>> getActualPassByPhoneNumber(String phoneNumber) {
        Optional<List<Pass>> passListFromDB = passRepository.findPassByPhone(phoneNumber);
        if (passListFromDB.isPresent()) {
            List<Pass> listActualPass = new ArrayList<>();
            for (Pass p: passListFromDB.get()) {
                if (haveDayInPassCalculate(p)) {
                    listActualPass.add(p);
                    return Optional.of(listActualPass);
                }
            }
        }
        return Optional.empty();
    }

    public ObservableList<Pass> getListPass(String phoneNumber){
        if (phoneNumber != null) {
            Optional<List<Pass>> listPass = getActualPassByPhoneNumber(phoneNumber);
            if (listPass.isPresent() && !listPass.get().isEmpty()) {
                if (listPass.get().size() == 1) {
                    return  FXCollections.observableArrayList(listPass.get());
                } else {
                    // здесь вписать новое окно с выбором, какой именно абонемент (из двух) нужен?
                    return FXCollections.emptyObservableList();
                }
            }
        }
        return FXCollections.emptyObservableList();
    }

    public static void correctInputPhoneLine(TextField newPhoneNumberValue) {
        int maxCharacters = 11;
        newPhoneNumberValue.textProperty().addListener((observable10, oldValue10, newValue10) -> {
            String newPhoneNumberValueArray = newPhoneNumberValue.getText();
            if (newValue10.length() > maxCharacters) newPhoneNumberValue.deleteNextChar();
            if (newPhoneNumberValueArray.length() == 1) {
                newValue10 = newPhoneNumberValueArray;
                if (!FIRST_NUMBER_IN_PHONE.matcher(newValue10).matches()) newPhoneNumberValue.setText(oldValue10);
            }
            if (newPhoneNumberValueArray.length() == 2) {
                newValue10 = String.valueOf(newPhoneNumberValueArray.charAt(1));
                if (!SECOND_NUMBER_IN_PHONE.matcher(newValue10).matches()) newPhoneNumberValue.setText(oldValue10);
            }
            if (newPhoneNumberValueArray.length() > 2 && newPhoneNumberValueArray.length() <= 10) {
                newValue10 = String.valueOf(newPhoneNumberValueArray.charAt(newPhoneNumberValueArray.length() - 1));
                if (!isNumbers(newValue10)) newPhoneNumberValue.setText(oldValue10);
            }
        });
    }

    public static boolean isNumbers(String inputNumbers) {
        return Pattern.compile(PatternTemplate.INTEGER_LINE.getTemplate()).matcher(inputNumbers).matches();
    }

    public static void correctInputDateLine(TextField dateInput) {
        dateInput.textProperty().addListener((observable, oldValue, newValue) -> {
            String dateInputArray = dateInput.getText();
            if (dateInputArray.length() == 1) {
                newValue = dateInputArray;
                if (!FIRST_NUMBER.matcher(newValue).matches()) dateInput.setText(oldValue);
            }
            if (dateInputArray.length() == 2) {
                if ("3".equals(String.valueOf(dateInputArray.charAt(0)))) {
                    newValue = String.valueOf(dateInputArray.charAt(1));
                    if (!SECOND_NUMBER_IF_THREE.matcher(newValue).matches()) dateInput.setText(oldValue);
                } else {
                    newValue = String.valueOf(dateInputArray.charAt(1));
                    if (!SECOND_NUMBER_IF_NO_THREE.matcher(newValue).matches()) dateInput.setText(oldValue);
                }
            }
            if (dateInputArray.length() == 3 || dateInputArray.length() == 6) {
                newValue = String.valueOf(dateInputArray.charAt(2));
                if (dateInputArray.length() == 6) newValue = String.valueOf(dateInputArray.charAt(5));;
                if (!THIRD_OR_SIXTH_SYMBOL.matcher(newValue).matches()) dateInput.setText(oldValue);
            }
            if (dateInputArray.length() == 4) {
                newValue = String.valueOf(dateInputArray.charAt(3));
                if (!FOURTH_NUMBER.matcher(newValue).matches()) dateInput.setText(oldValue);
            }
            if (dateInputArray.length() == 5) {
                if ("0".equals(String.valueOf(dateInputArray.charAt(3)))) {
                    newValue = String.valueOf(dateInputArray.charAt(4));
                    if (!FIFTH_NUMBER_IF_FOURTH_IS_ZERO.matcher(newValue).matches()) dateInput.setText(oldValue);
                } else {
                    newValue = String.valueOf(dateInputArray.charAt(4));
                    if (!FIFTH_NUMBER_IF_FOURTH_IS_ONE.matcher(newValue).matches()) dateInput.setText(oldValue);
                }
            }
            if (dateInputArray.length() == 7) {
                newValue = String.valueOf(dateInputArray.charAt(6));
                if (!SEVENTH_NUMBER.matcher(newValue).matches()) dateInput.setText(oldValue);
            }
            if (dateInputArray.length() == 8) {
                newValue = String.valueOf(dateInputArray.charAt(7));
                if (!EIGHT_NUMBER.matcher(newValue).matches()) dateInput.setText(oldValue);
            }
            if (dateInputArray.length() == 9) {
                newValue = String.valueOf(dateInputArray.charAt(8));
                if (!NINTH_NUMBER.matcher(newValue).matches()) dateInput.setText(oldValue);
            }
            if (dateInputArray.length() == 10) {
                newValue = String.valueOf(dateInputArray.charAt(9));
                if (!TENTH_NUMBER.matcher(newValue).matches()) dateInput.setText(oldValue);
            }
            if (dateInputArray.length() > 10) dateInput.setText(oldValue);
        });
    }

    public boolean updatePhoneNumberInPass(@NonNull String oldValuePhoneNumber, @NonNull String newValuePhoneNumber) {
        if (passRepository.findPassByPhone(oldValuePhoneNumber).isPresent()) {
            return passRepository.updatePhoneNumberInPass(oldValuePhoneNumber, newValuePhoneNumber);
        } else {
            return true;
        }
    }

    public static void correctInputIntegerLine(TextField number) {
        number.textProperty().addListener((observable11, oldValue11, newValue11) -> {
            if (!INTEGER_NUM.matcher(newValue11).matches()) number.setText(oldValue11);
        });
    }

    public static void correctInputStringLine(TextField anyString) {
        anyString.textProperty().addListener((observable50, oldValue50, newValue50) -> {
            String anyStringArray = anyString.getText();
            if (anyStringArray.length() == 1) {
                newValue50 = anyStringArray;
                if (!LETTERS.matcher(newValue50).matches()) anyString.setText(oldValue50);
            }
            if (anyStringArray.length() > 1) {
                newValue50 = String.valueOf(anyStringArray.charAt(anyStringArray.length() - 1));
                if (!LETTERS.matcher(newValue50).matches()) anyString.setText(oldValue50);
            }
        });
    }

    public boolean deleteVisitorFromDB(String phoneNumber) {
        boolean isSuccessDeleteVisits = deleteVisits(phoneNumber);
        boolean isSuccessDeletePass = false;
        boolean isSuccessDeleteStudent = false;
        if (isSuccessDeleteVisits) {
            isSuccessDeletePass = passRepository.deletePass(phoneNumber);
            if (isSuccessDeletePass) {
                isSuccessDeleteStudent = visitorsRepository.deleteVisitor(phoneNumber);
            }
        }
        return isSuccessDeleteVisits && isSuccessDeletePass && isSuccessDeleteStudent;
    }

    public static boolean isDate(TextField dateInput) {
        return Pattern.compile(PatternTemplate.DATE.getTemplate()).matcher(dateInput.getCharacters()).matches();
    }

    public LocalDate convertFormatLocalDate(String date) throws ParseException {
        SimpleDateFormat oldDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        java.util.Date newDate = oldDateFormat.parse(date);
        String result = newDateFormat.format(newDate);
        return LocalDate.parse(result);
    }

    public static boolean isPhoneNumber(String phoneNumber) {
        return !phoneNumber.isBlank() && phoneNumber.length() == 11
                && phoneNumber.matches(PatternTemplate.INTEGER_LINE.getTemplate())
                && Pattern.compile(PatternTemplate.FIRST_IN_PHONE.getTemplate()).matcher(String.valueOf(phoneNumber.charAt(0))).matches();
    }

    public static boolean isLetter(String line) {
        return line.matches("[а-яА-Я]+");
    }

    public static boolean isFillingAllFields(TextField phoneNumber, TextField dateStart, TextField limitVisits,
                                             TextField dateEnd, TextField limitFreeze, TextField dateStartFreeze) {
        boolean isPhoneNumber = phoneNumber.getText() != null && isPhoneNumber(phoneNumber.getText());
        boolean isDateStart = dateStart.getText() != null && isDate(dateStart);
        boolean isDateEnd = dateEnd.getText() != null && isDate(dateEnd);
        boolean isLimitVisits = limitVisits.getText() != null && isNumbers(limitVisits.getText());

        if (limitFreeze != null && limitFreeze.getText() != null) {
            boolean isLimitFreeze = isNumbers(limitFreeze.getText());
            boolean isDateStartFreeze = isDate(dateStartFreeze);

            return isPhoneNumber && isDateEnd && isDateStart && isLimitVisits && isLimitFreeze && isDateStartFreeze;
        }

        return isPhoneNumber && isDateEnd && isDateStart && isLimitVisits;
    }

    public Optional<List<Pass>> getPassList(String phoneNumber) {
        return passRepository.findPassByPhone(phoneNumber);
    }

    public boolean createPassInDB(Pass pass) {
        return passRepository.createPass(pass);
    }

    public Optional<List<Integer>> getListPassIdForDeleteVisits(String phoneNumber) {
        Optional<List<Pass>> passListFromDB = passRepository.findPassByPhone(phoneNumber);
        if (passListFromDB.isPresent()) {
            List<Integer> listPassId = new ArrayList<>();
            for(Pass p: passListFromDB.get()) {
                Integer passId = p.getNumPass();
                listPassId.add(passId);
            }
            return Optional.of(listPassId);
        }
        return Optional.empty();
    }

    private boolean deleteAllVisitsFromDB(List<Integer> listPassId) {
        for(Integer i: listPassId) {
            boolean isSuccess = visitsRepository.deleteVisit(i);
            if (!isSuccess) {
                return false;
            }
        }
        return true;
    }

    private boolean deleteVisits(String phoneNumber) {
        Optional<List<Integer>> list = getListPassIdForDeleteVisits(phoneNumber);
        return list.filter(this::deleteAllVisitsFromDB).isPresent();
    }

    private static boolean is79FirstTwoSymbol(String substringPhone) {
        return FIRST_TWO_SYMBOL_IN_PHONE_NUMBER.matcher(substringPhone).matches();
    }

        /**
         * Проверка, есть ли у этого chatId номер телефона в базе
         */
        public boolean havPhoneNumber(Long chatId) {
            Optional<String> phoneNumberInDB = visitorsRepository.findTelephoneNumByChatId(chatId);
            return phoneNumberInDB.isPresent();
        }

        public boolean havChatId(String phoneNumber) {
            Optional<Long> chatIDinDB = visitorsRepository.findChatIdByPhoneNumber(phoneNumber);
            return chatIDinDB.isPresent();
        }

        /**
         * Проверка, есть ли номер телефона в базе
         */
        public boolean existPhoneNumber(String phoneNumber) {
            Optional<Visitors> visitor = visitorsRepository.findVisitorByPhoneNumber(phoneNumber);
            return visitor.isPresent();
        }

        /**
         * Получение информации об абонементе
         * @param chatId - уникальный номер пользователя в telegram bot

        public Optional<List<Pass>> getPassByChatId(Long chatId) {
        Optional<Visitors> visitor = getVisitorByPhone(chatId);
        return visitor.map(Visitors::getPassList);
        }*/

        /**
         * Получение информации об абонементе
         * @param phoneNumber - номер телефона студента

        public Optional<List<Pass>> getPassByPhoneNumber(String phoneNumber) {
        Optional<Visitors> visitors = getVisitorByPhone(phoneNumber);
        return visitors.map(Visitors::getPassList);
        }  */

        /**
         * Заносим номер телефона в базу данных по chatId
         */
        public void createVisitorsBot(Long chatId, String phoneNumber) {
            System.out.println("вписываем телефон в базу данных");
            Visitors visitor = new Visitors();
            visitor.setChatId(chatId);
            visitor.setTelephoneNum(phoneNumber);
            visitorsRepository.create(visitor);
        }

        /**
         * Занесение данных о студенте (только для админов)

         public void createVisitorsAdmin(String phoneNumber, String name, @Nullable String surname,
         @Nullable String patronymic, @Nullable Pass pass) {
         Visitors visitor = new Visitors();
         visitor.setTelephoneNum(phoneNumber);
         visitor.setName(name);
         if(surname != null) {
         visitor.setSurname(surname);
         }
         if(patronymic != null) {
         visitor.setPatronymic(patronymic);
         }
         if(pass != null) {
         List<Pass> listPass = visitor.getPassList();
         listPass.add(pass);
         visitor.setPassList(listPass);
         }
         visitorsRepository.create(visitor);
         }  */

        /**
         * Получить данные студента по номеру телефона
         * @param phoneNumber - номер телефона студента
         */
        public Optional<Visitors> getVisitorByPhone(String phoneNumber) {
            return visitorsRepository.findVisitorByPhoneNumber(phoneNumber.trim());
        }

        /**
         * Удаление информации о студенте из БД (только для админов)
         * @param chatId - идентификатор студента в Телеграмм
         * @return true, если удаление прошло успешно
         */
        public boolean deleteVisitors(Long chatId) {
            BigDecimal bigDecimal = BigDecimal.valueOf(chatId);
            Integer chatIdForDB = Integer.valueOf(String.valueOf(bigDecimal));
            //visitorsRepository.deleteById(chatIdForDB);
            return true;
        }

        /**
         * Удаление информации о студенте (доступно только админам)
         * @param phoneNumber - номер телефона студента
         * @return true, если удаление прошло успешно

        public boolean deleteVisitors(String phoneNumber) {
        Optional<Visitors> visitor = getVisitor(phoneNumber);
        if (visitor.isPresent()) {
        Long chatId = visitor.get().getChatId();
        visitorsRepository.deleteById(chatId);
        return true;
        }
        return false;
        } */

        /**
         * Обновление информации о студенте
         * @param phoneNumber
         * @param surname - фамилия
         * @param patronymic - отчество
         * @return true, если обновление прошло успешно
         */
        public boolean updateInfoOfVisitor(String phoneNumber, String name, String surname,
                                           String patronymic, String newPhoneNumber) throws SeveralException {
            Optional<Visitors> visitors = getVisitorByPhone(phoneNumber);
            if (visitors.isPresent()) {
                if (name != null) {
                    visitors.get().setName(name);
                }
                if (surname != null) {
                    visitors.get().setSurname(surname);
                }
                if (patronymic != null) {
                    visitors.get().setPatronymic(patronymic);
                }
                if (newPhoneNumber != null) {
                    visitors.get().setTelephoneNum(newPhoneNumber);
                }
                try {
                    return visitorsRepository.updateByPhoneNumber(visitors.get());
                } catch (Exception e) {
                    return false;
                }
            }
            return false;
        }

        /**
         * Изменение номера телефона студента
         * @param phoneNumber - имеющийся в базе номер телефона студента
         * @param newPhoneNumber - новый номер телефона
         * @return true, если номер телефона изменен

        public boolean changePhoneNumberOfVisitor(String phoneNumber, String newPhoneNumber) {
        Optional<Visitors> visitors = getVisitorByPhone(phoneNumber);
        if (visitors.isPresent()) {
        visitors.get().setTelephoneNum(newPhoneNumber);
        visitorsRepository.save(visitors.get());
        return true;
        }
        return false;
        } */

        /**
         * Получение информации о визитах
         * @param chatId - идентификатор студента в Телеграмме

        public Optional<List<Visits>> getVisit(Long chatId, Integer passId) {
        Optional<List<Pass>> pass = getPassByChatId(chatId);
        return pass.map(passes -> passes.get(passId).getVisits());
        }*/

        /**
         * Получить список всех студентов, кто придет в текущий день
         */
        public List<Visitors> getVisitorsListToDay() throws SeveralException {
            List<Visitors> listVisitorsToDay = new ArrayList<>();
            LocalDate currencyDay = LocalDate.now(ZoneId.of("GMT+03:00"));
            Date sqlDate = Date.valueOf(currencyDay);
            Optional<List<Integer>> listPassId = getPassIdBySpecifiedDay(sqlDate);
            if (listPassId.isPresent()) {
                for(Integer i: listPassId.get()) {
                    Optional<String> phoneNumber = passRepository.findPhoneNumberByPassId(i);
                    if (phoneNumber.isPresent()) {
                        Optional<Visitors> visitors = getVisitorByPhone(phoneNumber.get());
                        visitors.ifPresent(listVisitorsToDay::add);
                    }
                }
            }
            return listVisitorsToDay;
        }

    /**
     * Внесение информации о визитах к конкретному абонементу студента
     * @param passId идентификатор абонемента
     * @param dateVisit - дата посещения
     * @return - true, если занесении информации о конкретном посещении прошло успешно
     */
    public boolean updateVisit(Integer passId, LocalDate dateVisit, Integer countVisit) {
        Optional<List<Visits>> visitList = getVisitFromDB(passId);
        if (visitList.isPresent()) {
            for (Visits v: visitList.get()) {
                if (dateVisit.equals(v.getDateVisit())) {
                    v.setCountVisit(countVisit);
                    return visitsRepository.updateVisit(v);
                }
            }
        }
        return false;
    }

    public boolean createVisit(Visits visit) throws ExceptionDB {
        return visitsRepository.createVisit(visit);
    }

    /**
     * Получение информации о визитах
     * @param passId - номер абонемента из БД
     */
    public Optional<List<Visits>> getVisitFromDB(Integer passId) {
        return visitsRepository.findAllVisitsByPassId(passId);
    }

    public boolean minusVisit(Integer passId, int inputNumber, LocalDate specifiedDay) {
        Optional<List<Visits>> list = getVisitFromDB(passId);
        if(list.isPresent()) {
            for (Visits v: list.get()) {
                if (specifiedDay.equals(v.getDateVisit())) {
                    Integer countVisitFromDB = v.getCountVisit();
                    if (countVisitFromDB >= inputNumber) {
                        v.setCountVisit(v.getCountVisit() - inputNumber);
                        return visitsRepository.updateVisit(v);
                    }
                }
            }
        }
        return false;
    }

    public boolean updateVisitors(Visitors visitor) {
        try {
            return visitorsRepository.updateByPhoneNumber(visitor);
        } catch (Exception e) {
            return false;
        }
    }

    private Integer calculateCountVisit(Integer countVisit) {
        return ++countVisit;
    }

    /**
     * Получить номера абонементов студентов, которые придут в указанный день
     * @param specifiedDate - указанный день
     * @return список номеров абонементов
     */
    public Optional<List<Integer>> getPassIdBySpecifiedDay(Date specifiedDate) {
        Optional<List<Visits>> visitsList = visitsRepository.findAllPassBySpecifiedDay(specifiedDate);
        List<Integer> listPassId = new ArrayList<>();
        if (visitsList.isPresent()) {
            for (Visits v: visitsList.get()) {
                listPassId.add(v.getPass());
            }
        }
        return Optional.of(listPassId);
    }

}
