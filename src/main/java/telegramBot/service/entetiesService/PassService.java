package telegramBot.service.entetiesService;

import dto.DontPlanToComeToDay;
import dto.PlanToComeToDay;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import telegramBot.enteties.Pass;
import telegramBot.enteties.Visitors;
import telegramBot.enteties.Visits;
import telegramBot.repositories.PassRepository;

import javax.annotation.Nullable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class PassService {
    @Getter
    @Setter
    private Map<String, PlanToComeToDay> mapVisitorsToDay = new HashMap<>();

    @Getter
    @Setter
    private Map<String, DontPlanToComeToDay> mapDontCome = new HashMap<>();

    @Getter
    private final PassRepository passRepository;
    private final VisitsService visitsService;
    private final VisitorsService visitorsService;

    @Autowired
    public PassService(PassRepository passRepository, VisitsService visitsService, VisitorsService visitorsService) {
        this.passRepository = passRepository;
        this.visitsService = visitsService;
        this.visitorsService = visitorsService;
    }

    public void createOrUpdatePass(Pass pass) {
        passRepository.save(pass);
    }

    public boolean clickButtonNo(Boolean isClickButtonNo) {
        return isClickButtonNo;
    }

    /**
     * Получение информации об актуальном абонементе
     * @param chatId - идентификатор студента в Телеграмм
     */
    public Optional<Pass> getActualPassByChatId(Long chatId) {
        Optional<Visitors> visitor = visitorsService.getVisitor(chatId);
        if (visitor.isPresent()) {
            List<Pass> list = visitor.get().getPassList();
            if (list != null && !list.isEmpty()) {
                for (Pass p: list) {
                    if (validateActualPass(p)) {
                        return Optional.ofNullable(p);
                    }
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Проверка, что текущий день находится между датой начала и конца абонемента, и в абонементе есть заняти
     * @param pass - информация об абонементе
     * @return true, если абонемент действующий
     */
    public boolean validateActualPass(Pass pass) {
        return betweenDate(pass) && haveDayInPassCalculate(pass);
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
     * @return
     */
    public Optional<String> calculateClassesLeft(Pass pass) {
        String classesLeft = null;
        int cumulativeTotal = 0;
        if (betweenDate(pass)) {
            if (haveDayInPassCalculate(pass)) {
                List<Visits> listVisits = pass.getVisits();
                for (Visits v: listVisits) {
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
            visits.setCountVisit(pass.getVisitLimit() - 1);
            visits.setDateVisit(Date.valueOf(currencyDay));
            boolean isSuccess = visitsService.createVisit(pass.getNumPass(), currencyDay);
            createOrUpdatePass(pass);
           // log.info(String.format("Вычтено занятие из абонемента %s, т.к. отметил боту 'Да' за %s день. " +
                           // "Создание записи в таблице Посещений прошло успешно? %s", pass.getNumPass(),
                    //currencyDay, isSuccess));
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
        return passRepository.findById(numberPass);
    }

    /**
     * Получить ChatId студента по номеру абонемента
     * @param passId - номер абонемента
     */
    public Long getChatId(Integer passId) {
        return passRepository.findChatIdByPassId(passId);
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
                List<Visits> listVisits = pass.get().getVisits();
                listVisits.add(visits);
                pass.get().setVisits(listVisits);
            }
            return true;
        }
        return false;
    }

    /**
     * Проверка, полночь или нет
     * @return true, если полночь
     */
    public boolean isMidnightCurrencyDay() {
        LocalTime currencyTime = LocalTime.now();
        //log.info("Получаем текущее время : " + currencyTime.toString());
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
    public Optional<String> plusClasses(Pass pass, Integer inputNumber) {
        pass.setVisitLimit(inputNumber);
        Optional<String> classesLeft = calculateClassesLeft(pass);
        if (classesLeft.isPresent()) {
            int numMinusVisits = inputNumber - Integer.parseInt(classesLeft.get());
            boolean isSuccess = visitsService.minusVisit(pass.getNumPass(), numMinusVisits);
            //log.info(String.format("К оставшимся дням в абонементе %s прибавлено %s занятий. Из таблицы о посещениях " +
                    //"удалены день и количество посещений? %s", pass.getVisitLimit(), inputNumber, isSuccess));
        }
        return classesLeft;
    }
}
