package telegramBot.service.modelService;

import appStudentAttedanceRecord.db.dto.DontPlanToComeToDay;
import appStudentAttedanceRecord.db.dto.PlanToComeToDay;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import telegramBot.model.Pass;
import telegramBot.model.Visitors;
import telegramBot.model.Visits;
import telegramBot.repositories.IPassRepository;
import telegramBot.repositories.impl.VisitsRepository;

import javax.annotation.Nullable;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

@Log4j2
@Service
public class PassService {
    @Getter
    @Setter
    private Map<String, PlanToComeToDay> mapVisitorsToDay = new HashMap<>();

    @Getter
    @Setter
    private Map<String, DontPlanToComeToDay> mapDontCome = new HashMap<>();

    @Getter
    private final IPassRepository passRepository;
    private final VisitsService visitsService;
    private final VisitorsService visitorsService;
    private final VisitsRepository visitsRepository;

    @Autowired
    public PassService(IPassRepository passRepository, VisitsService visitsService, VisitorsService visitorsService, VisitsRepository visitsRepository) {
        this.passRepository = passRepository;
        this.visitsService = visitsService;
        this.visitorsService = visitorsService;
        this.visitsRepository = visitsRepository;
    }

    public void updatePass(Pass pass) {
        passRepository.update(pass);
    }

    public boolean clickButtonNo(Boolean isClickButtonNo) {
        return isClickButtonNo;
    }

    /**
     * Получение информации об актуальном абонементе
     * @param chatId - идентификатор студента в Телеграмм
     * @return список абонементов. По логике кода заложено, что может быть несколько активных абонементов.
     * Но, по идее (в реальности), должен быть только один
     */
    public Optional<List<Pass>> getActualPassByChatId(Long chatId) {
        Optional<Visitors> visitor = visitorsService.getVisitorByChatId(chatId);
        Optional<List<Pass>> passListFromDB;
        if (visitor.isPresent()) {
            String phoneNumber = visitor.get().getTelephoneNum();
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
     * Вычет занятия из абонемента, если студент нажал "Да"
     */
    public boolean deductVisitIfYes(Pass pass) {
        if (haveDayInPassCalculate(pass)) {
            LocalDate currencyDay = LocalDate.now(ZoneId.of("GMT+03:00"));
            Visits visits = new Visits();
            visits.setPass(pass.getNumPass());
            visits.setCountVisit(pass.getVisitLimit() - 1);
            visits.setDateVisit(currencyDay);
            boolean isSuccess = visitsService.createVisit(visits);
            updatePass(pass);
            log.info(String.format("Вычтено занятие из абонемента %s, т.к. отметил боту 'Да' за %s день. " +
                            "Создание записи в таблице Посещений прошло успешно? %s", pass.getNumPass(),
                    currencyDay, isSuccess));
            return true;
        }
        return false;
    }

    /**
     * Вычет занятия, если до 23:59:00.000000000 не было ответа (ни "Да", ни "Нет")
     */
    public void deductVisitWithOutAnswer(Pass pass, Boolean isClickButtonNo) {
        if (!deductVisitIfYes(pass) && haveDayInPassCalculate(pass) && isMidnightCurrencyDay()
                && !clickButtonNo(isClickButtonNo)) {
            deductVisitIfYes(pass);
        }
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
                              Integer visitLimit, @Nullable Visits visits) {
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
     * Проверка, полночь или нет
     * @return true, если полночь
     */
    public boolean isMidnightCurrencyDay() {
        LocalTime currencyTime = LocalTime.now();
        log.info("Получаем текущее время : " + currencyTime.toString());
        System.out.println("Получаем текущее время : " + currencyTime);
        return "23:59:00.000000000".equals(currencyTime.toString());
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

    /**
     * Прибавляем занятие в абонементе (доступно только пдмину)
     * @param pass - информация об абонементе
     * @return количество оставшихся занятий в абонементе после прибавления
     */
    public Optional<String> plusClasses(Pass pass, Integer inputNumber, @NotBlank LocalDate specifiedDate) {
        pass.setVisitLimit(inputNumber);
        Optional<String> classesLeft = calculateClassesLeft(pass);
        if (classesLeft.isPresent()) {
            int numMinusVisits = inputNumber - Integer.parseInt(classesLeft.get());
            boolean isSuccess = visitsService.minusVisit(pass.getNumPass(), numMinusVisits, specifiedDate);
            log.info(String.format("К оставшимся дням в абонементе %s прибавлено %s занятий. Из таблицы о посещениях " +
                    "удалены день и количество посещений? %s", pass.getVisitLimit(), inputNumber, isSuccess));
        }
        return classesLeft;
    }
}
