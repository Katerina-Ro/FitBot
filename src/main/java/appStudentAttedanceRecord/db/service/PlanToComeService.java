package appStudentAttedanceRecord.db.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import telegramBot.enteties.Pass;
import telegramBot.enteties.Visitors;
import telegramBot.enteties.Visits;
import telegramBot.service.entetiesService.PassService;
import telegramBot.service.entetiesService.VisitorsService;
import telegramBot.service.entetiesService.VisitsService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class PlanToConeService {
    private final VisitsService visitsService;
    private final PassService passService;
    private final VisitorsService visitorsService;

    @Autowired
    public PlanToConeService(VisitsService visitsService, PassService passService, VisitorsService visitorsService) {
        this.visitsService = visitsService;
        this.passService = passService;
        this.visitorsService = visitorsService;
    }

    /**
     * Получить список всех студентов, кто придет в текущий день
     */
    public List<Visitors> getVisitorsListToDay() {
        List<Visitors> listVisitorsToDay = new ArrayList<>();
        LocalDate currencyDay = LocalDate.now(ZoneId.of("GMT+03:00"));
        Optional<List<Integer>> listPassId = visitsService.getListPassId(currencyDay);
        if (listPassId.isPresent()) {
            for(Integer i: listPassId.get()) {
                Long chatId = passService.getChatId(i);
                Optional<Visitors> visitors = visitorsService.getVisitor(chatId);
                visitors.ifPresent(listVisitorsToDay::add);
            }
        }
        return listVisitorsToDay;
    }

    // возможно, из-за метода ниже спринг циклится по бинам
    /**
     * Получить данные студента по номеру абонемента
     * @param passId - номер абонемента
     */
    public Optional<Visitors> getVisitor(Integer passId) {
        return visitorsService.getVisitor(passService.getChatId(passId));
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
                    if (passService.validateActualPass(p)) {
                        return Optional.ofNullable(p);
                    }
                }
            }
        }
        return Optional.empty();
    }




    /**
     * Вычет занятия из абонемента, если студент нажал "Да"
     */
    public boolean deductVisitIfYes(Pass pass) {
        if (passService.haveDayInPassCalculate(pass)) {
            LocalDate currencyDay = LocalDate.now(ZoneId.of("GMT+03:00"));
            Visits visits = new Visits();
            visits.setCountVisit(pass.getVisitLimit() - 1);
            visits.setDateVisit(currencyDay);
            boolean isSuccess = visitsService.createVisit(pass.getNumPass(), currencyDay);
            passService.createOrUpdatePass(pass);
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
        if (!deductVisitIfYes(pass) && passService.haveDayInPassCalculate(pass) && passService.isMidnightCurrencyDay()
                && !passService.clickButtonNo(isClickButtonNo)) {
            deductVisitIfYes(pass);
        }
    }





    /**
     * Прибавляем занятие в абонементе (доступно только пдмину)
     * @param pass - информация об абонементе
     * @return количество оставшихся занятий в абонементе после прибавления
     */
    public int plusClasses(Pass pass, Integer inputNumber) {
        pass.setVisitLimit(inputNumber);
        Integer classesLeft = passService.calculateClassesLeft(pass);
        int numMinusVisits = inputNumber - classesLeft;
        boolean isSuccess = visitsService.minusVisit(pass.getNumPass(), numMinusVisits);
        log.info(String.format("К оставшимся дням в абонементе %s прибавлено %s занятий. Из таблицы о посещениях " +
                "удалены день и количество посещений? %s", pass.getVisitLimit(), inputNumber, isSuccess));
        return classesLeft;
    }



    /**
     * Получение информации о визитах
     * @param chatId - идентификатор студента в Телеграмме
     */
    public Optional<List<Visits>> getVisit(Long chatId, Integer passId) {
        Optional<List<Pass>> pass = visitorsService.getPassByChatId(chatId);
        return pass.map(passes -> passes.get(passId).getVisits());
    }
}
